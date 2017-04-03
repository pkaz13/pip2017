package pl.hycom.pip.messanger.controller;

import lombok.extern.log4j.Log4j2;
import org.apache.catalina.filters.CorsFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import pl.hycom.pip.messanger.MessengerRecommendationsApplication;
import pl.hycom.pip.messanger.model.Product;
import pl.hycom.pip.messanger.repository.ProductRepository;
import pl.hycom.pip.messanger.service.ProductService;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


/**
 * Created by piotr on 03.04.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessengerRecommendationsApplication.class)
@ActiveProfiles("dev")
@WebAppConfiguration
@Log4j2
public class ProductControllerTest {


    ///for both
    private MockMvc mockMvc;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    ////////////integration tests
    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private ProductRepository productRepository;

    private List<Product> products = new ArrayList<>();

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }


    /////////////unit tests

//    @Mock
//    private ProductService productService;
//
//    @Autowired
//    @InjectMocks
//    private ProductController productController;

    @Before
    public void setUp() {
        ////unit tests
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("/main/resources/templates/");
//        viewResolver.setSuffix(".html");
//
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(productController).setViewResolvers(viewResolver).addFilters(new CorsFilter()).build();


        ////integration tests
        this.mockMvc = webAppContextSetup(applicationContext).build();

        Product product1;
        Product product2;

        product1 = new Product();
        product1.setId(1);
        product1.setName("name1");
        product1.setDescription("desc1");
        product1.setImageUrl("url1");

        product2 = new Product();
        product2.setId(2);
        product2.setName("name2");
        product2.setDescription("desc2");
        product2.setImageUrl("url2");

        products.add(productRepository.save(product1));
        products.add(productRepository.save(product2));

    }

//    @Test
//    public void mockCreationTest() {
//        assertNotNull("ProductService should not be null", productService);
//    }
//
//    @Test
//    public void productControllerIsNotNull() {
//        assertNotNull("Product Controller should not be null", productController);
//    }

//    @Test
//    public void findAllProductsTest() throws Exception {
//
//        when(productService.findAllProducts()).thenReturn(products);
//
//        mockMvc.perform(get("/admin/products"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(contentType))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].id", is(1)))
//                .andExpect(jsonPath("$[0].name", is("name1")))
//                .andExpect(jsonPath("$[1].id", is(2)))
//                .andExpect(jsonPath("$[1].name", is("name2")));
//
//        verify(productService, times(1)).findAllProducts();
//        verifyNoMoreInteractions(productService);
//    }

    @Test
    public void pageNotFoundTest() throws Exception {

        String user = "tester";

        mockMvc.perform(get("/"+user+"/products/")
                .content(this.json(new Product()))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void contentIsOkTest() throws Exception {
        mockMvc.perform(get("/admin/products")
                .content(this.json(new Product()))
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllProducts() throws Exception {
        mockMvc.perform(get("/admin/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(this.products.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(this.products.get(0).getName())))
                .andExpect(jsonPath("$[1].id", is(this.products.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(this.products.get(1).getName())));
    }










}
