package pl.hycom.pip.messanger.controller;

import com.github.messenger4j.profile.MessengerProfileClient;
import javafx.application.Application;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.filters.CorsFilter;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import pl.hycom.pip.messanger.MessengerRecommendationsApplication;


import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Piotr on 01.04.2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessengerRecommendationsApplication.class)
@ActiveProfiles("dev")
@WebAppConfiguration
@Log4j2
public class AdminControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @Mock
    private MessengerProfileClient profileClient;

    @Autowired
    @InjectMocks
    private AdminController adminController;

    @Before
    public void setUp() {
        //this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).addFilters(new CorsFilter()).build();
    }

    @Test
    public void mockCreationTest() {
        assertNotNull("MessengerProfileClient should not be null",profileClient);
    }

    @Test
    public void adminControllerIsNotNullTest() {
        assertNotNull("AdminController should not be null",adminController);
    }

    @Test
    public void shouldReturnAdminViewTest() {
        ModelAndView modelAndView = adminController.admin();
        Assert.assertThat("View name should be 'admin'",modelAndView.getViewName(), Is.is("admin"));
    }




}



