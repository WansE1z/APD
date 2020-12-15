public class Main {
    public static final int MAX_THREAD = 12;

    public static void main(String[] args) {
        int a[] = { 0 ,1 ,2 ,3 ,4 ,5 ,6 ,7 ,8 ,9, 10 ,11, 12 ,13 ,14 ,
                15, 16, 17 ,18 ,19, 20, 21, 22, 23,24, 25, 26, 27 ,28, 29, 30, 31, 32, 33, 34, 36};
        int size = 36;
        Thread t[] = new Thread[MAX_THREAD];
        for (int i = 0; i < MAX_THREAD; i++) {
            t[i] = new Thread(new BinarySearch(i, a, 23, size));
            t[i].start();
        }
        for (int i = 0; i < MAX_THREAD; i++) {
            try {
                t[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
