package multipleProducersMultipleConsumersNBuffer;
public class Main {
	public static final int N_CONSUMERS = 3; // pot fi schimbate pentru test
	public static final int N_PRODUCERS = 3;
    public static final int k = 4;
    
	public static void main(String[] args) {
        
		Buffer buffer = new Buffer(k);
        
		Thread threads[] = new Thread[N_CONSUMERS + N_PRODUCERS];
		for (int i = 0; i < N_PRODUCERS; i++)
			threads[i] = new Thread(new Producer(buffer, i));
		for (int i = N_PRODUCERS; i < N_CONSUMERS + N_PRODUCERS; i++)
			threads[i] = new Thread(new Consumer(buffer, i - N_PRODUCERS));

		for (int i = 0; i < N_CONSUMERS + N_PRODUCERS; i++)
			threads[i].start();
		for (int i = 0; i < N_CONSUMERS + N_PRODUCERS; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
        
}
