package task5;

import java.util.concurrent.ForkJoinPool;

public class Main {
    static int N = 10;
    static int COLORS = 3;
    static int[][] graph = {{0, 1}, {0, 4}, {0, 5}, {1, 0}, {1, 2}, {1, 6}, {2, 1}, {2, 3}, {2, 7},
            {3, 2}, {3, 4}, {3, 8}, {4, 0}, {4, 3}, {4, 9}, {5, 0}, {5, 7}, {5, 8}, {6, 1},
            {6, 8}, {6, 9}, {7, 2}, {7, 5}, {7, 9}, {8, 3}, {8, 5}, {8, 6}, {9, 4}, {9, 6},
            {9, 7}};

    static boolean verifyColors(int[] colors, int step) {
        for (int i = 0; i < step; i++) {
            if (colors[i] == colors[step] && isEdge(i, step))
                return false;
        }
        return true;
    }

    static boolean isEdge(int a, int b) {
        for (int[] ints : graph) {
            if (ints[0] == a && ints[1] == b)
                return true;
        }
        return false;
    }

    static void printColors(int[] colors) {
        StringBuilder aux = new StringBuilder();
        for (int color : colors) {
            aux.append(color).append(" ");
        }
        System.out.println(aux);
    }

    public static void main(String[] args) {
        ForkJoinPool fjp = new ForkJoinPool(4);
        int[] colors = new int[N];

        fjp.invoke(new Color(colors, 0));
        fjp.shutdown();
    }
}
