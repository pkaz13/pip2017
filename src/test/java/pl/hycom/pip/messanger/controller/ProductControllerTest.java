package pl.hycom.pip.messanger.controller;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import pl.hycom.pip.messanger.MessengerRecommendationsApplication;
import pl.hycom.pip.messanger.model.Product;
import pl.hycom.pip.messanger.service.ProductService;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


/**
 * Created by piotr on 03.04.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessengerRecommendationsApplication.class)
@ActiveProfiles({ "dev", "testdb" })
@WebAppConfiguration
@Log4j2
public class ProductControllerTest {


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ProductService productService;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        Product product1;
        Product product2;

        product1 = new Product();
        product1.setName("name1");
        product1.setDescription("desc1");
        product1.setImageUrl("url1");

        product2 = new Product();
        product2.setName("name2");
        product2.setDescription("desc2");
        product2.setImageUrl("url2");

        productService.addProduct(product1);
        productService.addProduct(product2);
        //productRepository.save(product1);
        //productRepository.save(product2);

    }

    @Test
    public void pageNotFoundTest() throws Exception {

         String user = "tester";

         mockMvc.perform(get("/"+user+"/products/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void pageFoundTest() throws Exception {

        mockMvc.perform(get("/admin/products"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllProducts() throws Exception {

        mockMvc.perform(get("/admin/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(model().attribute("products",hasSize(2)))
                .andExpect(model().attribute("products",hasItem(allOf(
                        hasProperty("id", is(1)),
                        hasProperty("name", is("name1")),
                        hasProperty("description", is("desc1")),
                        hasProperty("imageUrl", is("url1"))
                ))))
                .andExpect(model().attribute("products",hasItem(allOf(
                hasProperty("id", is(2)),
                hasProperty("name", is("name2")),
                hasProperty("description", is("desc2")),
                hasProperty("imageUrl", is("url2"))
        ))));
    }

    @Test
    public void deleteById() throws Exception {

        mockMvc.perform(get("/admin/products/2/delete"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/admin/products"));
                //.andExpect(model().attribute("products", hasSize(1)));
    }

//    @Test
//    public void addOrUpdateTest() throws Exception {
//
//        mockMvc.perform(post("/admin/products"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("products"));
//    }

//    @After
//    public void cleanAll() {
//        productService.deleteAllProducts();
//    }










}
