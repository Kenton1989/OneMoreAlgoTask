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
     *      If a node cannot reach any hospital, put a -1.
     * <p>
     *      The third one is the parent of a node in the BFS tree.
     *      If a node is hospital, its parent is itself.
     *      If a node cannot reach any hospital, put a -1.
     */
    
    public Answer solve(CityGraph graph) {
        int[] dist = new int[graph.V()];
        int[] hosp = new int[graph.V()];
        int[] parent = new int[graph.V()];
        Arrays.fill(dist, -1);
        Arrays.fill(hosp, -1);
        Arrays.fill(parent, -1);

        // Other Solution code
        Queue<Integer> queue = new LinkedList<>();

        return new Answer(graph, dist, hosp, parent);
    }

    public class Answer implements IAnswer {
        public CityGraph graph;
        public int[] parent;
        public int[] dist;
        public int[] hosp;

        Answer(CityGraph graph, int[] nearestDistance, int[] nearestHospital, int[] parent) {
            this.graph = graph;
            this.dist = nearestDistance;
            this.hosp = nearestHospital;
            this.parent = parent;
        }

        public void printAns() {
            
        }
    }
}