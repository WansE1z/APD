package ex1;

public class Task implements Runnable {

    private int id;
    private int result;

    public Task(int id) {
        this.id = id;
    }

    public void run() {
        System.out.println("This is a message by thread id " + this.id);
    }


}
