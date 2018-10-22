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
import cn.johnyu.springdata.demo.service.CustomerManagerImpl;

/**
 * 以Annotation的方式，使用JPA,Mysql,Hsqldb,
 * @author john
 *
 */

@Configuration
@ComponentScan(basePackages = { "cn.johnyu.springdata.demo.service" })
@EnableTransactionManagement(proxyTargetClass = false)
public class AppConfWithAnnoAndJpa {
	
	@Bean
	public DataSource getDataSourceWithMySql() {
		DriverManagerDataSource ds = new DriverManagerDataSource("jdbc:mysql://localhost:3306/test", "root", "123");
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		return ds;
	}
	@Bean DataSource getDataSourceWithHsql() {
		DriverManagerDataSource ds = new DriverManagerDataSource("jdbc:hsqldb:file:db/testdb", "sa", "");
		ds.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
		return ds;
	}

	@Bean
	public JpaVendorAdapter getHibernateJpaVendorAdapter() {
		HibernateJpaVendorAdapter vendor = new HibernateJpaVendorAdapter();
//		vendor.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
		vendor.setDatabasePlatform("org.hibernate.dialect.HSQLDialect");
		return vendor;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManageFactory() {
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		
		//此处可以更换数据源（Mysql及Hsql）
		emfb.setDataSource(getDataSourceWithHsql());
		
		
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
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfWithAnnoAndJpa.class);
		CustomerManagerImpl cm = context.getBean(CustomerManagerImpl.class);
		Customer c = new Customer();
		c.setCname("john");
		cm.save(c);
	}
}
