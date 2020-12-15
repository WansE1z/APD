package multipleProducersMultipleConsumersNBuffer;
public class Consumer implements Runnable {
	Buffer buffer;
	int id;
	public static int idCons;
	static int i = 0;

	Consumer(Buffer buffer, int id) {
		this.buffer = buffer;
		this.id = id;
		idCons = id;
	}

	int getNextI() {
		int value;
		synchronized (Consumer.class) {
			value = i;
			i++;
		}
		return value;
	}

	@Override
	public void run() {
		while (true) {
			if(buffer.get(id) != -1) {
				break;
			}
		}
	}
}
