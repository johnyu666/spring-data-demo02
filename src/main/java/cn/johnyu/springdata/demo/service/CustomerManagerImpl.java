package cn.johnyu.springdata.demo.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cn.johnyu.springdata.demo.pojo.Customer;

@Service
public class CustomerManagerImpl implements CustomerManager {
	
	@PersistenceContext(unitName="entityManager")
	private EntityManager em;
	@PersistenceUnit
	private EntityManagerFactory factory;
	@Transactional
	public void save(Customer c) {
		em.persist(c);
		
	}

}
