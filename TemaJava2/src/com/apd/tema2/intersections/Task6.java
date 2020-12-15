package com.apd.tema2.intersections;

import com.apd.tema2.entities.Car;
import com.apd.tema2.entities.Intersection;
import com.apd.tema2.utils.Constants;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

public class Task6 implements Intersection {
    private static int noOfHighPriorityCars;
    private static int noOfLowPriorityCars;
    private static ArrayBlockingQueue<Integer> carQueue;
    private static Semaphore semaphore;

    public static Semaphore getSemaphore() {
        return semaphore;
    }

    public static void setSemaphore(int number) {
        Task6.semaphore = new Semaphore(number);
    }

    public static int getNoOfHighPriorityCars() {
        return noOfHighPriorityCars;
    }

    public static void setNoOfHighPriorityCars(int noOfHighPriorityCars) {
        Task6.noOfHighPriorityCars = noOfHighPriorityCars;
    }

    public static int getNoOfLowPriorityCars() {
        return noOfLowPriorityCars;
    }

    public static void setNoOfLowPriorityCars(int noOfLowPriorityCars) {
        Task6.noOfLowPriorityCars = noOfLowPriorityCars;
    }

    public static ArrayBlockingQueue<Integer> getCarQueue() {
        return carQueue;
    }

    public static void setCarQueue(int number) {
        Task6.carQueue = new ArrayBlockingQueue<Integer>(number);
    }


    public void task6(Car car) {
        // daca are prioritate mica
        if (car.getPriority() == 1) {
            getCarQueue().add(car.getId());
            // adaug masina cu prioritate mica in coada, pentru a tine cont de ordine

            System.out.println("Car " + car.getId() + " with low priority is trying to enter the intersection...");
            // cat timp ordinea nu se respecta, si numarul de permisiuni nu este egal cu numarul de masini cu prioritate mare
            // cand numarul de permisiuni nu este egal cu numarul de masini, atunci inseamna ca este o masina cu prioritate mare in giratoriu
            while ((!getCarQueue().isEmpty() && getCarQueue().peek() != car.getId()) || getSemaphore().availablePermits() != noOfHighPriorityCars) {
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Car " + car.getId() + " with low priority has entered the intersection");

            // a iesit masina din giratoriu, o scot si din coada
            getCarQueue().remove();
        } else {
            System.out.println("Car " + car.getId() + " with high priority has entered the intersection");
            try {
                // dau acquire ca sa decrementez numarul de permisiuni, sa verific in while
                getSemaphore().acquire();

                // sleep pentru ca atat ii ia masinii sa iasa din giratoriu
                Thread.sleep(Constants.PRIORITY_INTERSECTION_PASSING);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Car " + car.getId() + " with high priority has exited the intersection");

            // release, incrementez numarul de permisiuni
            getSemaphore().release();
        }
        synchronized (this) {
            notifyAll();
        }
    }
}
