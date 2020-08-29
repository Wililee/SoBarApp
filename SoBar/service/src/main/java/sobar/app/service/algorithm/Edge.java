package sobar.app.service.algorithm;

import sobar.app.service.model.Business;

public class Edge {
    private final Business v;
    private final Business w;
    private final double weight;

    //constructor
    public Edge(Business v, Business w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    //these functions return the state variables
    public double weight() {
        return this.weight;
    }

    public Business start() {
        return this.v;
    }

    public Business end() {
        return this.w;
    }

    //Compares this edge to another edge in terms of weight
    public int compareTo(Edge that) {
        if (this.weight() < that.weight()) return -1;
        else if (this.weight() == that.weight()) return 0;
        return 1;
    }

}
