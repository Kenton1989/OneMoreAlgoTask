import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Q3Solution {

    private static class searchContext {

        int currentNode;
        int hospital;

        searchContext(int c, int h) {
            currentNode = c;
            hospital = h;
        }
    }

    /**
     * Solution for question 3 in lab 2.
     *
     * @param graph A undirected unweighted graph representing a city road network.
     * @return
     */
    public static void solve(CityGraph graph) {

        int[] pre1 = new int[graph.V()],
                pre2 = new int[graph.V()],
                hos1 = new int[graph.V()],
                hos2 = new int[graph.V()];
        Arrays.fill(pre1, -2);
        Arrays.fill(pre2, -2);
        Arrays.fill(hos1, -2);
        Arrays.fill(hos2, -2);

        LinkedList<searchContext> queue = new LinkedList<>();
        for (int h : graph.allH()) {
            queue.push(new searchContext(h, h));
            pre1[h] = -1;
            hos1[h] = h;
        }


        while (!queue.isEmpty()) {
//            System.out.println("One step");
            searchContext context = queue.removeLast();
            System.out.printf("%d %d\n", context.currentNode, context.hospital);
            for (int adjacent : graph.adj(context.currentNode)) {
                if (pre1[adjacent] == -2) {
                    pre1[adjacent] = context.currentNode;
                    hos1[adjacent] = context.hospital;
                    queue.push(new searchContext(adjacent, context.hospital));
                } else if (pre2[adjacent] == -2 && hos1[adjacent] != context.hospital) {
                    pre2[adjacent] = context.currentNode;
                    hos2[adjacent] = context.hospital;
                    queue.push(new searchContext(adjacent, context.hospital));
                }
            }
        }

        System.out.println(Arrays.toString(pre1));
        System.out.println(Arrays.toString(hos1));
        System.out.println(Arrays.toString(pre2));
        System.out.println(Arrays.toString(hos2));

        for (int i = 0; i < graph.V(); i++) {
            System.out.printf("Point %d:\n", i);

            System.out.printf("First nearest hospital: %d\n", hos1[i]);
            int next = pre1[i];
            System.out.printf("Route: %d", i);
            while (next != -1) {
                System.out.printf(" -> %d", next);
                next = hos1[next] == hos1[i] ? pre1[next] : pre2[next];
            }
            System.out.println();

            System.out.printf("Second nearest hospital: %d\n", hos2[i]);
            next = pre2[i];
            System.out.printf("Route: %d", i);
            while (next != -1) {
                System.out.printf(" -> %d", next);
                next = hos1[next] == hos2[i] ? pre1[next] : pre2[next];
            }
            System.out.println();
            System.out.println();
        }

//        return null;
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

        solve(builder.build());
    }

}
