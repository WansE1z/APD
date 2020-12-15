package com.apd.tema2.intersections;

import com.apd.tema2.entities.Car;
import com.apd.tema2.entities.Intersection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Task8 implements Intersection {
    private static ArrayBlockingQueue<Integer> directionQueue0, directionQueue1;
    private static CyclicBarrier cyclicBarrier;
    private static int noOfLanes;
    private static int noOfCarsPassing;
    private static Semaphore semaphore1, semaphore2;

    public static ArrayBlockingQueue<Integer> getDirectionQueue0() {
        return directionQueue0;
    }

    public static void setDirectionQueue0(int cars) {
        Task8.directionQueue0 = new ArrayBlockingQueue<Integer>(cars);
    }

    public static ArrayBlockingQueue<Integer> getDirectionQueue1() {
        return directionQueue1;
    }

    public static void setDirectionQueue1(int cars) {
        Task8.directionQueue1 = new ArrayBlockingQueue<Integer>(cars);
    }

    public static Semaphore getSemaphore1() {
        return semaphore1;
    }

    public static void setSemaphore1() {
        Task8.semaphore1 = new Semaphore(noOfCarsPassing);
    }

    public static Semaphore getSemaphore2() {
        return semaphore2;
    }

    public static void setSemaphore2() {
        Task8.semaphore2 = new Semaphore(0);
    }

    public static int getNoOfLanes() {
        return noOfLanes;
    }

    public static void setNoOfLanes(int noOfLanes) {
        Task8.noOfLanes = noOfLanes;
    }

    public static CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    public static void setCyclicBarrier(int cars) {
        Task8.cyclicBarrier = new CyclicBarrier(cars);
    }

    public static int getNoOfCarsPassing() {
        return noOfCarsPassing;
    }

    public static void setNoOfCarsPassing(int noOfCarsPassing) {
        Task8.noOfCarsPassing = noOfCarsPassing;
    }

    public void task8(Car car) {
        System.out.println("Car " + car.getId() + " from side number " + car.getStartDirection() + " has reached the bottleneck");

        // adaug in coada specifica pentru directie id-ul masinii
        if (car.getStartDirection() == 0){
            getDirectionQueue0().add(car.getId());
        } else {
            getDirectionQueue1().add(car.getId());
        }

        // bariera sa ma asigur ca toate se adauga pana se executa restul comenzilor
        try {
            getCyclicBarrier().await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        // cat timp numarul de permisiuni este 0, adica nu se poate trece, si ordinea nu se respecta, wait
        // astept ca un alt thread sa dea notify, ca thread-ul ramas in wait sa reverifice
        while ((getSemaphore1().availablePermits() == 0 || getDirectionQueue0().peek() != car.getId())
                && (getSemaphore2().availablePermits() == 0 || getDirectionQueue1().peek() != car.getId())) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Car " + car.getId() + " from side number " + car.getStartDirection()
                + " has passed the bottleneck");
        // au trecut de bottleneck

        // daca directia este 0, acquire pe semaforul 1
        if (car.getStartDirection() == 0) {
            try {
                getSemaphore1().acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            // daca directia este 1, acquire pe semaforul 2
            try {
                getSemaphore2().acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // scot din coada masina de a trecut
        if (car.getStartDirection() == 0){
            getDirectionQueue0().remove();
        } else {
            getDirectionQueue1().remove();
        }

        // daca a ajuns numarul de permisiuni 0, inseamna ca au trecut noOfCarsPassing
        // reinitializez semaforul cu numarul de permisiuni maxim, sa se poata relua procesul
        if (getSemaphore1().availablePermits() == 0 && car.getStartDirection() == 0) {
            getSemaphore2().release(getNoOfCarsPassing());
        } else {
            if (getSemaphore2().availablePermits() == 0 && car.getStartDirection() == 1) {
                getSemaphore1().release(getNoOfCarsPassing());
            }
        }

        synchronized (this) {
            notifyAll();
        }

    }
}