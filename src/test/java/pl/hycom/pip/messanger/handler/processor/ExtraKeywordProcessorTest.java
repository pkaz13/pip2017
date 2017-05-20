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

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.hycom.pip.messanger.model.Keyword;
import pl.hycom.pip.messanger.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by szale_000 on 2017-04-06.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ExtraKeywordProcessorTest {

    ExtraKeywordProcessor sut = new ExtraKeywordProcessor();

    private List<Keyword> initKeywords() {
        return Arrays.asList(new Keyword("K1"), new Keyword("K2"), new Keyword("K3"), new Keyword("K4"), new Keyword("K5"));
    }

    private Product createProduct(List<Keyword> keywords) {
        Product product = new Product();
        product.setId(1);
        product.setName("Foo");
        product.setDescription("Foo");
        product.setImageUrl("Foo");
        product.setKeywords(new HashSet<>(keywords));
        return product;
    }

    @Test
    public void findsExactlyInTheMiddle() throws Exception {
        // given
        List<Keyword> keywords = initKeywords();
        List<Product> products = new ArrayList<>();
        products.add(createProduct(Arrays.asList(keywords.get(0), keywords.get(1), keywords.get(2))));
        products.add(createProduct(Arrays.asList(keywords.get(0), keywords.get(1), keywords.get(4))));
        products.add(createProduct(Arrays.asList(keywords.get(0), keywords.get(3), keywords.get(4))));
        products.add(createProduct(Arrays.asList(keywords.get(1), keywords.get(2), keywords.get(3))));
        products.add(createProduct(Arrays.asList(keywords.get(0), keywords.get(1), keywords.get(2))));
        products.add(createProduct(Arrays.asList(keywords.get(0), keywords.get(2), keywords.get(4))));
        // when
        Keyword foundKeyword = sut.findKeyKeyword(products);
        // then
        Assertions.assertThat(foundKeyword).isEqualTo(keywords.get(4));
    }

    @Test
    public void findsClosestToMiddle() throws Exception {
        // given
        List<Keyword> keywords = initKeywords();
        List<Product> products = new ArrayList<>();
        products.add(createProduct(Arrays.asList(keywords.get(0), keywords.get(1), keywords.get(2))));
        products.add(createProduct(Arrays.asList(keywords.get(0), keywords.get(1), keywords.get(2))));
        products.add(createProduct(Arrays.asList(keywords.get(0), keywords.get(1), keywords.get(2))));
        products.add(createProduct(Arrays.asList(keywords.get(0), keywords.get(1), keywords.get(2))));
        products.add(createProduct(Arrays.asList(keywords.get(0), keywords.get(1), keywords.get(2))));
        products.add(createProduct(Arrays.asList(keywords.get(3), keywords.get(1), keywords.get(2))));
        products.add(createProduct(Arrays.asList(keywords.get(0), keywords.get(3), keywords.get(2))));
        products.add(createProduct(Arrays.asList(keywords.get(0), keywords.get(1), keywords.get(3))));

        // when
        Keyword foundKeyword = sut.findKeyKeyword(products);
        // then
        Assertions.assertThat(foundKeyword).isEqualTo(keywords.get(3));
    }

}