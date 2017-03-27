package pl.hycom.pip.messanger;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles({"dev", "testdb"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Log4j2
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private KeywordService keywordService;

    private Product product1;
    private Product product2;
    private Product product3;

    @Before
    public void setUp() {
        product1 = new Product();
        product1.setName("name1");
        product1.setDescription("desc1");
        product1.setImageUrl("url1");

        product2 = new Product();
        product2.setName("name2");
        product2.setDescription("desc2");
        product2.setImageUrl("url2");

        product3 = new Product();
        product3.setName("name3");
        product3.setDescription("desc3");
        product3.setImageUrl("url3");
    }

    @Test
    public void addProductWithKeywordsTest() {
        log.info("Test of addProduct method from ProductService class");

        //preparation
        long productsCount = productService.count();

        Keyword keyword1 = new Keyword();
        Keyword keyword2 = new Keyword();
        keyword1.setWord("test1");
        keyword2.setWord("test2");

        LinkedHashSet<Keyword> keywords1 = new LinkedHashSet<>();
        keywords1.add(keyword1);
        keywords1.add(keyword2);
        product1.setKeywords(keywords1);

        //action
        productService.addProduct(product1);

        //assertion
        assertEquals(productsCount + 1, productService.findAllProducts().size());
        Product checkedProduct = productService.findProductById(product1.getId());
        assertEquals(2, checkedProduct.getKeywords().size());
    }

    @Test
    public void findProductByIdTest() {
        log.info("Test of findProductById method from ProductService class");

        //preparation
        productService.addProduct(product1);

        //assertion
        assertNotNull(productService.findProductById(product1.getId()));
        assertEquals("name1", productService.findProductById(product1.getId()).getName());
        assertEquals("desc1", productService.findProductById(product1.getId()).getDescription());
        assertEquals("url1", productService.findProductById(product1.getId()).getImageUrl());
    }

    @Test
    public void deleteProductByIdTest() {
        log.info("Test of deleteProduct method from ProductService class");

        //preparation
        long count = productService.count();
        productService.addProduct(product1);

        //assertion
        assertEquals(count + 1, productService.count());
        productService.deleteProduct(product1.getId());
        assertEquals(count, productService.count());
    }

    @Test
    public void updateProductNameTest() {
        log.info("Test of updateProductName method from ProductService class");

        //preparation
        productService.addProduct(product1);

        //action
        productService.updateProductName(product1.getId(), "zażółć gęślą jaźń");

        //assertion
        assertEquals("zażółć gęślą jaźń", productService.findProductById(product1.getId()).getName());
    }

    @Test
    public void getRandomElements() {
        log.info("Test of getRandomElements method from ProductService class");

        //preparation
        productService.addProduct(product1);
        productService.addProduct(product2);
        productService.addProduct(product3);

        //assertion
        assertEquals(3, productService.getRandomProducts(3).size());
        assertEquals(2, productService.getRandomProducts(2).size());
    }

    @After
    public void cleanAll() {
        productService.deleteAllProducts();
    }
}
