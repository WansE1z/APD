public class Task implements Runnable {

    private int id;
    private int nt;
    private int N;

    public Task(int id, int number_of_threads, int N) {
        this.id = id;
        this.nt = number_of_threads;
        this.N = N;
    }

    public void run() {
        int start = (int) (id * (double) N / nt);
        int end = Math.min((int) ((id + 1) * (double) N / nt), N);
    }
}
