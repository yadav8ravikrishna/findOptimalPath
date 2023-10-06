package com.finalyear.route_scheduling.service.serviceImpl;

import com.finalyear.route_scheduling.entity.DropPoint;
import com.finalyear.route_scheduling.repository.DropPointRepo;
import com.finalyear.route_scheduling.service.FindPath;
import com.finalyear.route_scheduling.utils.Graph;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FindPathImpl implements FindPath {
    private final DropPointRepo repo;

    public FindPathImpl(DropPointRepo repo) {
        this.repo = repo;
    }

    @Override
    public Set<DropPoint> warehouses() {
        return repo.warehouses();
    }

    @Override
    public Set<DropPoint> dropPointsOrdered(Set<DropPoint> selectedDropPoints) {
        return null;
    }

    @Override
    public Set<DropPoint> selectedDropPoints(Set<Integer> dropPointIds) {
        return repo.selectedLocations(dropPointIds);
    }
    @Override
    public DropPoint suitableWarehouse(Set<DropPoint> selectedDropPoints, Set<DropPoint> warehouses) {
        double sumOfLat = 0;
        double sumOfLong = 0;

        for (DropPoint dp : selectedDropPoints) {
            sumOfLat += dp.getLatitude();
            sumOfLong += dp.getLongitude();
        }

        double avgLat = sumOfLat / selectedDropPoints.size();
        double avgLong = sumOfLong / selectedDropPoints.size();

        DropPoint nearestWarehouse = null;
        double shortestDistance = Double.MAX_VALUE;

        for (DropPoint warehouse : warehouses) {
            double distance = calculateHaversineDistance(avgLat, avgLong, warehouse.getLatitude(), warehouse.getLongitude());
            if (distance < shortestDistance) {
                shortestDistance = distance;
                nearestWarehouse = warehouse;
            }
        }

        return nearestWarehouse;
    }
    @Override
    public List<DropPoint> findSuitablePathDijkstra(Set<DropPoint> warehouses, Set<DropPoint> selectedDropPoints) {
        DropPoint nearestWarehouse = suitableWarehouse(warehouses,selectedDropPoints);
        // Create a graph with the selected drop points
        Graph graph = new Graph(selectedDropPoints);

        // Calculate distances between all drop points and add edges to the graph
        for (DropPoint source : selectedDropPoints) {
            for (DropPoint destination : selectedDropPoints) {
                if (!source.equals(destination)) {
                    double distance = calculateHaversineDistance1(source, destination);
                    graph.addEdge(source, destination, distance);
                }
            }
        }

        // Run Dijkstra's algorithm to find the shortest paths
        Map<DropPoint, Double> shortestDistances = graph.dijkstra(nearestWarehouse);

        // Create a list to store the sorted drop points
        List<DropPoint> shortestPath = new ArrayList<>(selectedDropPoints);

        // Sort the drop points based on their distances from the nearest warehouse
        shortestPath.sort(Comparator.comparingDouble(shortestDistances::get));

        return shortestPath;
    }





    @Override
    public List<DropPoint> findSuitablePathGreedy(DropPoint nearestWarehouse, Set<DropPoint> selectedDropPoints) {
        List<DropPoint> path = new ArrayList<>();
        Set<DropPoint> unvisitedDropPoints = new HashSet<>(selectedDropPoints);

        DropPoint currentLocation = nearestWarehouse;

        while (!unvisitedDropPoints.isEmpty()) {
            DropPoint nearestDropPoint = null;
            double minDistance = Double.MAX_VALUE;

            for (DropPoint dp : unvisitedDropPoints) {
                assert currentLocation != null;
                double distance = calculateHaversineDistance1(currentLocation, dp);
                if (distance < minDistance) {
                    nearestDropPoint = dp;
                    minDistance = distance;
                }
            }

            path.add(nearestDropPoint);
            currentLocation = nearestDropPoint;
            unvisitedDropPoints.remove(nearestDropPoint);
        }

        return path;
    }


    @Override
    public Map<String, Object> comparePaths(Set<DropPoint> warehouses, Set<DropPoint> selectedDropPoints) {

        DropPoint nearestWarehouse = suitableWarehouse(warehouses,selectedDropPoints);

        Map<String, Object> results = new HashMap<>();

        // Calculate Dijkstra's path and distance
        List<DropPoint> dijkstraPath = findSuitablePathDijkstra(warehouses, selectedDropPoints);
        double dijkstraDistance = calculateTotalDistance(dijkstraPath);

        // Calculate Greedy path and distance
        List<DropPoint> greedyPath = findSuitablePathGreedy(nearestWarehouse, selectedDropPoints);
        double greedyDistance = calculateTotalDistance(greedyPath);

        // Calculate the time taken for both paths
        double avgSpeed = 35.0; // Average speed in km/h
        double dijkstraTime = dijkstraDistance / avgSpeed;
        double greedyTime = greedyDistance / avgSpeed;

        // Calculate the difference in distance and time
        double distanceDifference = dijkstraDistance - greedyDistance;
        double timeDifference = dijkstraTime - greedyTime;

        results.put("DijkstraPath", dijkstraPath);
        results.put("DijkstraDistance", dijkstraDistance);
        results.put("GreedyPath", greedyPath);
        results.put("GreedyDistance", greedyDistance);
        results.put("DistanceDifference", distanceDifference);
        results.put("DijkstraTime", dijkstraTime);
        results.put("GreedyTime", greedyTime);
        results.put("TimeDifference", timeDifference);

        return results;
    }

    private double calculateTotalDistance(List<DropPoint> path) {
        double totalDistance = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            totalDistance += calculateHaversineDistance1(path.get(i), path.get(i + 1));
        }
        return totalDistance;
    }

    private double calculateHaversineDistance1(DropPoint point1, DropPoint point2) {
        final int R = 6371; // Earth's radius in kilometers

        double lat1 = Math.toRadians(point1.getLatitude());
        double lon1 = Math.toRadians(point1.getLongitude());
        double lat2 = Math.toRadians(point2.getLatitude());
        double lon2 = Math.toRadians(point2.getLongitude());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // Distance in kilometers
    }

    private double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth's radius in kilometers

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // Distance in kilometers
    }


}
