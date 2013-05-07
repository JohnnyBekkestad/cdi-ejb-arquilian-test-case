package com.bekkestad.examples.tutorial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

@Singleton
@Lock(LockType.READ)
public class OrderRepositoryImpl implements OrderRepository {
	
	private Map<Long, Order> orders;
	private Long orderId = 0L;

	@Override
	public Long getOrderId(){
		return orderId;
	}
	
	@Override
	@Lock(LockType.WRITE)
	public Long placeOrder(List<Product> order) {
		orders.put(++orderId, new Order(orderId, order, "CREATED"));
		return orderId;
	}

	@Override
	public Order getOrder(Long orderId) {		
		return orders.get(orderId);
	}
	
	@PostConstruct
	void initialize(){
		orders = new HashMap<Long, Order>();
	}

}
