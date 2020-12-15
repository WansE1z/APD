package task3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static int N = 4;
    static final AtomicInteger atomInt = new AtomicInteger();

    static boolean check(int[] arr, int step) {
        for (int i = 0; i <= step; i++) {
            for (int j = i + 1; j <= step; j++) {
                if (arr[i] == arr[j] || arr[i] + i == arr[j] + j || arr[i] + j == arr[j] + i)
                    return false;
            }
        }
        return true;
    }

    static void printQueens(int[] sol) {
        StringBuilder aux = new StringBuilder();
        for (int i = 0; i < sol.length; i++) {
            aux.append("(").append(sol[i] + 1).append(", ").append(i + 1).append("), ");
        }
        aux = new StringBuilder(aux.substring(0, aux.length() - 2));
        System.out.println("[" + aux + "]");
    }

    static void queens(int[] graph, int step, ExecutorService tpe) {
        if (Main.N == step) {
            printQueens(graph);

            if (atomInt.decrementAndGet() == 0) {
                tpe.shutdown();
            }
            return;
        }
        for (int i = 0; i < Main.N; ++i) {
            int[] newGraph = graph.clone();
            newGraph[step] = i;

            if (check(newGraph, step)) {
                atomInt.incrementAndGet();
                tpe.submit(() -> queens(newGraph, step + 1, tpe));
            }
        }

        if (atomInt.decrementAndGet() == 0) {
            tpe.shutdown();
        }
    }

    public static void main(String[] args) {
        ExecutorService tpe = Executors.newFixedThreadPool(4);
        int[] graph = new int[N];

        atomInt.incrementAndGet();
        tpe.submit(() -> queens(graph, 0, tpe));
    }
}
