package com.bekkestad.examples.tutorial;

import java.util.List;

public class Order {

	private Long id;
	private List<Product> products;
	private String status;

	public Order(Long orderId, List<Product> products, String status) {
		super();
		this.id = orderId;
		this.products = products;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
