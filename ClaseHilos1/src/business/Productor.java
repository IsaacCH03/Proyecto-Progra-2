package business;

public class Productor implements Runnable {
	
	private ShareQueue queue;
	
	public Productor(ShareQueue queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			addElementQueue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private void addElementQueue() throws Exception {
		for (int i = 0; i < 10; i++) {
			this.queue.addItemInQueue(i);
			Thread.sleep(4000);
			
		}
		
	}
	

}
