import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
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
        @SuppressWarnings("unchecked")
        List<Integer>[] dist = new List[graph.V()];
        @SuppressWarnings("unchecked")
        List<Integer>[] hosp = new List[graph.V()];
        @SuppressWarnings("unchecked")
        List<Integer>[] dist_all = new List[graph.hospitalNum()];

        for (int i = 0; i < graph.V(); i++) {
            dist[i] = new ArrayList<Integer>(k);
            hosp[i] = new ArrayList<Integer>(k);
            dist_all[i] = new ArrayList<Integer>(k);
            @SuppressWarnings("unchecked")
            List<Integer>[] temp = new List[graph.hospitalNum()];
            int j = 0;
            for (Integer h : graph.hospitals()) {
                List<Integer> s = shortestPath(graph, i, h);
                Integer d = s.size() - 1;
                temp[j].set(0, d);
                temp[j].set(1, h);
                dist_all[i].set(j, d);
                j++;
            }
            Collections.sort(dist_all[i]);
            for (int l = 0; l < k; l ++){
                if(dist_all[i].get(l) == null) {
                    break;
                }
                dist[i].set(l, dist_all[i].get(l));
                for (List<Integer> tmp : temp) {
                    if (tmp.get(0) == dist_all[i].get(l)){
                        hosp[i].set(l,tmp.get(1));
                        break;
                    }
                }
            }
        }
        return new BFAnswer(graph, dist, hosp);
    }

    public static List<Integer> shortestPath(CityGraph graph, Integer startNodeName, Integer endNodeName) {
        Map<Integer, Integer> parents = new HashMap<Integer, Integer>();
        List<Integer> temp = new ArrayList<Integer>();
    
        Integer start = startNodeName;
        temp.add(start);
        parents.put(startNodeName, null);
    
        while (temp.size() > 0) {
            Integer currentNode = temp.get(0);
            Iterable<Integer> neighbors = graph.adjacentNodes(currentNode);

            for (Integer n : neighbors) {
                // a node can only be visited once if it has more than one parents
                boolean visited = parents.containsKey(n);
                if (visited) {
                    continue;
                } else {
                    temp.add(n);
    
                    // parents map can be used to get the path
                    parents.put(n, currentNode);
    
                    // return the shortest path if end node is reached
                    if (n.equals(endNodeName)) {
                        System.out.println(parents);
                        return getPath(parents, endNodeName);
                    }
                }
            }
            temp.remove(0);
        }
    
        return null;
      }
    
    private static List<Integer> getPath(Map<Integer, Integer> parents, Integer endNodeName) {
        List<Integer> path = new ArrayList<Integer>();
        Integer node = endNodeName;
        while (node != null) {
          path.add(0, node);
          Integer parent = parents.get(node);
          node = parent;
        }
        return path;
    }

    public static class BFAnswer extends Answer {
        public CityGraph graph;
        public List<Integer>[] dist;
        public List<Integer>[] hosp;

        BFAnswer(CityGraph graph, List<Integer>[] nearestDistance, List<Integer>[] nearestHospital) {
            this.graph = graph;
            this.dist = nearestDistance;
            this.hosp = nearestHospital;
        }

        @Override
        protected void printAnsImpl(PrintStream printer) {
            
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int num = scanner.nextInt();
        CityGraph.Builder builder = new CityGraph.Builder(num);

        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            builder.addEdge(x, y);
        }

        int m = scanner.nextInt();
        for (int i = 0; i < m; i++) {
            builder.setHospital(scanner.nextInt());
        }

        int k = scanner.nextInt();

        BruteForceSolution solution = new BruteForceSolution();
        Answer answer = solution.solve(builder.build(), k);
        answer.printAns();

        scanner.close();
    }
}