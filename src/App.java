public class App {
    public static void main(String[] args) {
        // aSmallTest();
        // repeatTimingTest();
        timeComplexityTest12();
        // timeComplexityTest34();
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

        CityGraph smallGraph = load.randomGraph(30, 100, 2);
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

        CityGraph graphCA = load.fromFile("real_road/CA/roadNet.txt", "real_road/CA/hospital.txt");
        CityGraph graphTX = load.fromFile("real_road/TX/roadNet.txt", "real_road/TX/hospital.txt");
        CityGraph graphPA = load.fromFile("real_road/PA/roadNet.txt", "real_road/PA/hospital.txt");

        test.printResultTableHeader();
        test.test34("Dummy Test 1", load.randomGraph(100000, 1000000, 10), 5);
        test.test34("Dummy Test 2", load.randomGraph(100000, 1000000, 10), 5);
        test.test34("Dummy Test 3", load.randomGraph(100000, 1000000, 10), 5);
        test.test34("Real Graph - CA", graphCA, 5);
        test.test34("Real Graph - TX", graphTX, 5);
        test.test34("Real Graph - PA", graphPA, 5);
    }
}
