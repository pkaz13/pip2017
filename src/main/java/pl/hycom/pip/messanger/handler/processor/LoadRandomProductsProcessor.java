package pl.hycom.pip.messanger.handler.processor;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.hycom.pip.messanger.pipeline.PipelineContext;
import pl.hycom.pip.messanger.pipeline.PipelineException;
import pl.hycom.pip.messanger.pipeline.PipelineProcessor;
import pl.hycom.pip.messanger.service.ProductService;

@Component
@Log4j2
public class LoadRandomProductsProcessor implements PipelineProcessor {

    public static final String PRODUCTS = "products";

    @Autowired
    private ProductService productService;

    @Value("${messenger.recommendation.products-amount:3}")
    private Integer productsAmount;

    @Override
    public int runProcess(PipelineContext ctx) throws PipelineException {

        log.info("Started process of LoadProductsProcessor");

        ctx.put(PRODUCTS, productService.getRandomProducts(productsAmount));

        return 1;
    }
}
