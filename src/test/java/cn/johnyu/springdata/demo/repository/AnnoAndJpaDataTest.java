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
import cn.johnyu.springdata.demo.AppConfWithAnnoAndJpaData;
import cn.johnyu.springdata.demo.pojo.Customer;
import cn.johnyu.springdata.demo.service.CustomerManager;

/**
 * 以Annotation的方式，使用JPA-Data,Mysql,Hsqldb,
 * @author john
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes=AppConfWithAnnoAndJpaData.class)
public class AnnoAndJpaDataTest {
	@Autowired
	private CustomerRepository repository;
	@Autowired
	private CustomerManager cm;
	@Test
	public void testRepositorySave() {
		Customer c=new Customer();
		c.setCname("hello");
		repository.save(c);
		Assert.assertTrue(c.getId()!=null);
	}
	@Test
	public void testServiceSave() {
		Customer c=new Customer();
		c.setCname("hello");
		cm.save(c);
		Assert.assertTrue(c.getId()!=null);
	}
}
