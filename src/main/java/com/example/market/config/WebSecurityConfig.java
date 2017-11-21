package com.example.market.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Author: 朱庆
 * Tool: IntelliJ IDEA
 * info:
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("123456")
                .roles("ADMIN");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 配置SpringSecurity忽略静态资源
        web.ignoring().antMatchers("/templates/**","/css/**", "/js/**","/images/**", "/ftl/**", "**/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/", "/login.html", "/login", "/logout","/css/**", "/js/**","/images/**", "/ftl/**", "**/favicon.ico")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/index.html",true)
                .and()
                .rememberMe()
                .rememberMeParameter("rememberMe")
                .rememberMeCookieName("rememberMe")
                .tokenValiditySeconds(604800)
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("rememberMe")
                .logoutSuccessUrl("/login.html")
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredUrl("/kickout.html");

    }

}
