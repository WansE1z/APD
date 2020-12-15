package com.apd.tema2.intersections;

import com.apd.tema2.Main;
import com.apd.tema2.entities.Car;
import com.apd.tema2.entities.Intersection;

public class Task7 implements Intersection {
    private static final Boolean[] canPass = new Boolean[Main.carsNo];
    private static int executeTime;
    private static int maxNoOfPedestrians;

    public static Boolean[] getCanPass() {
        return canPass;
    }

    public static int getExecuteTime() {
        return executeTime;
    }

    public static void setExecuteTime(int executeTime) {
        Task7.executeTime = executeTime;
    }

    public static int getMaxNoOfPedestrians() {
        return maxNoOfPedestrians;
    }

    public static void setMaxNoOfPedestrians(int maxNoOfPedestrians) {
        Task7.maxNoOfPedestrians = maxNoOfPedestrians;
    }

    public static void setCanPassVector(int cars) {
        for (int i = 0; i < cars; i++) {
            canPass[i] = true;
        }
    }

    public void task7(Car car) {
        /*
            cat timp pedestrians.isFinished e fals, adica nu a trecut timpul din functia respectiva
            - daca nu trec pietonii, masiniile au verde
            - daca trec pietonii, masiniile au rosu
            - getCanPass e o functie in care conditionez schimbarea culorii
         */
        while (!Main.pedestrians.isFinished()) {
            if (!Main.pedestrians.isPass()) {
                if (getCanPass()[car.getId()]) {
                    System.out.println("Car " + car.getId() + " has now green light");
                    getCanPass()[car.getId()] = false;
                }
            }
            if (Main.pedestrians.isPass()) {
                if (!getCanPass()[car.getId()]) {
                    System.out.println("Car " + car.getId() + " has now red light");
                    getCanPass()[car.getId()] = true;
                }
            }
        }

        // ma asigur in caz de timpii pe checker nu corespund, ma asigur ca se afiseaza toti
        if (!Main.pedestrians.isPass()){
            if (getCanPass()[car.getId()]) {
                System.out.println("Car " + car.getId() + " has now green light");
            }
        }
        if (Main.pedestrians.isPass()){
            if (!getCanPass()[car.getId()]) {
                System.out.println("Car " + car.getId() + " has now red light");
            }
        }
    }
}
