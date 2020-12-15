package doubleVectorElements;

/**
 * @author cristian.chilipirea
 *
 */
public class Main {
	public static void main(String[] args) {
		int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
		int N = 100000013;
		int v[] = new int[N];
		Thread[] t = new Thread[NUMBER_OF_THREADS];

		for(int i=0;i<N;i++)
			v[i]=i;

		for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
			t[i] = new Thread(new Task(i, N, NUMBER_OF_THREADS, v));
			t[i].start();
		}

		for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
			try {
				t[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < N; i++) {
			if(v[i]!= i*2) {
				System.out.println("Wrong answer");
				System.exit(1);
			}
		}
		System.out.println("Correct");
	}

}
