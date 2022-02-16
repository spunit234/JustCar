package com.edios.cdf.config;

import javax.servlet.Filter;
import javax.servlet.ServletContext;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.edios.cdf.util.SessionManager;

public class AppIntializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		System.out.println("In getRootConfigClasses() function");
		return new Class[] { (WebSecurityConfig.class) , (JpaConfiguration.class)};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		System.out.println("In getServletConfigClasses() function");
		return new Class[] { WebMvcConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		System.out.println("In getServletMappings() function");
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		System.out.println("In getServletFilters() function");
		Filter[] singleton = { new CORSFilter() };
		return singleton;
	}
	
	 @Override
     protected void registerDispatcherServlet(ServletContext servletContext) {
		 System.out.println("In getServletFilters() function");
        super.registerDispatcherServlet(servletContext);
        servletContext.addListener(new SessionManager());
	 }
	
//	 @Override
//    public void onStartup(ServletContext container) {
//        container.addListener(SessionManager.class);
//    }

}
