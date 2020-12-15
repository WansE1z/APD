import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static Map<Integer, Integer> map;
    public static void main(String[] args) {

        int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
        map = Collections.synchronizedMap(new HashMap<>());
        map.put(0, 1);
        map.put(1, 3);
        map.put(2, 4);
        Thread[] t = new Thread[NUMBER_OF_THREADS];

        for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
            t[i] = new Thread(new Task(i, NUMBER_OF_THREADS, map.size(), map));
            t[i].start();
        }

        for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
            try {
                t[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(map.entrySet());
    }
}
