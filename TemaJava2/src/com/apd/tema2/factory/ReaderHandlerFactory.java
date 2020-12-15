package com.apd.tema2.factory;

import com.apd.tema2.Main;
import com.apd.tema2.entities.Pedestrians;
import com.apd.tema2.entities.ReaderHandler;
import com.apd.tema2.intersections.*;

/**
 * Returneaza sub forma unor clase anonime implementari pentru metoda de citire din fisier.
 */
public class ReaderHandlerFactory {

    public static ReaderHandler getHandler(String handlerType) {
        // simple semaphore intersection
        // max random N cars roundabout (s time to exit each of them)
        // roundabout with exactly one car from each lane simultaneously
        // roundabout with exactly X cars from each lane simultaneously
        // roundabout with at most X cars from each lane simultaneously
        // entering a road without any priority
        // crosswalk activated on at least a number of people (s time to finish all of them)
        // road in maintenance - 1 lane 2 ways, X cars at a time
        // road in maintenance - N lanes 2 ways, X cars at a time
        // railroad blockage for T seconds for all the cars
        // unmarked intersection
        // cars racing
        return switch (handlerType) {
            case "simple_semaphore" -> (handlerType1, br) -> {
                Main.intersection = IntersectionFactory.getIntersection(handlerType1);
            };
            case "simple_n_roundabout" -> (handlerType2, br) -> {
                String[] line = br.readLine().split(" ");
                Main.intersection = IntersectionFactory.getIntersection(handlerType2);
                Task2.setMaxNumberOfCars(Integer.parseInt(line[0]));
                Task2.setRoundaboutTime(Integer.parseInt(line[1]));
                Task2.setTimeInSeconds(Integer.parseInt(line[1]) / 1000);
                Task2.setSemaphore(Integer.parseInt(line[0]));
            };
            case "simple_strict_1_car_roundabout" -> (handlerType3, br) -> {
                String[] line = br.readLine().split(" ");
                Main.intersection = IntersectionFactory.getIntersection(handlerType3);
                Task345.setNoOfLanes(Integer.parseInt(line[0]));
                Task345.setRoundaboutPassingTime(Integer.parseInt(line[1]));
                Task345.setTimeInSeconds(Integer.parseInt(line[1]) / 1000);
                Task345.setCyclicBarrier3(Integer.parseInt(line[0]));
                Task345.setNoOfCarsPassing(1);
                Task345.setSemaphoreVector(Integer.parseInt(line[0]), 1);
            };
            case "simple_strict_x_car_roundabout" -> (handlerType4, br) -> {
                String[] line = br.readLine().split(" ");
                Main.intersection = IntersectionFactory.getIntersection(handlerType4);
                Task345.setNoOfLanes(Integer.parseInt(line[0]));
                Task345.setRoundaboutPassingTime(Integer.parseInt(line[1]));
                Task345.setTimeInSeconds(Integer.parseInt(line[1]) / 1000);
                Task345.setNoOfCarsPassing(Integer.parseInt(line[2]));
                Task345.setCyclicBarrier2(Integer.parseInt(line[0]) * Integer.parseInt(line[2]));
                Task345.setCyclicBarrier(Main.carsNo);
                Task345.setSemaphoreVector(Integer.parseInt(line[0]), Integer.parseInt(line[2]));
            };
            case "simple_max_x_car_roundabout" -> (handlerType5, br) -> {
                String[] line = br.readLine().split(" ");
                Main.intersection = IntersectionFactory.getIntersection(handlerType5);
                Task345.setNoOfLanes(Integer.parseInt(line[0]));
                Task345.setRoundaboutPassingTime(Integer.parseInt(line[1]));
                Task345.setTimeInSeconds(Integer.parseInt(line[1]) / 1000);
                Task345.setNoOfCarsPassing(Integer.parseInt(line[2]));
                Task345.setSemaphoreVector(Integer.parseInt(line[0]), Integer.parseInt(line[2]));
            };
            case "priority_intersection" -> (handlerType6, br) -> {
                String[] line = br.readLine().split(" ");
                Main.intersection = IntersectionFactory.getIntersection(handlerType6);
                Task6.setNoOfHighPriorityCars(Integer.parseInt(line[0]));
                Task6.setNoOfLowPriorityCars(Integer.parseInt(line[1]));
                Task6.setSemaphore(Integer.parseInt(line[0]));
                Task6.setCarQueue(Integer.parseInt(line[1]));
            };
            case "crosswalk" -> (handlerType7, br) -> {
                String[] line = br.readLine().split(" ");
                Main.intersection = IntersectionFactory.getIntersection(handlerType7);
                Main.pedestrians = new Pedestrians(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
                Task7.setCanPassVector(Main.carsNo);
                Task7.setExecuteTime(Integer.parseInt(line[0]));
                Task7.setMaxNoOfPedestrians(Integer.parseInt(line[1]));
            };
            case "simple_maintenance" -> (handlerType8, br) -> {
                String[] line = br.readLine().split(" ");
                Main.intersection = IntersectionFactory.getIntersection(handlerType8);
                Task8.setNoOfCarsPassing(Integer.parseInt(line[0]));
                Task8.setNoOfLanes(2);
                Task8.setCyclicBarrier(Main.carsNo);
                Task8.setSemaphore1();
                Task8.setSemaphore2();
                Task8.setDirectionQueue0(Main.carsNo);
                Task8.setDirectionQueue1(Main.carsNo);
            };
            case "complex_maintenance" -> (handlerType9, br) -> {
                String[] line = br.readLine().split(" ");
                Main.intersection = IntersectionFactory.getIntersection(handlerType9);
            };
            case "railroad" -> (handlerType10, br) -> {
                Main.intersection = IntersectionFactory.getIntersection(handlerType10);
                Task10.setCarsQueue(Main.carsNo);
                Task10.setCyclicBarrier(Main.carsNo);
                Task10.setCyclicBarrier2(Main.carsNo);
            };
            default -> throw new IllegalStateException("Unexpected value: " + handlerType);
        };
    }

}
