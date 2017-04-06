package pl.hycom.pip.messanger.handler.processor;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.hycom.pip.messanger.model.Product;
import pl.hycom.pip.messanger.pipeline.PipelineContext;
import pl.hycom.pip.messanger.pipeline.PipelineException;
import pl.hycom.pip.messanger.pipeline.PipelineProcessor;
import pl.hycom.pip.messanger.service.ProductService;

import java.util.List;

@Component
@Log4j2
public class LoadBestMatchingProductsProcessor implements PipelineProcessor{

    private static final String PRODUCTS = "products";

    @Autowired
    private ProductService productService;

    @Value("${messenger.recommendation.products-amount:3}")
    private Integer productsAmount;

    @Override
    public int runProcess(PipelineContext ctx) throws PipelineException {
        log.info("Started process of LoadBestMatchingProductsProcessor");

        //TODO: change to findBestMatchingProducts
        List<Product> products = productService.getRandomProducts(productsAmount);
        ctx.put(PRODUCTS, products);

        return 1;
    }
}
