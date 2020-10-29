import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Solution for question 1 & 2 in lab 2.
 */
public class Q1Q2Solution {
    /**
     * Solution for question 1 & 2 in lab 2.
     * 
     * @param graph A undirected unweighted graph representing a city.
     * @return Three int array representing the answer.
     * <p>
     *      The first one is the distance between the node and the nearest hospital.
     *      If a node is hospital, the distance is 0.
     *      If a node cannot reach any hospital, the diatance is -1.
     * <p>
     *      The second one is the node id of the nearest hospital.
     *      If a node is hospital, the nearest hospital is itself.
     *      If a node cannot reach any hospital, the value is not specified.
     * <p>
     *      The third one is the parent of a node in the BFS tree.
     *      If a node is hospital, its parent is itself.
     *      If a node cannot reach any hospital, the value is not specified.
     */
    
    public Q1Q2Answer solve(CityGraph graph) {
        int[] dist = new int[graph.V()];
        int[] hosp = new int[graph.V()];
        int[] parent = new int[graph.V()];
        Arrays.fill(dist, -1);

        Queue<Integer> queue = new LinkedList<>();

        for(int h: graph.allH()) {
            queue.add(h);
            dist[h] = 0;
            hosp[h] = h;
            parent[h] = h;
        }

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int next: graph.adj(node)) {
                // distance == -1 means the node haven't been accessed.
                if (dist[next] == -1) {
                    dist[next] = dist[node] + 1;
                    hosp[next] = hosp[node];
                    parent[next] = node;
                    queue.add(next);
                }
            }
        }

        return new Q1Q2Answer(graph, dist, hosp, parent);
    }

    private static class Q1Q2Answer extends Answer {
        public CityGraph graph;
        public int[] parent;
        public int[] dist;
        public int[] hosp;

        Q1Q2Answer(CityGraph graph, int[] nearestDistance, int[] nearestHospital, int[] parent) {
            this.graph = graph;
            this.dist = nearestDistance;
            this.hosp = nearestHospital;
            this.parent = parent;
        }

        private static final String ROW_FORMAT = "%s\t%s\t%s\t%s";
        private static final String[] HEADERS = {"Node#", "Nearest", "Length", "Path"};
        private static final String TABLE_HEADER = String.format(ROW_FORMAT, HEADERS[0], HEADERS[1], HEADERS[2], HEADERS[3]);

        public void printAns(PrintStream printer) {
            System.out.println(TABLE_HEADER);
            for(int node: graph.nonH()) {
                // If the distance is -1, there are no hospital can be reached from the node.
                if (dist[node] == -1) {
                    System.out.printf(ROW_FORMAT, node, "N/A", "N/A", "N/A\n");
                    continue;
                }
                System.out.printf(ROW_FORMAT, node, hosp[node], dist[node], "");
                int next = node;
                
                while (next != parent[next]) {
                    System.out.print(next);
                    System.out.print('-');
                    next = parent[next];
                }

                System.out.println(next);
            }
        }
    }

    public static void main(String[] args) {
        Tester test = new Tester();
        Loader load = new Loader();

        CityGraph smallGraph = load.randomGraph(20, 20, 2);
        Answer ans34;

        test.printResultTableHeader();
        ans34 = test.test12("Small test for Q1Q2", smallGraph);

        System.out.println();
        System.out.println("Tested graph:");
        smallGraph.printGraph();

        System.out.println();
        System.out.println("Answer:");
        ans34.printAns();
    }
}