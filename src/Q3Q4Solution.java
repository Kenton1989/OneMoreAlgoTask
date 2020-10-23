import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Q3Q4Solution {

    private static class SearchContext {
        int currentNode;
        int sourceHospital;
        int distance;

        SearchContext(int c, int h, int d) {
            currentNode = c;
            sourceHospital = h;
            distance = d;
        }
    }

    /**
     * Solution for question 3 & 4 in lab 2.
     *
     * @param graph A undirected unweighted graph representing the city road network.
     * @param k     the number of nearest hospitals needed to be found.
     * @return
     */

    public static Answer solve(CityGraph graph, int k) {
        @SuppressWarnings("unchecked")
        Set<Integer>[] hashSets = new HashSet[graph.V()];
        
        @SuppressWarnings("unchecked")
        List<Integer>[] queues = new LinkedList[graph.V()];

        @SuppressWarnings("unchecked")
        List<Integer>[] distances = new LinkedList[graph.V()];

        LinkedList<SearchContext> BFSQueue = new LinkedList<>();


        for (int i = 0; i < graph.V(); i++) {
            hashSets[i] = new HashSet<>(2*k);
            queues[i] = new LinkedList<>();
            distances[i] = new LinkedList<>();

            if (graph.isHospital(i)) {
                hashSets[i].add(i);

                queues[i].add(i);
                distances[i].add(0);
                BFSQueue.push(new SearchContext(i, i, 0));
            }
        }

        while (!BFSQueue.isEmpty()) {
            SearchContext context = BFSQueue.removeLast();
            for (int adj : graph.adj(context.currentNode)) {
                if (hashSets[adj].size() < k && !hashSets[adj].contains(context.sourceHospital)) {
                    hashSets[adj].add(context.sourceHospital);
                    queues[adj].add(context.sourceHospital);
                    distances[adj].add(context.distance + 1);
                    BFSQueue.push(new SearchContext(adj, context.sourceHospital, context.distance + 1));
                }
            }
        }

        return new Answer(queues, distances, graph, k);
    }

    public static class Answer implements IAnswer {
        public static List<Integer>[] queues, distances;
        public static CityGraph graph;
        public static int k;

        public Answer(List<Integer>[] queues, List<Integer>[] distances,
                      CityGraph graph, int k) {
            Answer.queues = queues;
            Answer.distances = distances;
            Answer.graph = graph;
            Answer.k = k;
        }

        public void printAns() {
            for (int i = 0; i < graph.V(); i++) {
                System.out.printf("Point %d:\n", i);
                if (queues[i].size() < k) {
                    System.out.printf("  This point cannot reach %d hospitals.\n", k);
                }
                System.out.printf("  Nearest %d hospitals:\n", queues[i].size());
                for (int j = 0; j < queues[i].size(); j++) {
                    System.out.printf("    Hospital: %d Distance: %d\n",
                                       queues[i].get(j), distances[i].get(j));
                }
                System.out.println();
            }
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

        Answer answer = solve(builder.build(), k);
        answer.printAns();

        scanner.close();
    }
}
