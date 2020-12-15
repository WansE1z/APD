package task6;

import java.util.concurrent.ForkJoinPool;

public class Main {
    public static int N = 4;

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


    public static void main(String[] args) {
        ForkJoinPool fjp = new ForkJoinPool(4);
        int[] graph = new int[N];

        fjp.invoke(new Queen(graph, 0));
        fjp.shutdown();
    }
}
