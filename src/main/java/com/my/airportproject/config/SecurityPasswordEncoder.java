package com.my.airportproject.config;
import com.my.airportproject.service.AuthService;
import com.my.airportproject.service.PatfinderUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
public class SecurityPasswordEncoder {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/").anonymous()
                .antMatchers("/users/login", "/users/register").permitAll()
                .antMatchers("/flights/flight-add", "/planes/add-plane", "/users/options","/planes/plane-list")
                .hasAnyRole("FIRM", "ADMIN")
                .antMatchers("/users/roles", "users/users-list").hasRole("ADMIN")
                .antMatchers("/flights/flight-list", "/welcome").authenticated()
                .and()
                .formLogin()
                .loginPage("/users/login")
                .usernameParameter("username")
                .passwordParameter("password".toLowerCase())
                .defaultSuccessUrl("/welcome")
                .failureForwardUrl("/users/login-error")
                .and()
                .logout()
                .logoutUrl("/users/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new Pbkdf2PasswordEncoder();
    }

}
