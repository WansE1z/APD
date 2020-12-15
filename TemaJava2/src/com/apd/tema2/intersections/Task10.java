package com.apd.tema2.intersections;

import com.apd.tema2.entities.Car;
import com.apd.tema2.entities.Intersection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Task10 implements Intersection {
    private static ArrayBlockingQueue<Integer> carsQueue;
    private static CyclicBarrier cyclicBarrier, cyclicBarrier2;

    public static ArrayBlockingQueue<Integer> getCarsQueue() {
        return carsQueue;
    }

    public static void setCarsQueue(int cars) {
        Task10.carsQueue = new ArrayBlockingQueue<>(cars);
    }

    public static CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    public static void setCyclicBarrier(int cars) {
        Task10.cyclicBarrier = new CyclicBarrier(cars);
    }

    public static CyclicBarrier getCyclicBarrier2() {
        return cyclicBarrier2;
    }

    public static void setCyclicBarrier2(int cars) {
        Task10.cyclicBarrier2 = new CyclicBarrier(cars);
    }

    public void task10(Car car) {
        System.out.println("Car " + car.getId() + " from side number " + car.getStartDirection()
                + " has stopped by the railroad");
        getCarsQueue().add(car.getId());
        // coada in care retin ordinea masiniilor

        try {
            getCyclicBarrier().await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        // bariera sa ma asigur ca se adauga toate masiniile inainte sa afisez mesajul

        if (car.getId() == 0) {
            System.out.println("The train has passed, cars can now proceed");
        }

        // poate masina cu id-ul 0 nu este prima, si sa nu risc scrierea gresita
        try {
            getCyclicBarrier2().await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        // daca ordinea masiniilor nu este respectata, wait()
        while (!getCarsQueue().isEmpty() && getCarsQueue().peek() != car.getId()) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // afisare dupa ce a trecut trenul, ca masiniile pleaca
        System.out.println("Car " + car.getId() + " from side number " + car.getStartDirection()
                + " has started driving");

        // scot din coada masina
        getCarsQueue().remove();

        // dau notifyAll ca a iesit masina, deci poate urmatoarea masina va respectat conditia din while
        synchronized (this) {
            notifyAll();
        }
    }
}
