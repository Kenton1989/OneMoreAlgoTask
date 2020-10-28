public class Tester {
    private static final String[] HEADERS = {"Label", "V", "E", "H", "K", "Runtime(ms)"};
    private static final String ROW_FORMAT = "%-30s %-8s %-15s %-8s %-8s %-10s";

    public static final String TESTER_HEADER = String.format(ROW_FORMAT, HEADERS[0], HEADERS[1], HEADERS[2], HEADERS[3], HEADERS[4], HEADERS[5]);

    Tester() {
    }

    public void printResultTableHeader() {
        System.out.println(TESTER_HEADER);
    }
    
    public Answer test12(String testLabel, CityGraph graph) {
        Q1Q2Solution solution = new Q1Q2Solution();

        long begTime = System.currentTimeMillis();
        Answer answer = solution.solve(graph);
        long endTime = System.currentTimeMillis();

        printRow(testLabel, graph.V(), graph.E(), graph.H(), null, endTime-begTime);

        return answer;
    }

    public Answer test34(String testLabel, CityGraph graph, int k) {
        Q3Q4Solution solution = new Q3Q4Solution();

        long begTime = System.currentTimeMillis();
        Answer answer = solution.solve(graph, k);
        long endTime = System.currentTimeMillis();

        printRow(testLabel, graph.V(), graph.E(), graph.H(), k, endTime-begTime);

        return answer;
    }

    public Answer testBF(String testLabel, CityGraph graph, int k) {
        BruteForceSolution solution = new BruteForceSolution();

        long begTime = System.currentTimeMillis();
        Answer answer = solution.solve(graph, k);
        long endTime = System.currentTimeMillis();

        printRow(testLabel, graph.V(), graph.E(), graph.H(), k, endTime-begTime);

        return answer;
    }

    private void printRow(String label, int V, int E, int H, Integer K, long time) {
        String vStr = String.valueOf(V);
        String eStr = String.valueOf(E);
        String hStr = String.valueOf(H);
        String tStr = String.valueOf(time);
        String kStr = K == null ? "N/A" : String.valueOf(K);

        System.out.println(String.format(ROW_FORMAT, label, vStr, eStr, hStr, kStr, tStr));
    }
}
