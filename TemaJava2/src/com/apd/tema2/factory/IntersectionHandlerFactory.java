package com.apd.tema2.factory;

import com.apd.tema2.Main;
import com.apd.tema2.entities.Car;
import com.apd.tema2.entities.IntersectionHandler;
import com.apd.tema2.intersections.*;

import static java.lang.Thread.sleep;

/**
 * Clasa Factory ce returneaza implementari ale InterfaceHandler sub forma unor
 * clase anonime.
 */
public class IntersectionHandlerFactory {

    public static IntersectionHandler getHandler(String handlerType) {
        // simple semaphore intersection
        // max random N cars roundabout (s time to exit each of them)
        // roundabout with exactly one car from each lane simultaneously
        // roundabout with exactly X cars from each lane simultaneously
        // roundabout with at most X cars from each lane simultaneously
        // entering a road without any priority
        // crosswalk activated on at least a number of people (s time to finish all of
        // them)
        // road in maintenance - 2 ways 1 lane each, X cars at a time
        // road in maintenance - 1 way, M out of N lanes are blocked, X cars at a time
        // railroad blockage for s seconds for all the cars
        // unmarked intersection
        // cars racing
        return switch (handlerType) {
            case "simple_semaphore" -> car -> ((Task1) Main.intersection).task1(car);
            case "simple_n_roundabout" -> car -> ((Task2) Main.intersection).task2(car);
            case "simple_strict_1_car_roundabout" -> car -> ((Task345) Main.intersection).task345(car, 3);
            case "simple_strict_x_car_roundabout" -> car -> ((Task345) Main.intersection).task345(car, 4);
            case "simple_max_x_car_roundabout" -> car -> {
                try {
                    sleep(car.getWaitingTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ((Task345) Main.intersection).task345(car, 5);
            };
            case "priority_intersection" -> car -> {
                try {
                    sleep(car.getWaitingTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ((Task6) Main.intersection).task6(car);
            };
            case "crosswalk" -> car -> ((Task7) Main.intersection).task7(car);
            case "simple_maintenance" -> car -> ((Task8) Main.intersection).task8(car);
            case "complex_maintenance" -> car -> ((Task9) Main.intersection).task9(car);
            case "railroad" -> car -> ((Task10) Main.intersection).task10(car);
            default -> null;
        };
    }
}
