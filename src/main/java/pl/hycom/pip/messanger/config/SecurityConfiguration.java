package pl.hycom.pip.messanger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String ROLE_ADMIN = "ADMIN";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/db-admin/console/**").permitAll()
                .antMatchers("/admin/**").hasRole(ROLE_ADMIN)
                .anyRequest().authenticated()

                .and()
                .formLogin().loginPage("/login").permitAll()

                //TODO: usunąć kiedy zrezygnujemy z consoli do łączenia się z H2
                .and()
                .csrf().ignoringAntMatchers("/db-admin/console/**")

                .and()
                .headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        authManagerBuilder.inMemoryAuthentication()
                .withUser("admin").password("admin").roles(ROLE_ADMIN);
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/css/**", "/js/**", "/webhook");
    }
}