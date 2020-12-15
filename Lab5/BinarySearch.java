public class BinarySearch implements Runnable {
    int id;
    int myVector[];
    int nrToFound;
    int size;
    boolean found = false;

    BinarySearch(int id, int a[], int nrToFound, int size) {
        this.id = id;
        this.myVector = a;
        this.nrToFound = nrToFound;
        this.size = size;
    }

    public void run() {
        int mid;
        int low = id * (size / Main.MAX_THREAD);
        int high = (id + 1) * (size / Main.MAX_THREAD);
        while (low <= high && !found)  {
            if (nrToFound > myVector[size-1]){
                return;
            } else if (nrToFound < myVector[0]){
                return;
            }
            mid = (high - low) / 2 + low;
            if (myVector[mid] == nrToFound)  {
                found = true;
                System.out.println("The number was found!");
                break;
            }
            else if (myVector[mid] > nrToFound)
                high = mid - 1;
            else
                low = mid + 1;
        }
    }
}
