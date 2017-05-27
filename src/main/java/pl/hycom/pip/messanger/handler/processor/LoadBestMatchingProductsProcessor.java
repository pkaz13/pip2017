/*
 *   Copyright 2012-2014 the original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package pl.hycom.pip.messanger.handler.processor;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.hycom.pip.messanger.model.Keyword;
import pl.hycom.pip.messanger.model.Product;
import pl.hycom.pip.messanger.pipeline.PipelineContext;
import pl.hycom.pip.messanger.pipeline.PipelineException;
import pl.hycom.pip.messanger.pipeline.PipelineProcessor;
import pl.hycom.pip.messanger.service.KeywordService;
import pl.hycom.pip.messanger.service.ProductService;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Log4j2
public class LoadBestMatchingProductsProcessor implements PipelineProcessor {

    private static final int SHOW_PRODUCTS = 1;
    private static final int FIND_KEYWORD_TO_ASK = 2;

    @Autowired
    private ProductService productService;

    @Autowired
    private KeywordService keywordService;

    @Value("${messenger.recommendation.products-amount}")
    private Integer numberOfProducts;

    @Override
    public int runProcess(PipelineContext ctx) throws PipelineException {
        log.info("Started process of LoadBestMatchingProductsProcessor");

        @SuppressWarnings("unchecked")
        Set<String> keywordsStr = ctx.get(KEYWORDS, Set.class);
        @SuppressWarnings("unchecked")
        Set<String> excludedKeywordsStr = ctx.get(KEYWORDS_EXCLUDED, Set.class);

        List<Keyword> keywords = convertStringsToKeywords(keywordsStr);
        List<Keyword> excludedKeywords = convertStringsToKeywords(excludedKeywordsStr);

        List<Product> products = tryFindBestMatchingProducts(keywords, excludedKeywords);
        ctx.put(PRODUCTS, products);

        List<Keyword> keywordsToBeSaved = getKeywordsThatWereInAnyProduct(products, keywords);
        ctx.put(KEYWORDS_FOUND, keywordsToBeSaved);

        return 1;
    }

    public List<Keyword> convertStringsToKeywords(Set<String> keywords) {
        if (keywords == null || keywords.isEmpty()) {
            return Collections.emptyList();
        }

        // This stream maps Array of strings into list of keywords
        return keywords.stream()
                .map(s -> keywordService.findKeywordByWord(s))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    List<Product> tryFindBestMatchingProducts(List<Keyword> keywordsList, List<Keyword> excludedKeywords) {
        if (keywordsList == null || keywordsList.isEmpty()) {
            return Collections.emptyList();
        }
        if (excludedKeywords == null) {
            excludedKeywords = Collections.emptyList();
        }
        List<Keyword> commonKeywords = new ArrayList<>(keywordsList);
        commonKeywords.retainAll(excludedKeywords);
        if (!commonKeywords.isEmpty()) {
            throw new InvalidParameterException("Excluded keyword cant be keyword to find");
        }
        return findBestMatchingProducts(keywordsList, excludedKeywords);
    }

    /**
     * Call only from wrapper method
     *
     * @see #tryFindBestMatchingProducts
     */
    private List<Product> findBestMatchingProducts(List<Keyword> keywordsList, List<Keyword> excludedKeywords) {
        List<Map.Entry<Product, Integer>> productsWithKeywords = productService
                .findAllProductsContainingAtLeastOneKeyword(keywordsList)
                .stream()
                .filter(product -> product.getKeywords().stream().noneMatch(excludedKeywords::contains))
                .collect(Collectors.toMap(Function.identity(), product -> {
                    List<Keyword> commonKeywords = new ArrayList<>(product.getKeywords());
                    commonKeywords.retainAll(keywordsList);
                    return commonKeywords.size();
                }))
                .entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> ((Map.Entry<Product, Integer>) entry).getValue()).reversed())
                .collect(Collectors.toList());

        if (productsWithKeywords.size() > 0) {
            int maxMatchingKeywords = productsWithKeywords.get(0).getValue();
            return productsWithKeywords.stream()
                    .filter(entry -> entry.getValue().equals(maxMatchingKeywords))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    private List<Keyword> getKeywordsThatWereInAnyProduct(List<Product> products, List<Keyword> keywords) {
        if (keywords == null || keywords.isEmpty() || products == null || products.isEmpty()) {
            return Collections.emptyList();
        }

        // This stream takes only these keywords from list that appear in at least one product
        return keywords.stream()
                .distinct()
                .filter(keyword -> products.stream().anyMatch(product -> product.containsKeyword(keyword)))
                .collect(Collectors.toList());

    }
}
