import java.util.concurrent.BrokenBarrierException;

public class Task implements Runnable {

    private int id;
    private int nt;
    private int N;
    private int v[];

    public Task(int id, int number_of_threads, int N, int v[]) {
        this.id = id;
        this.nt = number_of_threads;
        this.N = N;
        this.v = v;
    }

    public void run() {
        int start = (int) (id * (double) N / nt);
        int end = Math.min((int) ((id + 1) * (double) N / nt), N);

        for (int i = start; i < end; i++){
            Main.sum.addAndGet(v[i]);
        }

        try {
            Main.cb.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        if (id == 0){
            System.out.println(N - 1 + Main.sum.addAndGet((-1) * N * (N - 1) / 2));
        }
    }
}
