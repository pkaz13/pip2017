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
import pl.hycom.pip.messanger.service.ProductService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Log4j2
public class LoadBestMatchingProductsProcessor implements PipelineProcessor{

    private static final String PRODUCTS = "products";
    private static final String KEYWORDS_FOUND = "keywordsFound";

    @Autowired
    private ProductService productService;

    @Value("${messenger.recommendation.products-amount}")
    private Integer numberOfProducts;

    @Override
    public int runProcess(PipelineContext ctx) throws PipelineException {
        log.info("Started process of LoadBestMatchingProductsProcessor");

        //TODO: read keywords from context
        Keyword[] keywords = {};
        List<Product> products = findBestMatchingProducts(numberOfProducts, ctx, keywords);
        ctx.put(PRODUCTS, products);

        return 1;
    }

    public List<Product> findBestMatchingProducts(int numberOfProducts, PipelineContext ctx, Keyword... keywords) {
        log.info("Finding best matching products");

        if (keywords == null || keywords.length == 0) {
            return Collections.emptyList();
        }
        List<Product> productsWithKeywords = productService.findAllProductsContainingAtLeastOneKeyword(keywords);
        //This is priority queue, works like a stack, you can only access top element, but always has highest
        //element on top. The priority of elements is decided by comparator passed in constructor
        PriorityQueue<Map.Entry<Product, Long>> productsQueue =
                new PriorityQueue<>((o1, o2) -> Math.toIntExact(o2.getValue() - o1.getValue()));

        //This stream maps each product into an entry to priorityQueue with product as a key and number of keywords
        //it has from list as value
        productsWithKeywords.stream().filter(Objects::nonNull).map(product -> new HashMap.SimpleEntry<>(product,
            Arrays.stream(keywords).filter(Objects::nonNull).distinct().filter(product::containsKeyword).count()))
                .forEach(productsQueue::add);

        //This stream takes x first products from queue end puts them into list
        List<Product> bestMatchingProducts = IntStream.iterate(0, i -> i + 1).limit(numberOfProducts)
                .filter(i -> !productsQueue.isEmpty()).mapToObj(i -> productsQueue.poll().getKey())
                .filter(Objects::nonNull).collect(Collectors.toList());

        if (ctx != null) {
            saveKeywordsThatWereInAnyProduct(productsWithKeywords, Arrays.asList(keywords), ctx);
        }
        return bestMatchingProducts;
    }

    private void saveKeywordsThatWereInAnyProduct(List<Product> products, List<Keyword> keywords, PipelineContext ctx) {
        //This stream takes only these keywords from list that appear in at least one product
        List<Keyword> keywordsToBeSaved = keywords.stream().distinct().filter(keyword -> products.stream()
                .anyMatch(product -> product.containsKeyword(keyword))).collect(Collectors.toList());
        ctx.put(KEYWORDS_FOUND, keywordsToBeSaved);
    }
}
