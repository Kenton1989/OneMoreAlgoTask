import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class Q4Solution {

    private static class searchContext {

        int currentNode;
        int hospital;
        int distance;

        searchContext(int c, int h, int d) {
            currentNode = c;
            hospital = h;
            distance = d;
        }
    }

    /**
     * Solution for question 4 in lab 2.
     *
     * @param graph A undirected unweighted graph representing the city road network.
     * @param k     the number of nearest hospitals needed to be found.
     * @return
     */

    public static Answer solve(CityGraph graph, int k) {
        HashSet<Integer>[] hashSets = new HashSet[graph.V()];
        LinkedList<Integer>[] queues = new LinkedList[graph.V()];
        LinkedList<Integer>[] distances = new LinkedList[graph.V()];
        LinkedList<searchContext> BFSQueue = new LinkedList<>();


        for (int i = 0; i < graph.V(); i++) {
            hashSets[i] = new HashSet<>();
            queues[i] = new LinkedList<>();
            distances[i] = new LinkedList<>();

            if (graph.isHospital(i)) {
                hashSets[i].add(i);
                queues[i].push(i);
                distances[i].push(0);
                BFSQueue.push(new searchContext(i, i, 0));
            }
        }

        while (!BFSQueue.isEmpty()) {
            searchContext context = BFSQueue.removeLast();
            for (int adj : graph.adj(context.currentNode)) {
                if (hashSets[adj].size() < k && !hashSets[adj].contains(context.hospital)) {
                    hashSets[adj].add(context.hospital);
                    queues[adj].push(context.hospital);
                    distances[adj].push(context.distance + 1);
                    BFSQueue.push(new searchContext(adj, context.hospital, context.distance + 1));
                }
            }
        }

        return new Answer(queues, distances, graph, k);
    }

    public static class Answer {
        private static LinkedList<Integer>[] queues, distances;
        private static CityGraph graph;
        private static int k;

        public Answer(LinkedList<Integer>[] queues, LinkedList<Integer>[] distances,
                      CityGraph graph, int k) {
            Answer.queues = queues;
            Answer.distances = distances;
            Answer.graph = graph;
            Answer.k = k;
        }

        public void printout() {
            for (int i = 0; i < graph.V(); i++) {
                System.out.printf("Point %d:\n", i);
                if (queues[i].size() < k) {
                    System.out.printf("  This point cannot reach %d hospitals.\n", k);
                }
                System.out.printf("  Nearest %d hospitals:\n", queues[i].size());
                while (!queues[i].isEmpty()) {
                    System.out.printf("    Hospital: %d ", queues[i].removeLast());
                    System.out.printf("Distance: %d\n", distances[i].removeLast());
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
        answer.printout();
    }
}
