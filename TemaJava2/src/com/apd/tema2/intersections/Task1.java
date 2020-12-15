package com.apd.tema2.intersections;

import com.apd.tema2.entities.Car;
import com.apd.tema2.entities.Intersection;

public class Task1 implements Intersection {

    public void task1(Car car) {
        System.out.println("Car " + car.getId() + " has reached the semaphore, now waiting...");
        // toate masiniile au ajuns la semafor

        try {
            Thread.sleep(car.getWaitingTime());
            // dam sleep sa vina pe rand la semafor
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Car " + car.getId() + " has waited enough, now driving...");
        // mesajul de afisare ca a terminat de asteptat
    }
}
