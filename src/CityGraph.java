import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A immutable undirected unweighted  graph representing the city road network
 */
public class CityGraph {

    private int nodeNum;
    private long edgeNum;
    private List<List<Integer>> edges;
    private List<Integer> hospitals;
    private List<Integer> nonHospitals;
    private boolean[] isHospital;

    public CityGraph(int nodeNum, long edgeNum, List<List<Integer>> edges, List<Integer> hospitals,
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

    public long edgeNum() {
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

    public final int V() {
        return nodeNum();
    }

    public final long E() {
        return edgeNum();
    }

    public final int H() {
        return hospitalNum();
    }

    public final boolean isH(int node) {
        return isHospital(node);
    }

    public final Iterable<Integer> adj(int node) {
        return adjacentNodes(node);
    }

    public final Iterable<Integer> allH() {
        return hospitals();
    }

    public final Iterable<Integer> nonH() {
        return nonHospitals();
    }

    private static String ROW_FORMAT = "%s\t%s\t%s";
    private static String TABLE_HEADER = String.format(ROW_FORMAT, "Node#", "is H?", "Adjacent Nodes");
    public void printGraph(PrintStream printer) {
        printer.println("City graph in Adjacent List Representation:");
        printer.println(TABLE_HEADER);
        for (int i = 0; i < V(); ++i) {
            printer.printf(ROW_FORMAT, String.valueOf(i), String.valueOf(isH(i)), "");
            for (int node: adj(i)) {
                printer.printf("%d ", node);
            }
            printer.println();
        }
    }

    public void printGraph() {
        printGraph(System.out);
    }

    /**
     * Builder for the graph
     */
    public static class Builder {
        private int edgeNum;
        private List<Set<Integer>> edges;
        private List<Boolean> isHospital;
        private int hospitalCount;

        Builder() {
            this(0);
        }

        Builder(int nodeNum) {
            edgeNum = 0;
            edges = new ArrayList<>(nodeNum);
            isHospital = new ArrayList<Boolean>(nodeNum);
            hospitalCount = 0;

            for (int i = 0; i < nodeNum; ++i) {
                edges.add(new HashSet<>());
                isHospital.add(false);
            }
        }

        public int nodeNum() {
            return edges.size();
        }

        public int edgeNum() {
            return edgeNum;
        }

        public int hospitalNum() {
            return hospitalCount;
        }

        public void addEdge(int node0, int node1) {
            updateNodeNum(node0);
            updateNodeNum(node1);

            if (node0 == node1 || edges.get(node0).contains(node1)) {
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
            updateNodeNum(node);
            
            if (!isHospital.get(node)) {
                ++hospitalCount;
                isHospital.set(node, true);
            }
        }

        public void unsetHospital(int node) {
            if (isHospital.get(node)) {
                --hospitalCount;
                isHospital.set(node, false);
            }
        }

        public CityGraph build() {
            int nodeNum = edges.size();

            List<List<Integer>> e = new ArrayList<>(nodeNum);

            for (Set<Integer> adj: edges) {
                e.add(new ArrayList<>(adj));
            }

            boolean[] isHospital = new boolean[nodeNum];

            List<Integer> hospitals = new ArrayList<>(hospitalCount);
            List<Integer> nonHospitals = new ArrayList<>(nodeNum - hospitalCount);

            for (int i = 0; i < nodeNum; ++i) {
                if (this.isHospital.get(i)) {
                    hospitals.add(i);
                    isHospital[i] = true;
                } else {
                    nonHospitals.add(i);
                }
            }

            return new CityGraph(nodeNum, edgeNum, e, hospitals, nonHospitals, isHospital);
        }

        private void updateNodeNum(int nodeId) {
            if (nodeId < nodeNum()){
                return;
            }
            for (int i = nodeNum(); i <= nodeId; ++i) {
                edges.add(new HashSet<>());
                isHospital.add(false);
            }
        }
    }

}