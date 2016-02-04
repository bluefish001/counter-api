package com.optus.wordcount.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	 @Autowired
	    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
	        auth.inMemoryAuthentication().withUser("optus").password("candidates").roles("ADMIN");
	    }
	     
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	  
	      http
	      .csrf().disable()
	      .authorizeRequests()
	        .antMatchers(HttpMethod.POST, "/**").authenticated()
	        .antMatchers(HttpMethod.GET, "/**").authenticated()
	        .antMatchers(HttpMethod.DELETE, "/api/**").authenticated()
	        .anyRequest().permitAll()
	        .and()
	      .httpBasic().and()
	      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	    }

}
