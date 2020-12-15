package com.apd.tema2.factory;

import com.apd.tema2.entities.Intersection;
import com.apd.tema2.intersections.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Prototype Factory: va puteti crea cate o instanta din fiecare tip de implementare de Intersection.
 */
public class IntersectionFactory {
    private static Map<String, Intersection> cache = new HashMap<>();

    static {
        cache.put("simple_semaphore", new Task1());
        cache.put("simple_n_roundabout", new Task2());
        cache.put("simple_strict_1_car_roundabout", new Task345());
        cache.put("simple_strict_x_car_roundabout", new Task345());
        cache.put("simple_max_x_car_roundabout", new Task345());
        cache.put("priority_intersection", new Task6());
        cache.put("crosswalk", new Task7());
        cache.put("simple_maintenance", new Task8());
        cache.put("complex_maintenance", new Task9());
        cache.put("railroad", new Task10());
    }

    public static Intersection getIntersection(String handlerType) {
        return cache.get(handlerType);
    }

}
