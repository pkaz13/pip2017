package pl.hycom.pip.messanger;

import lombok.extern.log4j.Log4j2;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.hycom.pip.messanger.model.Keyword;
import pl.hycom.pip.messanger.model.Product;
import pl.hycom.pip.messanger.service.KeywordService;
import pl.hycom.pip.messanger.service.ProductService;

import java.util.LinkedHashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Log4j2
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private KeywordService keywordService;

    @Test
    public void createProductTest() {
        long count = productService.count();

        Product product1 = new Product();
        product1.setName("name");
        product1.setDescription("desc");
        product1.setImageUrl("url");

        // Checking if keywords are being added correctly. To be removed or fixed
        Keyword keyword1 = new Keyword();
        Keyword keyword2 = new Keyword();
        keyword1.setWord("test1");
        keyword2.setWord("test2");

        LinkedHashSet<Keyword> keywords1 = new LinkedHashSet<>();
        keywords1.add(keyword1);
        keywords1.add(keyword2);
        product1.setKeywords(keywords1);
        // End of keyword checking
        productService.addProduct(product1);
        log.info("Test of addProduct method from ProductService class");
        assertEquals(count + 1, productService.findAllProducts().size());

        // Checking if keywords are being added correctly. To be removed or fixed
        Product checkedProduct = productService.findProductById(product1.getId());
        assertEquals(2, checkedProduct.getKeywords().size());

        // assertEquals(2, keywordService.findAllKeywords().size());
        productService.deleteProduct(product1.getId());
        // assertEquals(2, keywordService.findAllKeywords().size());
    }

    @Test
    public void findProductByIdTest() {
        Product product1 = new Product();
        product1.setName("name");
        product1.setDescription("desc");
        product1.setImageUrl("url");
        productService.addProduct(product1);

        log.info("Test of findProductById method from ProductService class");
        assertNotNull(productService.findProductById(product1.getId()));
        assertEquals("name", productService.findProductById(product1.getId()).getName());
        assertEquals("desc", productService.findProductById(product1.getId()).getDescription());
        assertEquals("url", productService.findProductById(product1.getId()).getImageUrl());

        productService.deleteProduct(product1.getId());
    }

    @Test
    public void deleteProductByIdTest() {
        long count = productService.count();

        Product product1 = new Product();
        product1.setName("name");
        product1.setDescription("desc");
        product1.setImageUrl("url");

        log.info("Test of deleteProduct method from ProductService class");
        productService.addProduct(product1);

        assertEquals(count + 1, productService.count());

        productService.deleteProduct(product1.getId());
        assertEquals(count, productService.count());
    }

    @Test
    public void updateProductNameTest() {
        log.info("Test of updateProductName method from ProductService class");
        productService.updateProductName(1, "zażółć gęślą jaźń");
        assertEquals("zażółć gęślą jaźń", productService.findProductById(1).getName());
    }

    @Test
    public void getRandomElements() {
        assertEquals(3, productService.getRandomProducts(3).size());
        assertEquals(2, productService.getRandomProducts(2).size());
    }
}
