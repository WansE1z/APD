import java.util.Map;

public class Task implements Runnable {

    private int id;
    private int nt;
    private int N;
    private static Map<Integer, Integer> map;

    public Task(int id, int number_of_threads, int N, Map<Integer, Integer> map) {
        this.id = id;
        this.nt = number_of_threads;
        this.N = N;
        this.map = map;
    }

    public void run() {
        int start = (int) (id * (double) N / nt);
        int end = Math.min((int) ((id + 1) * (double) N / nt), N);
        for (int i = start; i < end; i++){
            map.put(i, map.get(i) + 1);
        }
    }
}
