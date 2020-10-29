public class App {
    public static void main(String[] args) {
        // aSmallTest();
        repeatTimingTest();
        // Tester test = new Tester();
        // Loader load = new Loader();

        // System.out.println("Load a small graph.");
        // CityGraph smallRandGraph = load.randomGraph(20, 100, 4);

        // System.out.println("Loading a big graph.");
        // CityGraph bigRandGraph = load.randomGraph(1000, 20000, 1000);
        // System.out.println("Big graph loaded");

        // System.out.println("Loading real graph - PA");
        // // CityGraph caGraph = load.fromFile("real_road/PA/roadNet.txt", "real_road/PA/hospital.txt");
        // System.out.println("Loaded real graph - PA");

        // test.printResultTableHeader();

        // Answer answer =  test.test34("Small random graph", smallRandGraph, 3);
        // test.test34("Big random graph", bigRandGraph, 10);
        // // test.test34("Real road - CA", caGraph, 10, load.output("real_road/PA/answer.txt"));

        // System.out.println();

        // System.out.println("Small graph:");
        // smallRandGraph.printGraph();

        // System.out.println("Answer for small graph:");
        // answer.printAns();
    }

    /**
     * Testing algorithms on a small graph.
     * Print the timing result and answer to standard output.
     */
    public static void aSmallTest() {
        Tester test = new Tester();
        Loader load = new Loader();

        CityGraph smallGraph = load.randomGraph(20, 100, 5);
        Answer ans34;

        test.printResultTableHeader();
        ans34 = test.test34("Small test for Q3Q4", smallGraph, 3);

        System.out.println();
        System.out.println("Tested graph:");
        smallGraph.printGraph();

        System.out.println();
        System.out.println("Answer:");
        ans34.printAns();
    }

    /**
     * Rpeat testing the algorithm on random graphs with 100'000 node, 1'000'000 edges and 10 hospital.
     * The timing result is printed to the standard output.
     * The answer of algorithm is discarded
     */
    public static void repeatTimingTest() {
        Tester test = new Tester();
        Loader load = new Loader();

        test.printResultTableHeader();
        for (int i = 0; i < 20; i++) {
            CityGraph aGraph = load.randomGraph(100000, 1000000, 10);
            test.test34("Test Q3Q4, random graph#"+i, aGraph, 5);
        }
    }
}
