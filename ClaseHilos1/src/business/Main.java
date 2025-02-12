package business;

public class Main {
	
	public static void main(String[] args) {
		ShareQueue queue = new ShareQueue(2);// este seria el tamanio de la cola
		Productor productor = new Productor(queue);
		Consumer consumer = new Consumer(" Juan", queue);
		Consumer consumer2 = new Consumer(" Diego", queue);
		
		
		Thread hilo1 = new Thread(productor);
		
		Thread hilo2 = new Thread(consumer);
		Thread hilo3 = new Thread(consumer2);
		
		hilo1.start();
		hilo2.start();
		hilo3.start();
	
	

}
}
	
