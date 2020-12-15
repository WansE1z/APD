package multipleProducersMultipleConsumersNBuffer;

public class Producer implements Runnable {
	int id;
	public static int idProd;
	Buffer buffer;

	Producer(Buffer buffer, int id) {
		this.buffer = buffer;
		this.id = id;
		idProd = id;
	}

	@Override
	public void run() {
		buffer.put(id);
	}

}