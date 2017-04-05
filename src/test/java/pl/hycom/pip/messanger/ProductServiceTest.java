package pl.hycom.pip.messanger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashSet;
import java.util.List;

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
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;
import pl.hycom.pip.messanger.model.Keyword;
import pl.hycom.pip.messanger.model.Product;
import pl.hycom.pip.messanger.service.KeywordService;
import pl.hycom.pip.messanger.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles({ "dev", "testdb" })
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
    private Keyword keyword;
    private Keyword keyword1;
    private Keyword keyword2;
    private Keyword keyword3;
    private Keyword keyword4;

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

        keyword = new Keyword();
        keyword.setWord("test_keyword");

        keyword1 = new Keyword();
        keyword2 = new Keyword();
        keyword3 = new Keyword();
        keyword4 = new Keyword();

        keyword1.setWord("test1");
        keyword2.setWord("test2");
        keyword3.setWord("test3");
        keyword4.setWord("test4");
    }

    private void addKeywordsToProduct1() {
        LinkedHashSet<Keyword> keywords1 = new LinkedHashSet<>();
        keywords1.add(keyword1);
        keywords1.add(keyword2);
        product1.setKeywords(keywords1);
    }

    private void addKeywordsToProducts() {
        keywordService.addKeyword(keyword1);
        keywordService.addKeyword(keyword2);
        keywordService.addKeyword(keyword3);
        keywordService.addKeyword(keyword4);

        product1.getKeywords().add(keyword1);
        product1.getKeywords().add(keyword2);
        product1.getKeywords().add(keyword3);
        product2.getKeywords().add(keyword2);
        product3.getKeywords().add(keyword3);
    }

    @Test
    public void addProductWithKeywordsTest() {
        log.info("Test of addProduct method from ProductService class");

        // preparation
        long productsCount = productService.count();
        addKeywordsToProduct1();

        // action
        productService.addProduct(product1);

        // assertion
        assertEquals("size of returned productsList should be greater by one than productsCount", productsCount + 1, productService.findAllProducts().size());
        Product checkedProduct = productService.findProductById(product1.getId());
        assertEquals("size of returned keywordsList should be equal to 2", 2, checkedProduct.getKeywords().size());
    }

    @Test
    public void findProductByIdTest() {
        log.info("Test of findProductById method from ProductService class");

        // preparation
        productService.addProduct(product1);
        Product product = productService.findProductById(product1.getId());

        // assertion
        assertNotNull("added product shouldn't be null", product);
        assertEquals("name of product1 should be equal to 'name1'", "name1", product.getName());
        assertEquals("description of product1 should be equal to 'desc1'", "desc1", product.getDescription());
        assertEquals("url of product1 should be equal to 'url1'", "url1", product.getImageUrl());
    }

    @Test
    public void deleteProductByIdTest() {
        log.info("Test of deleteProduct method from ProductService class");

        // preparation
        long count = productService.count();
        productService.addProduct(product1);

        // assertion
        assertEquals("after adding product: size of returned productList should be greater by one than count", count + 1, productService.count());
        productService.deleteProduct(product1.getId());
        assertEquals("after deleting product: size of returned productList should be equal to count", count, productService.count());
    }

    @Test
    public void updateProductNameTest() {
        log.info("Test of updateProductName method from ProductService class");

        // preparation
        Product addedProduct = productService.addProduct(product1);
        String newName = "zażółć gęślą jaźń";
        addedProduct.setName(newName);

        // action
        productService.updateProduct(addedProduct);

        // assertion
        assertEquals("name of product1 should be updated and equal to " + newName, newName, productService.findProductById(addedProduct.getId()).getName());
    }

    @Test
    public void getRandomElements() {
        log.info("Test of getRandomElements method from ProductService class");

        // preparation
        productService.addProduct(product1);
        productService.addProduct(product2);
        productService.addProduct(product3);

        // assertion
        assertEquals("size of returned list should be equal to the value of getRandomProducts parameter - 3", 3, productService.getRandomProducts(3).size());
        assertEquals("size of returned list should be equal to the value of getRandomProducts parameter - 2", 2, productService.getRandomProducts(2).size());
    }

    @Test
    public void addKeywordToProductTest() {
        // preparation
        addKeywordsToProduct1();
        long initialKeywordCount = product1.getKeywords().size();
        productService.addProduct(product1);
        keywordService.addKeyword(keyword);
        keyword = keywordService.findKeywordById(keyword.getId());

        // action
        product1 = productService.addKeywordsToProduct(product1.getId(), keyword);

        // assertion
        assertEquals("product1 should have one more keyword than at the beginning of the test", initialKeywordCount + 1, product1.getKeywords().size());
        assertTrue("product1 should contain keyword", product1.getKeywords().contains(keyword));
    }

    @Test
    public void removeKeywordFromProductTest() {
        // preparation
        addKeywordsToProduct1();
        long initialKeywordCount = product1.getKeywords().size();
        productService.addProduct(product1);

        // action
        product1 = productService.removeKeywordsFromProduct(product1.getId(), keyword1);

        // assertion
        assertEquals("product1 should have one less keyword than at the beginning of the test", initialKeywordCount - 1, product1.getKeywords().size());
        assertFalse("product1 should not contain keyword", product1.getKeywords().contains(keyword));
    }

    @Test
    @Transactional
    public void findAllProductsContainingAtLeastOneKeywordTest() {
        // preparation
        addKeywordsToProducts();

        // action
        productService.addProduct(product1);
        productService.addProduct(product2);
        productService.addProduct(product3);
        List<Product> productsWithKeywords = productService.findAllProductsContainingAtLeastOneKeyword(keyword1, keyword2, keyword3);

        // assertion
        assertEquals("list should contain all 3 products", 3, productsWithKeywords.size());
    }

    @After
    public void cleanAll() {
        productService.deleteAllProducts();
        keywordService.deleteAllKeywords();
    }
}
