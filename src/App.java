public class App {
    public static void main(String[] args) {
        Tester test = new Tester();
        DataLoader load = new DataLoader();

        test.printHeader();
        // test.test34("Random Graph S34", load.random(20000, 1000000, 50), 3);
        // test.test34("Random Graph S34", load.random(20000, 1000000, 50), 30);
        // CityGraph aBigGraph = load.random(20000, 1000000, 500);
        // test.test34("Random Graph S34", aBigGraph, 3);
        // test.test34("Random Graph S34", aBigGraph, 30);
        // test.test34("Random Graph S34", aBigGraph, 30);
        // test.test34("Random Graph S34", aBigGraph, 30);
        // test.test34("Random Graph S34", aBigGraph, 300);
        CityGraph caGraph = load.fromFile("real_road/PA/roadNet.txt", "real_road/PA/hospital.txt");
        test.test34("Real city - CA", caGraph, 15);
        test.test34("Real city - CA", caGraph, 40);
        test.test34("Real city - CA", caGraph, 80);
        // test.test34("Real city - CA", caGraph, 1000);
    }
}
