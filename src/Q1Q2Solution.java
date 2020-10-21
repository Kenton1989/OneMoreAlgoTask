import java.util.Arrays;
import java.util.Queue;

/**
 * Solution for question 1 & 2 in lab 2.
 */
public class Q1Q2Solution {
    /**
     * Solution for question 1 & 2 in lab 2.
     * 
     * @param graph A undirected unweighted graph representing a city road network.
     * @return Two int array representing the answer.
     */
    public Answer solve(CityGraph graph) {
        int[] dist = new int[graph.V()];
        int[] parent = new int[graph.V()];
        Arrays.fill(dist, -1);
        Arrays.fill(parent, -1);

        // Other Solution code

        return new Answer(dist, parent);
    }

    public class Answer {
        public int[] parent;
        public int[] dist;

        Answer(int[] nearest, int[] parent) {
            this.dist = nearest;
            this.parent = parent;
        }
    }
}