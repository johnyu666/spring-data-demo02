package cn.johnyu.springdata.demo.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import cn.johnyu.springdata.demo.pojo.Customer;
@org.springframework.stereotype.Repository
public interface CustomerRepository extends Repository<Customer, Long>{
	public void save(Customer c);

}
