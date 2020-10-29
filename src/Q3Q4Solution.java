import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Q3Q4Solution {

    private static class SearchContext {
        public int currentNode;
        public int sourceHospital;
        public int distance;

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

    public Answer solve(CityGraph graph, int k) {
        @SuppressWarnings("unchecked")
        Set<Integer>[] hashSets = new Set[graph.V()];
        
        @SuppressWarnings("unchecked")
        List<Integer>[] queues = new List[graph.V()];

        @SuppressWarnings("unchecked")
        List<Integer>[] distances = new List[graph.V()];

        LinkedList<SearchContext> BFSQueue = new LinkedList<>();


        for (int i = 0; i < graph.V(); i++) {
            hashSets[i] = new HashSet<>(3*k);
            queues[i] = new ArrayList<>(k);
            distances[i] = new ArrayList<>(k);

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

        return new Q3Q4Answer(queues, distances, graph, k);
    }

    public static class Q3Q4Answer extends Answer {
        public List<Integer>[] queues, distances;
        public CityGraph graph;
        public int k;

        public Q3Q4Answer(List<Integer>[] queues, List<Integer>[] distances,
                      CityGraph graph, int k) {
            this.queues = queues;
            this.distances = distances;
            this.graph = graph;
            this.k = k;
        }

        public void printAns(PrintStream printer) {
            for (int i = 0; i < graph.V(); i++) {
                printer.printf("Point %d:\n", i);
                if (queues[i].size() < k) {
                    printer.printf("  This point cannot reach %d hospitals.\n", k);
                }
                printer.printf("  Nearest %d hospitals:\n", queues[i].size());
                for (int j = 0; j < queues[i].size(); j++) {
                    printer.printf("    Hospital: %d Distance: %d\n",
                                       queues[i].get(j), distances[i].get(j));
                }
                printer.println();
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

        Q3Q4Solution solution = new Q3Q4Solution();
        Answer answer = solution.solve(builder.build(), k);
        
        answer.printAns();

        scanner.close();
    }
}
