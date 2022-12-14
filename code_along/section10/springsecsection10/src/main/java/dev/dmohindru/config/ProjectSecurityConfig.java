package dev.dmohindru.config;


import dev.dmohindru.filter.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;


@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
       http
               // This code tells spring boot not to generate JSESSIONID for session management
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
               .cors().configurationSource(new CorsConfigurationSource() {
                   @Override
                   public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                       CorsConfiguration config = new CorsConfiguration();
                       config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                       config.setAllowedMethods(Collections.singletonList("*"));
                       config.setAllowCredentials(true);
                       config.setAllowedHeaders(Collections.singletonList("*"));
                       // expose Authorization header so that back end server can send JWT token in header
                       config.setExposedHeaders(Arrays.asList("Authorization"));
                       config.setMaxAge(3600L);
                       return config;
                   }
               })
               .and()
               .csrf().ignoringAntMatchers("/contact", "/register")
               .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
               .and()
               //.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
               .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
               .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
               .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
               .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
               .authorizeRequests()
               // endpoint /myAccount, /myBalance, /myLoans, /myCards need to be authenticated and have respective
               // authorization
               // code example with hasAuthority
               /*.antMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
               .antMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT", "VIEWBALANCE")
               .antMatchers("/myLoans").hasAuthority("VIEWLOANS")
               .antMatchers("/myCards").hasAuthority("VIEWCARDS")*/
               // code example with hasRole
               .antMatchers("/myAccount").hasRole("USER")
               .antMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
               .antMatchers("/myLoans").authenticated()
               .antMatchers("/myCards").hasRole("USER")
               .antMatchers( "/user").authenticated()
               .antMatchers("/notices", "/contact", "/register").permitAll()
               .and().formLogin()
               .and().httpBasic();

       return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
