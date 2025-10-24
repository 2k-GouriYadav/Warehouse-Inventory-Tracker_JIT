package com.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Warehouse {

	private final String warehouseName;
	private final Map<String, Product> inventory;
	private final List<StockObserver> observers;
	private final ReentrantLock lock = new ReentrantLock();

	public Warehouse(String warehouseName) {
		this.warehouseName = warehouseName;
		this.inventory = new HashMap();
		this.observers = new ArrayList();
	}

	public void addObserver(StockObserver observer) {
		observers.add(observer);
	}

	public void addProduct(Product product) {

		lock.lock();
		try {
			if (inventory.containsKey(product.getId())) {
				System.out.println("Product already exists !");
				return;
			}
			inventory.put(product.getId(), product);
			System.out.println("Added product : " + product.getName());

		} finally {
			lock.unlock();
		}
	}

	public void receiveShipment(String productId, int quantity) {

		lock.lock();
		try {
			Product product = inventory.get(productId);
			if (product == null) {
				System.out.println("Invalid product Id!");
				return;
			}
			product.addStock(quantity);
			System.out.println("Received shipment : " + quantity + " units of " + product.getName());
		} finally {
			lock.unlock();
		}
	}

	public void fulfillOrder(String productId, int quantity) {
		lock.lock();
		try {
			Product product = inventory.get(productId);
			if (product == null) {
				System.out.println("Invalid product Id!");
				return;
			}

			if (quantity > product.getQuantity()) {
				System.out.println(
						"Your order is not fulfilled ,yet we haven't " + quantity + " units of " + product.getName());
				checkStockLevel(product);
				return;
			}
			try {
				product.reduceStock(quantity);
				System.out.println("FullFilled Order : " + quantity + " units of " + product.getName());
				checkStockLevel(product);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		} finally {
			lock.unlock();
		}
	}

	public void checkStockLevel(Product product) {
		if (product.getQuantity() <= product.getReorderThreshold()) {
			notifyObservers(product);
		}
	}

	public void notifyObservers(Product product) {
		for (StockObserver observer : observers) {
			observer.LowStock(product);
		}
	}

	public void displayProducts() {
		lock.lock();
		try {
			System.out.println("Inventory of " + warehouseName + " :");
			for (Product p : inventory.values()) {
				System.out.println(p);
			}
		} finally {
			lock.unlock();
		}
	}
}
