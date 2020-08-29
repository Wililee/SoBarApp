package sobar.app.service.algorithm;

import sobar.app.service.model.Business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//This is a basic graph for the business type
public class Graph {

    private Map<Business, ArrayList<Edge>> adj = new HashMap<>();
    private int E = 0;
    private int V = 0;

    //adds a vertex to the graph
    public void addVertex(Business b) {
        adj.putIfAbsent(b, new ArrayList<>());
        V++;
    }

    //adds an edge tot the graph
    public void addEdge(Business v, Business w, double weight) {
        adj.get(v).add(new Edge(v, w, weight));
        adj.get(w).add(new Edge(w, v, weight));
        E++;
    }

    //returns the list of all the vertices in the graph
    public ArrayList<Business> getBars() {
        return new ArrayList<Business>(adj.keySet());
    }

    //returns the adjanceny list
    public Map<Business, ArrayList<Edge>> adj() {
        return adj;
    }

    public int getV() {
        return V;
    }

    public int getE() {
        return E;
    }

}
