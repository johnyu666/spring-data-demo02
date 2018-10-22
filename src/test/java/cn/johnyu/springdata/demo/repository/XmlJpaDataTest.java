package cn.johnyu.springdata.demo.repository;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import cn.johnyu.springdata.demo.AppConfWithAnnoAndJpa;
import cn.johnyu.springdata.demo.pojo.Customer;
import cn.johnyu.springdata.demo.service.CustomerManager;


/**
 * 使用配置文件，DATA-JPA,HSQL和MySQL完成测试
 * @author john
 *
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(value="classpath:applicationContext.xml")
public class XmlJpaDataTest {
	@Autowired
	private CustomerManager cm;
	@Autowired
	private CustomerRepository repository;
	@Test
	public void testSaveWithService() {
		Customer c=new Customer();
		c.setCname("hello");
		cm.save(c);
	}
	
	@Test
	public void testSaveWithRepository() {
		Customer c=new Customer();
		c.setCname("hello");
		repository.save(c);
		Assert.assertNotNull(c.getId());
	}

}
