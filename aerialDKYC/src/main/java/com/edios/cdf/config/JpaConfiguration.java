package com.edios.cdf.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:app/DBConfig.properties" })
public class JpaConfiguration {

	@Autowired
	private Environment environment;

	@Bean("mysqlEntMan")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws IllegalStateException, PropertyVetoException {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan(new String[] { "com.edios.cdf","com.edios.csr"});
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		factoryBean.setJpaProperties(jpaProperties());
		factoryBean.setPersistenceUnitName("mysqlDBUnit");
		return factoryBean;
	}

	//@Bean
	public DataSource dataSource() throws IllegalStateException, PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setJdbcUrl(environment.getRequiredProperty("jdbc.url"));
		dataSource.setUser(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		dataSource.setIdleConnectionTestPeriod(Integer.parseInt(environment.getRequiredProperty("hibernate.c3p0.idle_test_period")));
		dataSource.setInitialPoolSize(Integer.parseInt(environment.getRequiredProperty("hibernate.c3p0.initialPoolSize")));
		dataSource.setCheckoutTimeout(Integer.parseInt(environment.getRequiredProperty("hibernate.c3p0.timeout")));
		dataSource.setMaxPoolSize(Integer.parseInt(environment.getRequiredProperty("hibernate.c3p0.max_size")));
		dataSource.setMinPoolSize(Integer.parseInt(environment.getRequiredProperty("hibernate.c3p0.min_size")));
		dataSource.setMaxStatements(Integer.parseInt(environment.getRequiredProperty("hibernate.c3p0.max_statements")));
		return dataSource;
	}

	@Bean("db1Tx")
	public PlatformTransactionManager transactionManager(@Qualifier("mysqlEntMan")EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	Properties jpaProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));

	/*	properties.put("hibernate.c3p0.idle_test_period",
				environment.getRequiredProperty("hibernate.c3p0.idle_test_period"));
		properties.put("hibernate.c3p0.initialPoolSize",
				environment.getRequiredProperty("hibernate.c3p0.initialPoolSize"));
		properties.put("hibernate.c3p0.timeout", environment.getRequiredProperty("hibernate.c3p0.timeout"));
		properties.put("hibernate.c3p0.max_size", environment.getRequiredProperty("hibernate.c3p0.max_size"));
		properties.put("hibernate.c3p0.min_size", environment.getRequiredProperty("hibernate.c3p0.min_size"));
		properties.put("hibernate.c3p0.max_statements",
				environment.getRequiredProperty("hibernate.c3p0.max_statements"));*/

		return properties;
	}

	JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendor = new HibernateJpaVendorAdapter();
		return hibernateJpaVendor;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	/*
	 * @Bean WebLogging logger() throws Exception { WebLogging logging = new
	 * WebLogging("AL", "userNotExistID", "app/AppConfiguration"); return logging; }
	 */
	
/*	@Bean("session2")
	   public LocalSessionFactoryBean getSessionFactory1() {
	      LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
	    Properties props = new Properties();
	      // Setting JDBC properties
	      props.put(DRIVER,environment.getRequiredProperty("ibm.jdbc.driverClassName"));
	      props.put(URL, environment.getRequiredProperty("ibm.jdbc.url"));
	      props.put(USER, environment.getRequiredProperty("ibm.jdbc.username"));
	      props.put(PASS, environment.getRequiredProperty("ibm.jdbc.password"));

	      // Setting Hibernate properties
	      props.put(DIALECT, environment.getRequiredProperty("ibm.hibernate.dialect"));
	      props.put(SHOW_SQL, environment.getRequiredProperty("ibm.hibernate.show_sql"));
	    //  props.put(HBM2DDL_AUTO, env.getProperty("hibernate.hbm2ddl.auto"));

	      // Setting C3P0 properties
	      props.put(C3P0_MIN_SIZE, environment.getRequiredProperty("hibernate.c3p0.min_size"));
	      props.put(C3P0_MAX_SIZE, environment.getRequiredProperty("hibernate.c3p0.max_size"));
	     // props.put(C3P0_ACQUIRE_INCREMENT, 
	         //   env.getProperty("hibernate.c3p0.acquire_increment"));
	      props.put(C3P0_TIMEOUT, environment.getRequiredProperty("hibernate.c3p0.timeout"));
	      props.put(C3P0_MAX_STATEMENTS, environment.getRequiredProperty("hibernate.c3p0.max_statements"));

	      factoryBean.setHibernateProperties(props);
	     factoryBean.setPackagesToScan("com.edios.csr");

	      return factoryBean;
	   }

	   @Bean("entityManagerIBM")
	   public HibernateTransactionManager getTransactionManager1() {
	      HibernateTransactionManager transactionManager = new HibernateTransactionManager();
	      transactionManager.setSessionFactory(getSessionFactory1().getObject());
	      return transactionManager;
	   }*/
	
	//IBM database connectivity
		@Bean("IBMEMF")
		public LocalContainerEntityManagerFactoryBean entityManagerFactoryIBM() throws IllegalStateException, PropertyVetoException {
			LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
			factoryBean.setDataSource(dataSourceIBM());
			factoryBean.setPackagesToScan(new String[] { "com.edios.cdf","com.edios.csr"});
			factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
			factoryBean.setJpaProperties(jpaPropertiesIBM());
			factoryBean.setPersistenceUnitName("ibmDBUnit");
			return factoryBean;
		}

		
		//@Bean("IBMDS")
		public DataSource dataSourceIBM() throws IllegalStateException, PropertyVetoException {
			ComboPooledDataSource dataSource = new ComboPooledDataSource();
			dataSource.setDriverClass(environment.getRequiredProperty("ibm.jdbc.driverClassName"));
			dataSource.setJdbcUrl(environment.getRequiredProperty("ibm.jdbc.url"));
			//dataSource.setUser(environment.getRequiredProperty("ibm.jdbc.username"));
			//dataSource.setPassword(environment.getRequiredProperty("ibm.jdbc.password"));
			dataSource.setIdleConnectionTestPeriod(Integer.parseInt(environment.getRequiredProperty("hibernate.c3p0.idle_test_period")));
			dataSource.setInitialPoolSize(Integer.parseInt(environment.getRequiredProperty("hibernate.c3p0.initialPoolSize")));
			dataSource.setCheckoutTimeout(Integer.parseInt(environment.getRequiredProperty("hibernate.c3p0.timeout")));
			dataSource.setMaxPoolSize(Integer.parseInt(environment.getRequiredProperty("hibernate.c3p0.max_size")));
			dataSource.setMinPoolSize(Integer.parseInt(environment.getRequiredProperty("hibernate.c3p0.min_size")));
			dataSource.setMaxStatements(Integer.parseInt(environment.getRequiredProperty("hibernate.c3p0.max_statements")));
			return dataSource;
		}

		@Bean("IBMTx")
		public PlatformTransactionManager transactionManagerIBM(@Qualifier("IBMEMF")EntityManagerFactory emf) throws IllegalStateException, PropertyVetoException {
			JpaTransactionManager transactionManager = new JpaTransactionManager();
			transactionManager.setEntityManagerFactory(emf);
			return transactionManager;
		}

		Properties jpaPropertiesIBM() {
			Properties properties = new Properties();
			properties.put("hibernate.dialect", environment.getRequiredProperty("ibm.hibernate.dialect"));
			properties.put("hibernate.show_sql", environment.getRequiredProperty("ibm.hibernate.show_sql"));

		/*	properties.put("hibernate.c3p0.idle_test_period",
					environment.getRequiredProperty("hibernate.c3p0.idle_test_period"));
			properties.put("hibernate.c3p0.initialPoolSize",
					environment.getRequiredProperty("hibernate.c3p0.initialPoolSize"));
			properties.put("hibernate.c3p0.timeout", environment.getRequiredProperty("hibernate.c3p0.timeout"));
			properties.put("hibernate.c3p0.max_size", environment.getRequiredProperty("hibernate.c3p0.max_size"));
			properties.put("hibernate.c3p0.min_size", environment.getRequiredProperty("hibernate.c3p0.min_size"));
			properties.put("hibernate.c3p0.max_statements",
					environment.getRequiredProperty("hibernate.c3p0.max_statements"));*/

			return properties;
		}
}
