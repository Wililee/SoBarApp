package sobar.app.service.algorithm;

import sobar.app.service.Utils;
import sobar.app.service.model.Business;

import java.util.*;

/**
 * @author William Lee, Waseef Nayeem
 * @implSpec This class implements the Shortest Hamiltonian Path finding algorithm.
 */
public class ShortestHamiltonianPath {

    private Map<Business, Boolean> marked;
    private Graph G;
    private ArrayList<ArrayList<Business>> paths;

    public ShortestHamiltonianPath(Graph G, Business start) {
        this.G = G;
        marked = new HashMap<Business, Boolean>();
        for (Business b : G.getBars())
            marked.putIfAbsent(b, false);

        paths = new ArrayList<>();

        permutate(G.getBars(), G.getV());

        ArrayList<Business> shortest = findShortest();
        for (int i = 0; i < shortest.size() - 1; i++)
            G.addEdge(shortest.get(i), shortest.get(i + 1), distTo(shortest.get(i), shortest.get(i + 1)));
    }

    //This is based on an algorithm for a hamiltonian path permutation
    //https://www.geeksforgeeks.org/heaps-algorithm-for-generating-permutations/
    public void permutate(ArrayList<Business> a, int size) {

        if (size == 1) {
            ArrayList<Business> temp = new ArrayList<>(a);
            paths.add(temp);
        }


        for (int i = 0; i < size; i++) {
            permutate(a, size - 1);

            if (size % 2 == 1) {
                Collections.swap(a, 0, size - 1);
            } else {
                Collections.swap(a, i, size - 1);
            }
        }

    }

    //Determines if a permutation is valid for a hamiltonian path
    public boolean validPermutation(ArrayList<Business> barSeq) {
        //check if they are all marked first
        for (Business b : G.getBars())
            if (!marked.get(b)) return false;

        return G.getE() == G.getV() - 1;
    }

    public double distTo(Business a, Business b) {
        return Utils.dist(a.getLatitude(), a.getLongitude(), b.getLatitude(), b.getLongitude());
    }

    //This finds the shortest out of a list of the avalible hamiltonian paths
    public ArrayList<Business> findShortest() {
        ArrayList<Business> shortest = paths.get(0);
        double bestDist = Double.POSITIVE_INFINITY;
        for (ArrayList<Business> bars : paths) {
            double total = 0.0;
            for (int i = 0; i < bars.size() - 1; i++)
                total += distTo(bars.get(i), bars.get(i + 1));
            if (total < bestDist) {
                shortest = bars;
                bestDist = total;
            }
        }

        return shortest;
    }

}
