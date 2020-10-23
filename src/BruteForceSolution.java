import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BruteForceSolution {
    /**
     * Brute force solution for question in lab 2.
     * 
     * @param graph A undirected unweighted graph representing the city road network.
     * @param k The number of nearest hospital to be found.
     * @return two array of List of Integer representing the answer.
     * <p>
     *         First one store k nearest hospitals for each node.
     *         Each element of array is a array contains the list of nearest hospital.
     *         That is, arr[N] = list of k hospitals that are the nearest hospital to node N.
     *         For any hospital H, arr[H] can be any format, even a null.
     *         If from a node N, only X hospitals can be reached and X < K, then arr[N] is a list
     *         of size X.
     * <p>
     *         Second one store the distance to k nearest hospitals for each node.
     *         Each element of array is a array contains the list of diatance to a hospital.
     *         That is, arr[N] = list of distance to k hospitals that are the nearest hospital to node N.
     *         For any hospital H, [H] can be any format, even a null.
     *         If from a node N, only X hospitals can be reached and X < K, then arr[N] is a list
     *         of size X.
     */
    public Answer solve(CityGraph graph, int k) {
        List<Integer>[] dist = new List[graph.V()];
        List<Integer>[] hosp = new List[graph.V()];

        for (int i = 0; i < graph.V(); i++) {
            dist[i] = new ArrayList<Integer>(k);
            hosp[i] = new ArrayList<Integer>(k);
        }

        Queue<Integer> queue = new LinkedList<>();


        return new Answer(graph, dist, hosp);
    }

    

    public class Answer {
        public CityGraph graph;
        public List<Integer>[] dist;
        public List<Integer>[] hosp;

        Answer(CityGraph graph, List<Integer>[] nearestDistance, List<Integer>[] nearestHospital) {
            this.graph = graph;
            this.dist = nearestDistance;
            this.hosp = nearestHospital;
        }

        public void printAnswer() {
            
        }
    }
}
