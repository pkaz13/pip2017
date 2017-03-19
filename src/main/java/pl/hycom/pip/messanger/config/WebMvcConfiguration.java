package pl.hycom.pip.messanger.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import nz.net.ultraq.thymeleaf.decorators.strategies.GroupingStrategy;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Configuration
    @ConditionalOnClass(name = { "nz.net.ultraq.thymeleaf.LayoutDialect" })
    protected static class ThymeleafWebLayoutConfiguration {
        @Bean
        public LayoutDialect layoutDialect() {
            return new LayoutDialect(new GroupingStrategy());
        }
    }
}
