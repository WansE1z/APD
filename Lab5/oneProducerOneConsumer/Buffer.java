package oneProducerOneConsumer;
/**
 * @author cristian.chilipirea
 *
 */

public class Buffer {
	int a;
	volatile static boolean isNotFull = true;

	public synchronized void put(int value){
		while (!Buffer.isNotFull) {
			try{
				wait();
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		a = value;
		Buffer.isNotFull = false;
		notify();
	}

	public synchronized int get(){
		while (Buffer.isNotFull) {
			try{
				wait();
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		Buffer.isNotFull = true;
		notify();
		return a;
	}

}