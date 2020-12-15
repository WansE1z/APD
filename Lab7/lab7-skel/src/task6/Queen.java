package task6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class Queen extends RecursiveTask<Void> {
    int[] graph;
    int step;

    public Queen(int[] graph, int step) {
        this.graph = graph;
        this.step = step;
    }

    @Override
    protected Void compute() {
        if (Main.N == step) {
            Main.printQueens(graph);
            return null;
        }

        List<Queen> queenList = new ArrayList<>();

        for (int i = 0; i < Main.N; ++i) {
            int[] newGraph = graph.clone();
            newGraph[step] = i;

            if (Main.check(newGraph, step)) {
                Queen queen = new Queen(newGraph, step + 1);
                queenList.add(queen);
                queen.fork();
            }
        }
        queenList.forEach(Queen::join);
        return null;
    }
}
