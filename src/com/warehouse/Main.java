package com.warehouse;

public class Main {
	public static void main(String[] args) {
		final Warehouse warehouse = new Warehouse("Main warehouse");
		AlertService alertService = new AlertService();
		warehouse.addObserver(alertService);

		Product laptop = new Product("P001", "Laptop", 5);
		warehouse.addProduct(laptop);

		System.out.println();

		warehouse.receiveShipment("P001", 10);
		
	    Runnable laptopTask1 = new Runnable() {
            
            public void run() {
                warehouse.fulfillOrder("P001", 3);
            }
        };

        Runnable laptopTask2 = new Runnable() {
           
            public void run() {
                warehouse.fulfillOrder("P001", 2);
            }
        };

        Runnable shipmentTask = new Runnable() {
           
            public void run() {
                warehouse.receiveShipment("P001", 5);
            }
        };

        Thread laptopThread1 = new Thread(laptopTask1, "LaptopThread-1");
        Thread laptopThread2 = new Thread(laptopTask2, "LaptopThread-2");
        Thread shipmentThread = new Thread(shipmentTask, "ShipmentThread");

	        
        laptopThread1.start();
        laptopThread2.start();
	        shipmentThread.start();

	        
	        try {
	        	laptopThread1.join();
	        	laptopThread2.join();
	            shipmentThread.join();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

		warehouse.displayProducts();


		System.out.println();
		System.out.println();
		
	

		Product fan = new Product("F114", "Crompton Fan", 4);
		warehouse.addProduct(fan);

		System.out.println();
		
warehouse.receiveShipment("F114", 6);
		
	    Runnable fanTask1 = new Runnable() {
            
            public void run() {
                warehouse.fulfillOrder("F114", 5);
            }
        };

		Thread fanThread=new Thread(fanTask1,"Fan Thread-1");
		fanThread.start();
		
		 try {
	            fanThread.join();
	            
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
		System.out.println();
		warehouse.displayProducts();
	}
}
