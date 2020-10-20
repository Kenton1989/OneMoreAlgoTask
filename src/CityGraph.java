import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A immutable undirected unweighted  graph representing the city road network
 */
public class CityGraph {

    private int nodeNum;
    private int edgeNum;
    private List<List<Integer>> edges;
    private List<Integer> hospitals;
    private List<Integer> nonHospitals;
    private boolean[] isHospital;

    public CityGraph(int nodeNum, int edgeNum, List<List<Integer>> edges, List<Integer> hospitals,
            List<Integer> nonHospitals, boolean[] isHospital) {

        this.nodeNum = nodeNum;
        this.edgeNum = edgeNum;
        this.edges = edges;
        this.hospitals = hospitals;
        this.nonHospitals = nonHospitals;
        this.isHospital = isHospital;
    }

    public int nodeNum() {
        return nodeNum;
    }

    public int edgeNum() {
        return edgeNum;
    }

    public int hospitalNum() {
        return hospitals.size();
    }

    public boolean isHospital(int node) {
        return isHospital[node];
    }

    public Iterable<Integer> adjacentNodes(int node) {
        return edges.get(node);
    }

    public Iterable<Integer> hospitals() {
        return this.hospitals;
    }

    public Iterable<Integer> nonHospitals() {
        return this.nonHospitals;
    }

    public int V() {
        return nodeNum();
    }

    public int E() {
        return edgeNum();
    }

    public int H() {
        return hospitalNum();
    }

    public boolean isH(int node) {
        return isHospital(node);
    }

    public Iterable<Integer> adj(int node) {
        return adjacentNodes(node);
    }

    public Iterable<Integer> allH() {
        return hospitals();
    }

    public Iterable<Integer> nonH() {
        return nonHospitals();
    }



    /**
     * Builder for the graph
     */
    public static class Builder {
        private int nodeNum;
        private int edgeNum;
        private List<Set<Integer>> edges;
        private boolean[] isHospital;
        private int hospitalCount;

        Builder(int totalNodeNum) {
            nodeNum = totalNodeNum;
            edgeNum = 0;
            edges = new ArrayList<>(nodeNum);
            isHospital = new boolean[nodeNum];
            hospitalCount = 0;

            for (int i = 0; i < totalNodeNum; ++i) {
                edges.add(new HashSet<>());
            }
        }

        public void addEdge(int node0, int node1) {
            if (edges.get(node0).contains(node1)) {
                return;
            }
            ++edgeNum;
            edges.get(node0).add(node1);
            edges.get(node1).add(node0);
        }
        
        public void removeEdge(int node0, int node1) {
            if (!edges.get(node0).contains(node1)) {
                return;
            }
            --edgeNum;
            edges.get(node0).remove(node1);
            edges.get(node1).remove(node0);
        }

        public void setHospital(int node) {
            if (!isHospital[node]) {
                ++hospitalCount;
                isHospital[node] = true;
            }
        }

        public void unsetHospital(int node) {
            if (isHospital[node]) {
                --hospitalCount;
                isHospital[node] = false;
            }
        }

        public CityGraph build() {
            List<List<Integer>> e = new ArrayList<>(nodeNum);

            for (Set<Integer> adj: edges) {
                e.add(new ArrayList<>(adj));
            }

            List<Integer> hospitals = new ArrayList<>(hospitalCount);
            List<Integer> nonHospitals = new ArrayList<>(nodeNum - hospitalCount);

            for (int i = 0; i < nodeNum; ++i) {
                if (isHospital[i]) {
                    hospitals.add(i);
                } else {
                    nonHospitals.add(i);
                }
            }

            return new CityGraph(nodeNum, edgeNum, e, hospitals, nonHospitals, isHospital);
        }
    }

}