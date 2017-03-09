package pl.hycom.pip.messanger.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by maciek on 06.03.17.
 */

//TODO
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = MessengerIntegrationConfig.class)
public class MessengerIntegrationConfigTest {
    @Autowired ConfigService configService;

    @Test
    public void configServiceShouldNotBeNull() {
        assertNotNull(configService);
    }

    @Test
    public void propertyTest() {
        assertEquals("token", configService.getVerifyToken());
        assertEquals("EAAImJ54xVrcBAJp5Aw1dU1zIPSw92mprMUo5QIRbux0WxrfKZCayfyEBJMmTJXoqrSfSglcUBV39YRvPZBo2jAaQu2QyiyA5vdTkCBbJE9NOAjpiM33PQ7sS0sIaMSsR6COd5IWihqYSjhTZBdQxfPqE7oliQ95lFKknSCUqQZDZD",
                    configService.getPageAccessToken());
        assertEquals("d44fb500a9e69c572a8fa8d01fab8218", configService.getAppSecret());
    }
}
