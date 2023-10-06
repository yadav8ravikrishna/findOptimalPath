package com.finalyear.route_scheduling.utils;

import com.finalyear.route_scheduling.entity.DropPoint;

public class Edge {

    private DropPoint source;
    private DropPoint destination;
    private double weight;

    public Edge(DropPoint source, DropPoint destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public DropPoint getSource() {
        return source;
    }

    public DropPoint getDestination() {
        return destination;
    }

    public double getWeight() {
        return weight;
    }
}
