package com.finalyear.route_scheduling.utils;

import com.finalyear.route_scheduling.entity.DropPoint;

import java.util.*;

public class Graph {

    public Map<DropPoint, List<Edge>> adjacencyList;

    public Graph(Set<DropPoint> dropPoints) {
        this.adjacencyList = new HashMap<>();
        for (DropPoint dropPoint : dropPoints) {
            this.adjacencyList.put(dropPoint, new ArrayList<>());
        }
    }

    public void addEdge(DropPoint source, DropPoint destination, double weight) {
        Edge edge = new Edge(source, destination, weight);
        this.adjacencyList.get(source).add(edge);
    }

    public Map<DropPoint, Double> dijkstra(DropPoint source) {
        Map<DropPoint, Double> distances = new HashMap<>();
        for (DropPoint dropPoint : this.adjacencyList.keySet()) {
            distances.put(dropPoint, Double.POSITIVE_INFINITY);
        }

        distances.put(source, 0.0);

        Set<DropPoint> visited = new HashSet<>();

        while (!visited.containsAll(this.adjacencyList.keySet())) {
            DropPoint currentDropPoint = getClosestUnvisitedDropPoint(distances, visited);

            if (currentDropPoint == null) {
                // All remaining nodes are unreachable, break the loop.
                break;
            }

            visited.add(currentDropPoint);

            if (this.adjacencyList.get(currentDropPoint) != null) {
                for (Edge edge : this.adjacencyList.get(currentDropPoint)) {
                    DropPoint adjacentDropPoint = edge.getDestination();
                    double newDistance = distances.get(currentDropPoint) + edge.getWeight();

                    if (newDistance < distances.get(adjacentDropPoint)) {
                        distances.put(adjacentDropPoint, newDistance);
                    }
                }
            }
        }

        return distances;
    }


    private DropPoint getClosestUnvisitedDropPoint(Map<DropPoint, Double> distances, Set<DropPoint> visited) {
        DropPoint closestDropPoint = null;
        double minDistance = Double.POSITIVE_INFINITY;

        for (DropPoint dropPoint : this.adjacencyList.keySet()) {
            if (!visited.contains(dropPoint)) {
                double distance = distances.get(dropPoint);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestDropPoint = dropPoint;
                }
            }
        }

        return closestDropPoint;
    }
}