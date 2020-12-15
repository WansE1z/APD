package com.apd.tema2.intersections;

import com.apd.tema2.entities.Car;
import com.apd.tema2.entities.Intersection;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Task345 implements Intersection {
    private static int noOfLanes;
    private static int roundaboutPassingTime;
    private static int noOfCarsPassing;
    private static Semaphore[] semaphoreVector;
    private static CyclicBarrier cyclicBarrier;
    private static CyclicBarrier cyclicBarrier2;
    private static CyclicBarrier cyclicBarrier3;
    private static int timeInSeconds;

    public static int getTimeInSeconds() {
        return timeInSeconds;
    }

    public static void setTimeInSeconds(int timeInSeconds) {
        Task345.timeInSeconds = timeInSeconds;
    }

    public static void setNoOfLanes(int noOfLanes) {
        Task345.noOfLanes = noOfLanes;
    }

    public static int getRoundaboutPassingTime() {
        return roundaboutPassingTime;
    }

    public static void setRoundaboutPassingTime(int roundaboutPassingTime) {
        Task345.roundaboutPassingTime = roundaboutPassingTime;
    }

    public static void setNoOfCarsPassing(int noOfCarsPassing) {
        Task345.noOfCarsPassing = noOfCarsPassing;
    }

    public static CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    public static void setCyclicBarrier(int cars) {
        Task345.cyclicBarrier = new CyclicBarrier(cars);
    }

    public static Semaphore[] getSemaphoreVector() {
        return semaphoreVector;
    }

    public static void setSemaphoreVector(int noLanes, int maxNoOfCars) {
        semaphoreVector = new Semaphore[noOfLanes];
        for (int i = 0; i < noLanes; i++) {
            getSemaphoreVector()[i] = new Semaphore(maxNoOfCars);
        }
    }

    public static CyclicBarrier getCyclicBarrier2() {
        return cyclicBarrier2;
    }

    public static void setCyclicBarrier2(int cars) {
        Task345.cyclicBarrier2 = new CyclicBarrier(cars);
    }

    public static CyclicBarrier getCyclicBarrier3() {
        return cyclicBarrier3;
    }

    public static void setCyclicBarrier3(int cars) {
        Task345.cyclicBarrier3 = new CyclicBarrier(cars);
    }

    public void task345(Car car, int taskNr) {
        // taskNr e parametru de il dau din handlerFactory, pentru fiecare cerinta in parte (3, 4, 5)

        if (taskNr == 3) {
            System.out.println("Car " + car.getId() + " has reached the roundabout");
        } else if (taskNr == 4) {
            System.out.println("Car " + car.getId() + " has reached the roundabout, now waiting...");
        } else {
            System.out.println("Car " + car.getId() + " has reached the roundabout from lane " + car.getStartDirection());
        }
        // fiecare task are afisarea sa

        if (taskNr == 4) {
            try {
                getCyclicBarrier().await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        try {
            getSemaphoreVector()[car.getStartDirection()].acquire();
            // dau acquire pe fiecare semafor,lasand fiecare lane doar cate masini au voie, astfel
            // putand intra maxim maxNoOfCars masini
            if (taskNr == 3) {
                getCyclicBarrier3().await();
            }
            if (taskNr == 4) {
                System.out.println("Car " + car.getId() + " was selected to enter the roundabout from lane " + car.getStartDirection());
                getCyclicBarrier2().await();
            }

            System.out.println("Car " + car.getId() + " has entered the roundabout from lane " + car.getStartDirection());

            if (taskNr == 4) {
                try {
                    getCyclicBarrier2().await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }

            Thread.sleep(getRoundaboutPassingTime());
            // sleep pentru ca atat dureaza ca masina sa iasa din sesnsul giratoriu

            System.out.println("Car " + car.getId() + " has exited the roundabout after " + getTimeInSeconds() + " seconds");

            if (taskNr == 4) {
                try {
                    getCyclicBarrier2().await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }

            getSemaphoreVector()[car.getStartDirection()].release();
            // masina a iesit din giratoriu, asa ca dau release
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
