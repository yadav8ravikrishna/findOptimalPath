package com.finalyear.route_scheduling.service;

import com.finalyear.route_scheduling.entity.DropPoint;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface FindPath {
    Set<DropPoint> warehouses();

    Set<DropPoint> dropPointsOrdered(Set<DropPoint> selectedDropPoints);

    Set<DropPoint> selectedDropPoints(Set<Integer> dropPointIds);

    DropPoint suitableWarehouse(Set<DropPoint> selectedDropPoints, Set<DropPoint> warehouses);

    List<DropPoint> findSuitablePathDijkstra(Set<DropPoint> warehouses, Set<DropPoint> selectedDropPoints);

    List<DropPoint> findSuitablePathGreedy(DropPoint nearestWarehouse, Set<DropPoint> selectedDropPoints);

    Map<String, Object> comparePaths(Set<DropPoint> warehouses, Set<DropPoint> selectedDropPoints);
}
