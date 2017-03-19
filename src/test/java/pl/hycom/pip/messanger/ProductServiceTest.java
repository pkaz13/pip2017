package pl.hycom.pip.messanger;

import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.hycom.pip.messanger.model.Product;
import pl.hycom.pip.messanger.service.ProductService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Log4j2
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Before
    public void setUp() {
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("name");
        product1.setDescription("desc");
        product1.setImageUrl("url");
        productService.addProduct(product1);
        Product product = new Product();
        product.setId(2);
        product.setName("name");
        product.setDescription("desc");
        product.setImageUrl("url");
        productService.addProduct(product);

    }

    @Test
    public void createProductTest() {
        Product product = new Product();
        product.setId(3);
        product.setName("name");
        product.setDescription("desc");
        product.setImageUrl("url");
        productService.addProduct(product);
        log.info("Test of addProduct method from ProductService class");
        assertEquals(3, productService.findAllProducts().size());
    }

    @Test
    public void findProductByIdTest() {
        log.info("Test of findProductById method from ProductService class");
        assertNotNull(productService.findProductById(1));
        assertEquals("name", productService.findProductById(1).getName());
        assertEquals("desc", productService.findProductById(1).getDescription());
        assertEquals("url", productService.findProductById(1).getImageUrl());
    }

    @Test
    public void deleteProductByIdTest() {
        log.info("Test of deleteProduct method from ProductService class");
        productService.deleteProduct(2);
        assertEquals(2, productService.findAllProducts().size());
    }

    @Test
    public void updateProductNameTest() {
        log.info("Test of updateProductName method from ProductService class");
        productService.updateProductName(1, "zażółć gęślą jaźń");
        assertEquals("zażółć gęślą jaźń", productService.findProductById(1).getName());
    }

    @Test
    public void getFewProductsTest() {
    assertEquals(2,productService.getFewProducts(2).size());
    }
}
