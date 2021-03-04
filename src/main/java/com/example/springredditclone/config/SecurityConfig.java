package com.example.springredditclone.config;

import com.example.springredditclone.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Class: Security Configuration
 * this class holds the complete security configuration of our application,
 * that’s why the class is named SecurityConfig.java
 */
@EnableWebSecurity //This is the main annotation which enables the Web Security module in our Project.
/**
 * WebSecurityConfigurerAdapter:
 * This is the base class for our SecurityConfig class,
 * it provides us the default security configurations,
 * which we can override in our SecurityConfig and customize them.
 */
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //As UserDetailsService is an interface, we have to provide an implementation where it fetches the user information from our MySQL Database.
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    //We have defined a bean for AuthenticationManager and... (check configureGlobal)
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * configure()
     * Next, we have the configure method which we have overridden from the base class which takes HttpSecurity as an argument.
     * Here, we are configuring Spring to allow all the requests which match the endpoint “/api/auth/**”,
     * as these endpoints are used for authentication and registration we don’t expect the user to be authenticated at that point of time.
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated();
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    //...configured AuthenticationManagerBuilder to use the UserDetailsService interface to check for user information.
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * passwordEncoder()
     * Now before storing the user in the database, we ideally want to encode the passwords.
     * One of the best hashing algorithms for passwords is the Bcrypt Algorithm.
     * We are using the BCryptPasswordEncoder class provided by Spring Security.
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
