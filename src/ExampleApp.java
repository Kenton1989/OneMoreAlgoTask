import java.io.PrintStream;

import lab2algo.Answer;
import lab2algo.CityGraph;
import lab2algo.Loader;
import lab2algo.Tester;

public class ExampleApp {
    public static void main(String[] args) {
        // *************************************
        // ************* Notes *****************
        // *************************************
        // the function name containing "12" means testing the solution for Q1/Q2
        // the function name containing "34" means testing the solution for Q3/Q4

        // uncomment the follow test example to run the example
        aSmallTest();
        // test12WithFiles("real_road/CA/roadNet.txt", "real_road/CA/hospital.txt", "output12-CA.txt");
        // test34WithFilesAndK("real_road/CA/roadNet.txt", "real_road/CA/hospital.txt", 3, "output34-CA.txt");
        // randomTest12(100, 1000, 5, "output12-random.txt");
        // randomTest34(100, 1000, 5, 3, "output12-random.txt");
        // repeatTimingTest();
        // timeComplexityTest12();
        // timeComplexityTest34();
        // realGraphTest12();
        // realGraphTest34();
    }

    /**
     * Testing algorithms on a small graph. Print the timing result and answer to
     * standard output.
     */
    public static void aSmallTest() {
        System.out.println("Testing on a small graph");
        Tester test = new Tester();
        Loader load = new Loader();

        CityGraph smallGraph = load.randomGraph(6, 10, 2);
        Answer ans34;

        test.printResultTableHeader();
        ans34 = test.test12("Small test for Q1Q2", smallGraph);

        System.out.println();
        System.out.println("Tested graph:");
        smallGraph.printGraph();

        System.out.println();
        System.out.println("Answer:");
        ans34.printAns();
    }

    /**
     * Testing the solution for Q1 & Q2 with the graph defined by roadNetFile and hospitalFile.
     * The answer of the algorithm is printed to the outputFile.
     */
    public static void test12WithFiles(String roadNetFile, String hospitalFile, String outputFile) {
        Tester test = new Tester();
        Loader load = new Loader();

        System.out.println("Testing the the solution to Q1/Q2 with input files: ");
        System.out.println(roadNetFile);
        System.out.println(hospitalFile);
        System.out.println("and output file: ");
        System.out.println(outputFile);
        System.out.println();

        System.out.print("Loading input file... ");
        CityGraph graph = load.fromFile(roadNetFile, hospitalFile);
        System.out.println("DONE!");

        System.out.println("Start testing");
        test.printResultTableHeader();
        test.test12("Test 12 with files", graph, load.output(outputFile));
    }

    /**
     * Testing the solution for Q3 & Q4 with the graph defined by roadNetFile and hospitalFile.
     * The answer of the algorithm is printed to the outputFile.
     */
    public static void test34WithFilesAndK(String roadNetFile, String hospitalFile, int k, String outputFile) {
        Tester test = new Tester();
        Loader load = new Loader();

        System.out.println("Testing the the solution to Q3/Q4 with input files: ");
        System.out.println(roadNetFile);
        System.out.println(hospitalFile);
        System.out.println("with K = " + k);
        System.out.println("and output file: ");
        System.out.println(outputFile);
        System.out.println();

        System.out.print("Loading input file... ");
        CityGraph graph = load.fromFile(roadNetFile, hospitalFile);
        System.out.println("DONE!");

        System.out.println("Start testing");
        test.printResultTableHeader();
        test.test34("Test 34 with files", graph, k, load.output(outputFile));
    }

    /**
     * Testing the solution for Q1 & Q2 with a random graph.
     * The generated graph is printed to the outputFile.
     * The answer of the algorithm is printed to the outputFile.
     */
    public static void randomTest12(int nodeNum, int edgeNum, int hospitalNum, String outputFile) {
        System.out.println("Testing the solution for Q1 & Q2 with random graph.");
        System.out.println("Edge number = "+edgeNum);
        System.out.println("Node number = "+nodeNum);
        System.out.println("Hospital number = "+hospitalNum);
        
        Tester test = new Tester();
        Loader load = new Loader();

        System.out.print("Generating random graph... ");
        CityGraph graph = load.randomGraph(nodeNum, edgeNum, hospitalNum);
        System.out.println("DONE!");

        PrintStream output = load.output(outputFile);
        
        graph.printGraph(output);

        System.out.println("Start testing");
        test.printResultTableHeader();
        test.test12("Random graph test", graph, output);
    }
    
    /**
     * Testing the solution for Q3 & Q4 with a random graph.
     * The generated graph is printed to the outputFile.
     * The answer of the algorithm is printed to the outputFile.
     */
    public static void randomTest34(int nodeNum, int edgeNum, int hospitalNum, int k, String outputFile) {
        System.out.println("Testing the solution for Q1 & Q2 with random graph.");
        System.out.println("Edge number = "+edgeNum);
        System.out.println("Node number = "+nodeNum);
        System.out.println("Hospital number = "+hospitalNum);
        
        Tester test = new Tester();
        Loader load = new Loader();

        System.out.print("Generating random graph... ");
        CityGraph graph = load.randomGraph(nodeNum, edgeNum, hospitalNum);
        System.out.println("DONE!");

        PrintStream output = load.output(outputFile);
        
        graph.printGraph(output);

        System.out.println("Start testing");
        test.printResultTableHeader();
        test.test34("Random graph test", graph, k, output);
    }

    /**
     * Rpeat testing the algorithm on random graphs with 100'000 node, 1'000'000
     * edges and 10 hospital. The timing result is printed to the standard output.
     * The answer of algorithm is discarded
     */
    public static void repeatTimingTest() {
        System.out.println("Repeat testing on random graph for 20 times");

        Tester test = new Tester();
        Loader load = new Loader();

        // generating the graph may take a long time
        CityGraph aGraph = load.randomGraph(100000, 1000000, 10);
        test.printResultTableHeader();
        for (int i = 0; i < 20; i++) {
            test.test34("Test Q3Q4, random graph#" + i, aGraph, 5);
        }
    }

    /**
     * Testing the time complexity for the solution to Q1 & Q2 with various size of
     * input.
     */
    public static void timeComplexityTest12() {
        System.out.println("Testing the time complexity for the solution to Q1 & Q2");

        Tester test = new Tester();
        Loader load = new Loader();

        int testNo = 0;

        test.printResultTableHeader();
        for (int v = 10; v <= 10000; v *= 10) {

            long maxE = (long) v * (v - 1) / 2 / 2;
            long de = (maxE - v) / 10;
            for (long e = v; e <= maxE; e += de) {
                
                int maxH = v / 100;
                int dh = Math.max(1, maxH / 10);
                for (int h = 1; h <= maxH; h += dh) {
                    
                    CityGraph graph = load.randomGraph(v, e, h);
                    test.test12("Timing test on Q1/2 #" + testNo, graph);
                    ++testNo;
                }
            }
        }
    }

    /**
     * Testing the time complexity for the solution to Q3 & Q4 with various size of
     * input.
     */
    public static void timeComplexityTest34() {
        System.out.println("Testing the time complexity for the solution to Q3 & Q4");
        
        Tester test = new Tester();
        Loader load = new Loader();

        int testNo = 0;

        test.printResultTableHeader();
        for (int v = 10; v <= 10000; v *= 10) {

            long maxE = (long) v * (v - 1) / 2 / 2;
            long de = maxE/ 5;
            long minE = de;
            for (long e = minE; e <= maxE; e += de) {
                
                int maxH = v / 10;
                int dh = Math.max(2, maxH / 5);
                for (int h = 1; h <= maxH; h += dh) {
                    
                    CityGraph graph = load.randomGraph(v, e, h);

                    int maxK = Math.min(h, 1000000/v);
                    int dk = Math.max(1, maxK / 5);
                    for (int k = 2; k <= maxK; k+=dk) {
                        test.test34("Timing test on Q3/4 #" + testNo, graph, k);
                        ++testNo;
                    }
                }
            }
        }
    }

    /**
     * Testing the solution for Q3 & Q4 with a real graph.
     * The answer of the algorithm is discarded.
     */
    public static void realGraphTest34() {
        System.out.println("Testing the solution for Q3 & Q4 with real graph.");
        
        Tester test = new Tester();
        Loader load = new Loader();

        System.out.print("Loading graphs... ");
        CityGraph graphCA = load.fromFile("real_road/CA/roadNet.txt", "real_road/CA/hospital.txt");
        CityGraph graphTX = load.fromFile("real_road/TX/roadNet.txt", "real_road/TX/hospital.txt");
        CityGraph graphPA = load.fromFile("real_road/PA/roadNet.txt", "real_road/PA/hospital.txt");
        System.out.println("DONE!");

        test.printResultTableHeader();
        test.test34("Dummy Test 1", load.randomGraph(100000, 1000000, 10), 5);
        test.test34("Dummy Test 2", load.randomGraph(100000, 1000000, 10), 5);
        test.test34("Dummy Test 3", load.randomGraph(100000, 1000000, 10), 5);
        test.test34("Real Graph - CA", graphCA, 5);
        test.test34("Real Graph - TX", graphTX, 5);
        test.test34("Real Graph - PA", graphPA, 5);
    }

    /**
     * Testing the solution for Q1 & Q2 with a real graph.
     * The answer of the algorithm is discarded.
     */
    public static void realGraphTest12() {
        System.out.println("Testing the solution for Q1 & Q2 with real graph.");
        
        Tester test = new Tester();
        Loader load = new Loader();

        System.out.print("Loading graphs... ");
        CityGraph graphCA = load.fromFile("real_road/CA/roadNet.txt", "real_road/CA/hospital.txt");
        CityGraph graphTX = load.fromFile("real_road/TX/roadNet.txt", "real_road/TX/hospital.txt");
        CityGraph graphPA = load.fromFile("real_road/PA/roadNet.txt", "real_road/PA/hospital.txt");
        System.out.println("DONE!");

        test.printResultTableHeader();
        test.test12("Dummy Test 1", load.randomGraph(100000, 1000000, 10));
        test.test12("Dummy Test 2", load.randomGraph(100000, 1000000, 10));
        test.test12("Dummy Test 3", load.randomGraph(100000, 1000000, 10));
        for (int i = 0; i < 10; i++) {
            test.test12("Real Graph - CA Round "+i, graphCA);
            test.test12("Real Graph - TX Round "+i, graphTX);
            test.test12("Real Graph - PA Round "+i, graphPA);
        }
    }
}
