import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static CyclicBarrier cb;
    public static AtomicInteger sum = new AtomicInteger(0);
    public static void main(String[] args) {

        int v[] = new int[100];
        int N = 100; // cat o sa ni se ceara
        int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
        Thread[] t = new Thread[NUMBER_OF_THREADS];
        cb = new CyclicBarrier(NUMBER_OF_THREADS);

        for (int i = 0; i < 100; i++){
            v[i] = i;
        }
        v[99] = 44;

        for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
            t[i] = new Thread(new Task(i, NUMBER_OF_THREADS, N, v));
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
