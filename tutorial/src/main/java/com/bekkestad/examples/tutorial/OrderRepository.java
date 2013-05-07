package com.bekkestad.examples.tutorial;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

@Local
public interface OrderRepository {

	Long placeOrder(List<Product> order);	
	Order getOrder(Long orderId);	
	Long getOrderId();
}
