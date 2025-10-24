package com.warehouse;

public class Product {

	private final String id;
	private final String name;
	private int quantity;
	private final int reorderThreshold;
	
	
	public Product(String id,String name,int reorderThreshold) {
		this.id=id;
		this.name=name;
		this.reorderThreshold=reorderThreshold;
		this.quantity=0;
		
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getReorderThreshold() {
		return reorderThreshold;
	}

	
	public void addStock(int amount) {
		if(amount>0) {
		quantity=quantity+amount;
		}
	}
	
	public void reduceStock(int amount) {
		if(amount<=0 && amount>quantity) {
		throw new IllegalArgumentException("Invalid amount or Insufficient stock");
			
		}
		else {
		quantity=quantity-amount;
		}
		
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", quantity=" + quantity + ", reorderThreshold="
				+ reorderThreshold + "]";
	}
	

	
	
	
}
