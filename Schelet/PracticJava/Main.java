public class Main {

    // public static AtomicInteger sum = new AtomicInteger(0);
    // public static Semaphore sem;
    // public static CyclicBarrier bar;
    public static void main(String[] args) {

        int N = 1; // cat o sa ni se ceara
        int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();

        /*
            sem = new Semaphore(x);
            x > 0 => astept sa dea x thread-uri acquire
            x < 0 => astept sa dea thread-urile release pana ajunge contorul 1 (folosit la citire)
        */
        // bar = new CyclicBarrier(NUMBER_OF_THREADS);
        Thread[] t = new Thread[NUMBER_OF_THREADS];

        for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
            t[i] = new Thread(new Task(i, NUMBER_OF_THREADS, N));
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
