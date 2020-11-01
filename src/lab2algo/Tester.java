package lab2algo;

import java.io.PrintStream;

/**
 * A tool helping testing the algorithm.
 * The timing result will be printed in table format.
 * Each test function will print one row of result.
 * The header of table need to be printed specially.
 * <p>
 * The timing result will be printed to the standard output stream by default, unless the output stream be specified.
 * The answer of each test will be discard by default, unless the output stream be specified.
 */
public class Tester {
    private static final String[] HEADERS = {"Label", "V", "E", "H", "K", "Runtime(ms)"};
    private static final String ROW_FORMAT = "%-30s %-9s %-16s %-8s %-8s %-10s";

    public static final String TESTER_HEADER = String.format(ROW_FORMAT, HEADERS[0], HEADERS[1], HEADERS[2], HEADERS[3], HEADERS[4], HEADERS[5]);
    public static final PrintStream DEFAULT_OUTPUT = System.out;

    private PrintStream timingOutput;

    /**
     * A tester that prints the timing result to std output
     */
    public Tester() {
        this(DEFAULT_OUTPUT);
    }

    /**
     * A tester that prints the timing result to the given output stream,
     * @param timingOutput the stream to output the result,
     */
    public Tester(PrintStream timingOutput) {
        this.timingOutput = timingOutput;
    }

    /**
     * Print the header of the testing result table,
     */
    public void printResultTableHeader() {
        timingOutput.println(TESTER_HEADER);
    }
    
    /**
     * Testing solution for Q1 & Q2.
     * The answer of the algorithm will not be printed.
     * @param testLabel the lable of testing
     * @param graph the graph as the algorithm's input
     * @return the answer produced by the algorithm
     */
    public Answer test12(String testLabel, CityGraph graph) {
        return test12(testLabel, graph, null);
    }
    
    /**
     * Testing solution for Q1 & Q2.
     * The answer of the algorithm will be printed to the given output stream.
     * @param testLabel the lable of testing
     * @param graph the graph as the algorithm's input
     * @param ansOutput the output stream to print the result of answer
     * @return the answer produced by the algorithm
     */
    public Answer test12(String testLabel, CityGraph graph, PrintStream ansOutput) {
        testLabel = formatLabel(testLabel);
        Q1Q2Solution solution = new Q1Q2Solution();

        long begTime = System.currentTimeMillis();
        Answer answer = solution.solve(graph);
        long endTime = System.currentTimeMillis();

        printRow(testLabel, graph.V(), graph.E(), graph.H(), null, endTime-begTime);

        answer.printAns(ansOutput);

        return answer;
    }

    /**
     * Testing solution for Q3 & Q4.
     * The answer of the algorithm will not be printed.
     * @param testLabel the lable of testing
     * @param graph the graph as the algorithm's input
     * @param k the number of the nearest hospitals need to be found by the algorithm
     * @return the answer produced by the algorithm
     */
    public Answer test34(String testLabel, CityGraph graph, int k) {
        return test34(testLabel, graph, k, null);
    }

    /**
     * Testing solution for Q3 & Q4.
     * The answer of the algorithm will be printed to the given output stream.
     * @param testLabel the lable of testing
     * @param graph the graph as the algorithm's input
     * @param k the number of the nearest hospitals need to be found by the algorithm
     * @param ansOutput the output stream to print the result of answer
     * @return the answer produced by the algorithm
     */
    public Answer test34(String testLabel, CityGraph graph, int k, PrintStream ansOutput) {
        testLabel = formatLabel(testLabel);

        Q3Q4Solution solution = new Q3Q4Solution();

        long begTime = System.currentTimeMillis();
        Answer answer = solution.solve(graph, k);
        long endTime = System.currentTimeMillis();

        printRow(testLabel, graph.V(), graph.E(), graph.H(), k, endTime-begTime);

        answer.printAns(ansOutput);

        return answer;
    }

    /**
     * Testing brute force solution.
     * The answer of the algorithm will not be printed.
     * @param testLabel the lable of testing
     * @param graph the graph as the algorithm's input
     * @param k the number of the nearest hospitals need to be found by the algorithm
     * @return the answer produced by the algorithm
     */
    public Answer testBF(String testLabel, CityGraph graph, int k) {
        return testBF(testLabel, graph, k, null);
    }

    /**
     * Testing solution for Q3 & Q4.
     * The answer of the algorithm will be printed to the given output stream.
     * @param testLabel the lable of testing
     * @param graph the graph as the algorithm's input
     * @param k the number of the nearest hospitals need to be found by the algorithm
     * @param ansOutput the output stream to print the result of answer
     * @return the answer produced by the algorithm
     */
    public Answer testBF(String testLabel, CityGraph graph, int k, PrintStream output) {
        testLabel = formatLabel(testLabel);
        BruteForceSolution solution = new BruteForceSolution();

        long begTime = System.currentTimeMillis();
        Answer answer = solution.solve(graph, k);
        long endTime = System.currentTimeMillis();

        printRow(testLabel, graph.V(), graph.E(), graph.H(), k, endTime-begTime);

        answer.printAns(output);

        return answer;
    }

    private void printRow(String label, int V, long E, int H, Integer K, long time) {
        String vStr = String.valueOf(V);
        String eStr = String.valueOf(E);
        String hStr = String.valueOf(H);
        String tStr = String.valueOf(time);
        String kStr = K == null ? "N/A" : String.valueOf(K);

        timingOutput.println(String.format(ROW_FORMAT, label, vStr, eStr, hStr, kStr, tStr));
    }

    private String formatLabel(String label) {
        if (label == null || label.trim().length() == 0) {
            return "No Label";
        }
        return label;
    }
}
