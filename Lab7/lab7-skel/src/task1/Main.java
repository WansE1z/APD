package task1;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static final AtomicInteger atomInt = new AtomicInteger();
    static int[][] graph = {{0, 1}, {0, 4}, {0, 5}, {1, 0}, {1, 2}, {1, 6}, {2, 1}, {2, 3}, {2, 7},
            {3, 2}, {3, 4}, {3, 8}, {4, 0}, {4, 3}, {4, 9}, {5, 0}, {5, 7}, {5, 8}, {6, 1},
            {6, 8}, {6, 9}, {7, 2}, {7, 5}, {7, 9}, {8, 3}, {8, 5}, {8, 6}, {9, 4}, {9, 6},
            {9, 7}};

    static void getPath(ArrayList<Integer> partialPath, int dest, ExecutorService tpe) {
        if (partialPath.get(partialPath.size() - 1) == dest) {
            System.out.println(partialPath);

            if (atomInt.decrementAndGet() == 0) {
                tpe.shutdown();
            }
            return;
        }

        int lastNodeInPath = partialPath.get(partialPath.size() - 1);
        for (int[] ints : graph) {
            if (ints[0] == lastNodeInPath) {
                if (partialPath.contains(ints[1]))
                    continue;
                atomInt.incrementAndGet();
                ArrayList<Integer> newPartialPath = new ArrayList<>(partialPath);
                newPartialPath.add(ints[1]);
                tpe.submit(() -> getPath(newPartialPath, dest, tpe));
            }
        }

        if (atomInt.decrementAndGet() == 0) {
            tpe.shutdown();
        }
    }

    public static void main(String[] args) {
        ExecutorService tpe = Executors.newFixedThreadPool(4);
        ArrayList<Integer> partialPath = new ArrayList<>();

        partialPath.add(0);
        atomInt.incrementAndGet();

        tpe.submit(() -> getPath(partialPath, 3, tpe));
    }
}
