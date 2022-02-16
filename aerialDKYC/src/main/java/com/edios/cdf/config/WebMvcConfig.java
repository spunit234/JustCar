package com.edios.cdf.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.edios.cdf.interceptors.SessionManagementInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.edios.cdf.controller", "com.edios.csr.controller" })
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
		registry.addResourceHandler("/assets/**").addResourceLocations("/static/assets/");
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("app/AppLogin", "app/message");
		return messageSource;
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(1000000000);
		return multipartResolver;
	}
	
	@Bean
    SessionManagementInterceptor httpApiRequestInterceptor() {
         return new SessionManagementInterceptor();
    }
 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       
    	registry.addInterceptor(httpApiRequestInterceptor())
    	.addPathPatterns("/api/**")
    	.excludePathPatterns( new String[] {"/static/**","/assets/**"});
    }
 
   
}
	
	
	 

