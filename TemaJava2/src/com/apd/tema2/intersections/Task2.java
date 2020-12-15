package com.apd.tema2.intersections;

import com.apd.tema2.entities.Car;
import com.apd.tema2.entities.Intersection;

import java.util.concurrent.Semaphore;

public class Task2 implements Intersection {
    private static int maxNumberOfCars;
    private static int roundaboutTime;
    private static Semaphore semaphore;
    private static int timeInSeconds;

    public static int getTimeInSeconds() {
        return timeInSeconds;
    }

    public static void setTimeInSeconds(int timeInSeconds) {
        Task2.timeInSeconds = timeInSeconds;
    }

    public static void setMaxNumberOfCars(int maxNumberOfCars) { Task2.maxNumberOfCars = maxNumberOfCars; }

    public static int getRoundaboutTime() {
        return roundaboutTime;
    }

    public static void setRoundaboutTime(int roundaboutTime) {
        Task2.roundaboutTime = roundaboutTime;
    }

    public static Semaphore getSemaphore() {
        return semaphore;
    }

    public static void setSemaphore(int cars) {
        Task2.semaphore = new Semaphore(cars);
    }

    public void task2(Car car) {
        System.out.println("Car " + car.getId() + " has reached the roundabout, now waiting...");
        // masina a ajuns la giratoriu, afisez mesajul
        try {
            getSemaphore().acquire();
            // numarul de permisiuni al semaforului scade

            Thread.sleep(getRoundaboutTime());
            // timpul necesar ca o masina sa paraseasca giratoriul

            System.out.println("Car " + car.getId() + " has entered the roundabout");
            getSemaphore().release();
            // incrementez numarul de permisiuni

            System.out.println("Car " + car.getId() + " has exited the roundabout after " + getTimeInSeconds() + " seconds");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
