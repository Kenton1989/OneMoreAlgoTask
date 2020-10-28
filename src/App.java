public class App {
    public static void main(String[] args) {
        Tester test = new Tester();
        DataLoader load = new DataLoader();

        System.out.println("Load a small graph.");
        CityGraph smallRandGraph = load.random(20, 100, 4);

        System.out.println("Loading a big graph.");
        CityGraph bigRandGraph = load.random(1000, 20000, 1000);
        System.out.println("Big graph loaded");

        // System.out.println("Loading real graph - PA");
        // CityGraph caGraph = load.fromFile("real_road/PA/roadNet.txt", "real_road/PA/hospital.txt");
        // System.out.println("Loaded real graph - PA");

        test.printResultTableHeader();

        Answer smallAns = test.test34("Small random graph", smallRandGraph, 3);
        test.test34("Big random graph", bigRandGraph, 10);
        // test.test34("real graph - PA", bigRandGraph, 3);

        System.out.println();

        System.out.println("Small graph:");
        smallRandGraph.printGraph();

        System.out.println("Answer for small graph:");
        smallAns.printAns();

        
    }
}
