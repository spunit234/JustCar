package com.edios.cdf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.edios.cdf.security.AccountAuthenticationProvider;
import com.edios.cdf.security.CustomBasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = { "com.edios.cdf","com.edios.csr" })
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	AccountAuthenticationProvider accountAuthenticationProvider;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) {
		System.out.println("In configureGlobal() function");
		auth.authenticationProvider(accountAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("In configure() function");
		http
		     .csrf().disable()
	         .authorizeRequests().antMatchers("/api/**").authenticated()
	         .anyRequest().permitAll()
	         .and()  .httpBasic()
	         .authenticationEntryPoint(getBasicAuthEntryPoint("111")).and().sessionManagement()
			 .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Bean
	public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(String str) {
		System.out.println("In getBasicAuthEntryPoint() function"+str);
		return new CustomBasicAuthenticationEntryPoint();
	}
	

	/*
	 * To allow Pre-flight [OPTIONS] request from browser
	 * 
	 * @Override public void configure(WebSecurity web) throws Exception {
	 * web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**"); }
	 */
}
