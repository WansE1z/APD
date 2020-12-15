package shortestPathsFloyd_Warshall;


/**
 * @author cristian.chilipirea
 *
 */
public class Main {
	public static void main(String[] args) {
		int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
		int M = 9;
		int graph[][] = { { 0, 1, M, M, M },
				          { 1, 0, 1, M, M },
				          { M, 1, 0, 1, 1 },
				          { M, M, 1, 0, M },
				          { M, M, 1, M, 0 } };
		Thread[] t = new Thread[NUMBER_OF_THREADS];

		for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
			t[i] = new Thread(new Task(i, NUMBER_OF_THREADS, graph));
			t[i].start();
		}

		for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
			try {
				t[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print(graph[i][j] + " ");
			}
			System.out.println();
		}
	}
}
