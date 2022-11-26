package dev.dmohindru.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/myAccount","/myBalance","/myLoans","/myCards").authenticated()
                .antMatchers("/notices","/contact").permitAll()
                .and().formLogin()
                .and().httpBasic();

        return http.build();
    }
}
