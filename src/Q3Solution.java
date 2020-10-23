import java.util.Scanner;

public class Q3Solution {
    /**
     * Solution for question 3 in lab 2.
     *
     * @param graph A undirected unweighted graph representing a city road network.
     * @return
     */
    public static Q4Solution.Answer solve(CityGraph graph) {
        return Q4Solution.solve(graph, 2);
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

        Q4Solution.Answer answer = solve(builder.build());
        answer.printout();
    }

}
