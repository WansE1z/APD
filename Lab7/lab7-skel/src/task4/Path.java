package task4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class Path extends RecursiveTask<Void> {
    ArrayList<Integer> partPath;
    int dest;

    public Path(ArrayList<Integer> partialPath, int dest) {
        this.partPath = partialPath;
        this.dest = dest;
    }

    @Override
    protected Void compute() {
        if (partPath.get(partPath.size() - 1) == dest) {
            System.out.println(partPath);
            return null;
        }

        // se verifica nodurile pentru a evita ciclarea in graf
        int lastNodeInPath = partPath.get(partPath.size() - 1);
        List<Path> pathsList = new ArrayList<>();

        for (int[] ints : Main.graph) {
            if (ints[0] == lastNodeInPath) {
                if (partPath.contains(ints[1]))
                    continue;

                ArrayList<Integer> newPartialPath = new ArrayList<>(partPath);
                newPartialPath.add(ints[1]);

                Path newPath = new Path(newPartialPath, dest);
                pathsList.add(newPath);
                newPath.fork();
            }
        }
        pathsList.forEach(Path::join);
        return null;
    }
}
