package business;

import java.util.Queue;

public class Consumer implements Runnable {
	
	private String name;
	private ShareQueue queue;
	
	public Consumer(String name,ShareQueue queue) {
		this.name = name;
		this.queue = queue;
	}

	
	@Override
	public void run() {
		try {
			removeElement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	}
	
	private void removeElement() throws Exception {
		for(int i=0; i<5; i++) {
			this.queue.removeItemQueue(this.name);
			Thread.sleep(5000);
			
			
		}
	}

}
