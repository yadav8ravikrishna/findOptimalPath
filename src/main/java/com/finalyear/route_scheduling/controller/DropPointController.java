package com.finalyear.route_scheduling.controller;

import com.finalyear.route_scheduling.entity.DropPoint;
import com.finalyear.route_scheduling.service.FindPath;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class DropPointController {
    private final FindPath service;

    public DropPointController(FindPath findPath) {
        this.service = findPath;
    }

    @GetMapping("/warehouses")
    public Set<DropPoint> warehouses(){
        return service.warehouses();
    }


    @GetMapping("/comparePaths")
    public Map<String,Object> comparePath(@RequestBody Set<Integer> dropPointIds){
        Set<DropPoint> selectedDropPoints = service.selectedDropPoints(dropPointIds);
        Set<DropPoint> warehouses = service.warehouses();
        return service.comparePaths(warehouses,selectedDropPoints);
    }

    @GetMapping("/DijkstraSuitablePath")
    public List<DropPoint> DijkstraSuitablePath(@RequestBody Set<Integer> dropPointIds){
        Set<DropPoint> selectedDropPoints = service.selectedDropPoints(dropPointIds);
        Set<DropPoint> warehouses = service.warehouses();
        return service.findSuitablePathDijkstra(warehouses,selectedDropPoints);
    }

    @GetMapping("/suitableWarehouse")
    public DropPoint SuitableWarehouse(@RequestBody Set<Integer> dropPointIds){
        Set<DropPoint> selectedDropPoints = service.selectedDropPoints(dropPointIds);
        Set<DropPoint> warehouses = service.warehouses();
        return service.suitableWarehouse(selectedDropPoints,warehouses);
    }


}
