package com.employee;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.OpenJpaVendorAdapter;

@EnableJpaRepositories(basePackages="com.employee.repository", entityManagerFactoryRef="entityManagerFactory")
@Configuration
public class PersistenceConfig {
	private static final Logger LOG = LoggerFactory.getLogger(PersistenceConfig.class);

	@Primary
	@Bean(name = "entityManagerFactory")
	LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource") DataSource dataSource,
			@Qualifier("openJpaVendorAdapterSQL") OpenJpaVendorAdapter openJpaVendorAdapterSQL) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(openJpaVendorAdapterSQL);

		return entityManagerFactoryBean;
	}

	@Bean(name = "openJpaVendorAdapterSQL")
	protected OpenJpaVendorAdapter openJpaVendorAdapterSQL() {
		OpenJpaVendorAdapter openJpaVendorAdapterBean = new OpenJpaVendorAdapter();
		openJpaVendorAdapterBean.setShowSql(false);
		openJpaVendorAdapterBean.setGenerateDdl(false);

		return openJpaVendorAdapterBean;
	}

	@Bean(name = "transactionManager")
	JpaTransactionManager employeeTransactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		LOG.debug("INSTANTIATING A NEW JpaTransactionManager");
		return new JpaTransactionManager(entityManagerFactory);
	}
}
