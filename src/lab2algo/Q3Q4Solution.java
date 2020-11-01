package lab2algo;

import java.io.PrintStream;
import java.util.*;

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
     * @return Answer contains the shortest distances to k nearest hospitals of each node.
     */

    public Answer solve(CityGraph graph, int k) {
        @SuppressWarnings("unchecked")
        Set<Integer>[] visitedH = new Set[graph.V()];
        
        @SuppressWarnings("unchecked")
        List<Integer>[] orderedH = new List[graph.V()];

        @SuppressWarnings("unchecked")
        List<Integer>[] distances = new List[graph.V()];

        Queue<SearchContext> BFSQueue = new LinkedList<>();


        for (int i = 0; i < graph.V(); i++) {
            visitedH[i] = new HashSet<>(2*k);
            orderedH[i] = new ArrayList<>(k);
            distances[i] = new ArrayList<>(k);

            if (graph.isHospital(i)) {
                visitedH[i].add(i);
                orderedH[i].add(i);
                distances[i].add(0);
                BFSQueue.add(new SearchContext(i, i, 0));
            }
        }

        while (!BFSQueue.isEmpty()) {
            SearchContext context = BFSQueue.poll();
            for (int adj : graph.adj(context.currentNode)) {
                if (visitedH[adj].size() < k && !visitedH[adj].contains(context.sourceHospital)) {
                    visitedH[adj].add(context.sourceHospital);
                    orderedH[adj].add(context.sourceHospital);
                    distances[adj].add(context.distance + 1);
                    BFSQueue.add(new SearchContext(adj, context.sourceHospital, context.distance + 1));
                }
            }
        }

        return new Q3Q4Answer(orderedH, distances, graph, k);
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

        @Override
        protected void printAnsImpl(PrintStream printer) {
            printer.println("All the buildings and their nearest "+k+" hospitals, and the corresponding distance.");
            // print header
            printer.print("Node\thosp?\t");
            for (int i = 1; i <= k; ++i) {
                printer.print("Hosp"+i+"\t"+"Dist"+i+"\t");
            }
            printer.println();

            // print answers
            for (int node = 0; node < graph.V(); ++node) {
                printer.print(node+"\t"+graph.isH(node)+"\t");
                for (int i = 0; i < queues[node].size(); i++) {
                    printer.print(queues[node].get(i)+"\t"+distances[node].get(i)+"\t");
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
