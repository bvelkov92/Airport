package com.my.airportproject.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
public class SecurityPasswordEncoder {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity ) throws Exception {
        httpSecurity
                .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/users/login", "/users/register", "/flights/flight-list").anonymous()
                .antMatchers("/flights/flight-add", "/planes/add-plane","/planes/plane-list")
                .hasAnyRole("FIRM", "ADMIN")
                .antMatchers("/users/roles/{id}", "/users/users-list", "/flights/remove/{id}", "/users/removeuser/{id}").hasRole("ADMIN")
                .antMatchers("/tickets/ticket-list").authenticated()
                .and()
                .formLogin()
                .loginPage("/users/login")
                .usernameParameter("email")
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                .defaultSuccessUrl("/")
                .failureForwardUrl("/users/login-error")
                .and()
                .logout()
                .logoutUrl("/users/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true).and()
                .csrf().disable();

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new Pbkdf2PasswordEncoder();
    }

}
