package com.bekkestad.examples.tutorial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

@SessionScoped
public class ShoppingCart implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<Long, Product> shoppingCartMap;
	
	@EJB
	private OrderRepository order;

	public void addProduct(Product product){
		shoppingCartMap.put(product.getId(), product);		
	}
	
	public void removeProduct(Long productId){
		shoppingCartMap.remove(productId);
	}
	
	public List<Product> getShoppingCart(){		
		return Collections.unmodifiableList(new ArrayList<Product>(shoppingCartMap.values()));			
	}
	
	public Long placeOrder(){
		Long orderId = order.placeOrder(getShoppingCart());
		shoppingCartMap.clear();
		return orderId;			
	}
	
	public String getOrderStatus(Long orderId){
		return order.getOrder(orderId).getStatus();
	}
	
	@PostConstruct
	void initialize(){
		shoppingCartMap = new HashMap<Long, Product>();
	}
}
