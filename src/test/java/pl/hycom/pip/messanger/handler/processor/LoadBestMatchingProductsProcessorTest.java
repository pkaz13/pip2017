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
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.hycom.pip.messanger.model.Keyword;
import pl.hycom.pip.messanger.model.Product;
import pl.hycom.pip.messanger.service.KeywordService;
import pl.hycom.pip.messanger.service.ProductService;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
@Log4j2
public class LoadBestMatchingProductsProcessorTest {

    @Mock
    private ProductService productService;

    @Mock
    private KeywordService keywordService;

    @InjectMocks
    private LoadBestMatchingProductsProcessor processor;

    private List<Keyword> keywords;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        keywords = Arrays.asList(new Keyword("K1"), new Keyword("K2"), new Keyword("K3"), new Keyword("K4"), new Keyword("K5"), new Keyword("K6"));
    }

    @Test
    @Transactional
    public void findBestFittingProductsTest() {
        //given
        List<Product> givenProducts = Arrays.asList(
                createProduct(1, Arrays.asList(keywords.get(0), keywords.get(1), keywords.get(2))),
                createProduct(2, Arrays.asList(keywords.get(0), keywords.get(2), keywords.get(3))),
                createProduct(3, Arrays.asList(keywords.get(0), keywords.get(2), keywords.get(3), keywords.get(4))),
                createProduct(4, Arrays.asList(keywords.get(0), keywords.get(5), keywords.get(4))),
                createProduct(5, Arrays.asList(keywords.get(0), keywords.get(2), keywords.get(3), keywords.get(5))),
                createProduct(6, Arrays.asList(keywords.get(0), keywords.get(1)))
        );
        Mockito.when(productService.findAllProductsContainingAtLeastOneKeyword(any()))
                .thenReturn(givenProducts);

        List<Keyword> keywordsFromRequest = Arrays.asList(keywords.get(0), keywords.get(2), keywords.get(3));
        int numberOfMatchingProducts = 3;

        //when
        List<Product> bestFittingProducts = processor.findBestMatchingProducts(numberOfMatchingProducts, keywordsFromRequest);

        // then
        Assertions.assertThat(bestFittingProducts).hasSize(numberOfMatchingProducts)
                .containsOnly(givenProducts.get(1), givenProducts.get(2), givenProducts.get(4));
    }

    @Test
    @Transactional
    public void findBestFittingProductsTestNoKeywords() {
        List<Product> bestFittingProducts = processor.findBestMatchingProducts(3, Collections.emptyList());

        // assertion
        assertEquals("List should contain no products", 0, bestFittingProducts.size());
    }

    private Product createProduct(int id, List<Keyword> keywords) {
        final Product product = new Product();
        product.setId(id);
        product.setName("Foo");
        product.setDescription("Foo");
        product.setImageUrl("Foo");
        product.setKeywords(new HashSet<>(keywords));
        return product;
    }
}
