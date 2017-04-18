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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
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