package business;

import java.util.LinkedList;
import java.util.Queue;

public class ShareQueue {
	
	private Queue <Integer> queue;
	private int quantity; // tamanio de la cola
	
	public ShareQueue(int quantity) {
		 queue = new LinkedList<Integer>();
		 this.quantity = quantity;
		 
	}
	
	//metodo productor sincronizado
	public synchronized void addItemInQueue(int value) throws Exception {
			while (queue.size() == this.quantity) {
				System.out.println("La cola esta llena");
				wait();// espera que la condicion de llenado				
			}
			
			this.queue.add(value);
			System.out.println("Se agrego el valor " + value);
			notifyAll();//llama a que continuen los hilos
	
		}
	//metodo consumidor sincronizado
	
	public synchronized int removeItemQueue(String name) throws Exception {
		while(this.queue.isEmpty()) {
			wait();
			System.out.println("Consumidor "+ name+ " esta esperando por cola vacia");
		}
		int value = this.queue.remove();
		System.out.println("Sacando el valor "+value);
		notifyAll();
		
		return value;
		
	}
	}

	
		
