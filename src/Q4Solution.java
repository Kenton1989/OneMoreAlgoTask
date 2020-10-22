import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class Q4Solution {

    private static class searchContext {

        int currentNode;
        int hospital;

        searchContext(int c, int h) {
            currentNode = c;
            hospital = h;
        }
    }

    /**
     * Solution for question 4 in lab 2.
     *
     * @param graph A undirected unweighted graph representing the city road network.
     * @param k     the number of nearest hospitals needed to be found.
     * @return
     */

    public static void solve(CityGraph graph, int k) {
        HashSet<Integer>[] hashSets = new HashSet[graph.V()];
        LinkedList<Integer>[] queues = new LinkedList[graph.V()];
        LinkedList<searchContext> BFSQueue = new LinkedList<>();


        for (int i = 0; i < graph.V(); i++) {
            hashSets[i] = new HashSet<>();
            queues[i] = new LinkedList<>();
            if (graph.isHospital(i)) {
                hashSets[i].add(i);
                queues[i].push(i);
                BFSQueue.push(new searchContext(i, i));
            }
        }

        while (!BFSQueue.isEmpty()) {
            searchContext context = BFSQueue.removeLast();
            for (int adj : graph.adj(context.currentNode)) {
                if (hashSets[adj].size() < k && !hashSets[adj].contains(context.hospital)) {
                    hashSets[adj].add(context.hospital);
                    queues[adj].push(context.hospital);
                    BFSQueue.push(new searchContext(adj, context.hospital));
                }
            }
        }

        for (int i = 0; i < graph.V(); i++) {
            System.out.printf("Point %d:\n Nearest %d hospitals:", i, k);
            while (!queues[i].isEmpty()) {
                System.out.printf(" %d", queues[i].removeLast());
            }
            System.out.println();
        }
    }

    public class Answer {

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

        solve(builder.build(), k);
    }
}
