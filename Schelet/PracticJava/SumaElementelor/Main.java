import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger sum = new AtomicInteger(0);
    public static void main(String[] args) {
        CyclicBarrier bar;
        int N = 24;
        int v[] = new int[N];
        int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();

        bar = new CyclicBarrier(NUMBER_OF_THREADS);
        Thread[] t = new Thread[NUMBER_OF_THREADS];

        for (int i = 0; i < N; i++){
            v[i] = i;
        }
        for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
            t[i] = new Thread(new Task(i, NUMBER_OF_THREADS, N, v, bar));
            t[i].start();
        }

        for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
            try {
                t[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
