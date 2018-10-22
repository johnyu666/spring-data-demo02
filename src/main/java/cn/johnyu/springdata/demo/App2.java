package cn.johnyu.springdata.demo;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.johnyu.springdata.demo.pojo.Customer;
import cn.johnyu.springdata.demo.service.CustomerServiceImpl;

public class App2 {
	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		CustomerServiceImpl si=context.getBean(CustomerServiceImpl.class);
		Customer c=new Customer();
		c.setCname("john");
		si.save(c);
	}
}
