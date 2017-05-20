package com.example.demo.configuration;

import java.util.Properties;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ComponentScan(basePackages = { "com.example.demo" })
@EnableJpaRepositories(basePackages = {
		"com.example.demo" }, entityManagerFactoryRef = JPAConfiguration.ENTITY_MANAGER_NAME, transactionManagerRef = JPAConfiguration.TRANSACTION_MANAGER_NAME)
@PropertySource("classpath:database.properties")
public class JPAConfiguration {

	public static final String ENTITY_MANAGER_NAME = "entityManagerFactory";
	public static final String TRANSACTION_MANAGER_NAME = "transactionManager";
	public static final String PERSITENCE_UNIT_NAME = "persistence-unit";

	private @Value("${example.datasource.url}") String datasourceUrl;
	private @Value("${example.datasource.username}") String username;
	private @Value("${example.datasource.password}") String password;
	private @Value("${example.datasource.driverClassName}") String driverClassName;
	private @Value("${example.datasource.max-active}") Integer maxActive;
	private @Value("${example.datasource.max-idle}") Integer maxIdle;
	private @Value("${example.datasource.min-idle}") Integer minIdle;
	private @Value("${example.datasource.max-wait}") Integer maxWait;
	private @Value("${example.datasource.initial-size}") Integer initialSize;
	private @Value("${example.datasource.schema}") String defaultSchema;

	@Bean
	@Primary
	public DataSource exampleDataSource() {

		PoolProperties properties = new PoolProperties();
		properties.setUrl(datasourceUrl);
		properties.setUsername(username);
		properties.setPassword(password);
		properties.setDriverClassName(driverClassName);
		properties.setInitialSize(initialSize);
		properties.setMaxActive(maxActive);
		properties.setMaxIdle(maxIdle);
		properties.setMaxWait(maxWait);
		properties.setMinIdle(minIdle);

		return new DataSource(properties);

	}

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean exampleEntityManagerFactory() {

		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		Properties properties = new Properties();
		properties.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.SingletonEhCacheProvider");
		properties.setProperty("hibernate.cache.use_second_level_cache", "true");
		properties.setProperty("hibernate.cache.use_query_cache", "true");
		properties.setProperty("hibernate.cache.use_structured_entries", "true");
		properties.setProperty("hibernate.cache.region.factory_class",
				"org.hibernate.cache.ehcache.EhCacheRegionFactory");
		properties.setProperty("jadira.usertype.autoRegisterUserTypes", "true");
		properties.setProperty("jadira.usertype.databaseZone", "jvm");
		properties.setProperty("javax.persistence.sharedCache.mode", "ENABLE_SELECTIVE");
		properties.setProperty("hibernate.generate_statistics", "false");
		properties.setProperty("hibernate.hbm2ddl.auto", "create");
		properties.setProperty("hibernate.default_schema", defaultSchema);

		entityManager.setPackagesToScan("com.example.demo.model");
		entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManager.setPersistenceUnitName(PERSITENCE_UNIT_NAME);
		entityManager.setJpaProperties(properties);
		entityManager.setDataSource(exampleDataSource());

		return entityManager;
	}

	@Bean
	@Primary
	public PlatformTransactionManager defaultTransactionManager() {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(exampleEntityManagerFactory().getObject());
		return transactionManager;
	}

}
