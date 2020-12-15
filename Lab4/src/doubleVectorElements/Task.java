package doubleVectorElements;

public class Task implements Runnable {

    private int id;
    private int N;
    private int NUMBER_OF_THREADS;
    private int v[];

    public Task(int id, int N, int NUMBER_OF_THREADS, int v[]) {
        this.id = id;
        this.N = N;
        this.NUMBER_OF_THREADS = NUMBER_OF_THREADS;
        this.v = v;
    }

    public int min(int a, int b){
        if (a <= b) return a;
        return b;
    }

    public void run() {
        // Parallelize me
        int start = id * N / NUMBER_OF_THREADS;
        int end = min((id + 1) * N / NUMBER_OF_THREADS, N);
        for (int i = start; i < end; i++) {
            v[i] = v[i] * 2;
        }
    }


}
