package cn.johnyu.springdata.demo;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;

import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import cn.johnyu.springdata.demo.pojo.Customer;
import cn.johnyu.springdata.demo.service.CustomerServiceImpl;

@Configuration

// @EnableJpaRepositories
// 可选以下配置
// @EnableJpaRepositories(basePackages=
// {},entityManagerFactoryRef="",transactionManagerRef="")

@ComponentScan(basePackages = { "service" })
@EnableTransactionManagement(proxyTargetClass = false)
public class App {
	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource("jdbc:mysql://localhost:3306/test", "root", "123");
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		return ds;
	}

	@Bean
	public JpaVendorAdapter getHibernateJpaVendorAdapter() {
		HibernateJpaVendorAdapter vendor = new HibernateJpaVendorAdapter();
		vendor.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
		return vendor;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManageFactory() {
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(getDataSource());
		emfb.setJpaVendorAdapter(getHibernateJpaVendorAdapter());
		emfb.setPackagesToScan("cn.johnyu.springdata.demo.pojo");
		emfb.setPersistenceUnitName("entityManager");

		
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
		jpaProperties.put("hibernate.show_sql", "true");
		jpaProperties.put("hibernate.hbm2ddl.auto", "create");
		emfb.setJpaProperties(jpaProperties);
		return emfb;
	}

	@Bean
	public JpaTransactionManager getTransactionManager() {
		return new JpaTransactionManager((EntityManagerFactory) entityManageFactory().getObject());
	}

	public static void main(String[] args) throws Exception {
		ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
		// DataSource ds=context.getBean(DataSource.class);
		// System.out.println(ds.getConnection());
		// EntityManagerFactory factory=context.getBean(EntityManagerFactory.class);
		//
		// EntityManager manager=factory.createEntityManager();
		// System.out.println(manager);
		CustomerServiceImpl si = context.getBean(CustomerServiceImpl.class);
		Customer c = new Customer();
		c.setCname("john");
		si.save(c);

	}
}
