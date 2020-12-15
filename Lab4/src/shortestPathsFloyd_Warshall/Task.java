package shortestPathsFloyd_Warshall;

public class Task implements Runnable {

    private int id;
    private int NUMBER_OF_THREADS;
    private int graph[][];

    public Task(int id, int NUMBER_OF_THREADS, int graph[][]) {
        this.id = id;
        this.NUMBER_OF_THREADS = NUMBER_OF_THREADS;
        this.graph = graph;
    }

    public int min(int a, int b){
        if (a <= b) return a;
        return b;
    }

    public void run() {
        // Parallelize me
        int start = id * 5 / NUMBER_OF_THREADS;
        int end = min((id + 1) * 5 / NUMBER_OF_THREADS, 5);
        for (int k = 0; k < 5; k++) {
            for (int i = start; i < end; i++) {
                for (int j = 0; j < 5; j++) {
                    graph[i][j] = Math.min(graph[i][k] + graph[k][j], graph[i][j]);
                }
            }
        }
    }


}
