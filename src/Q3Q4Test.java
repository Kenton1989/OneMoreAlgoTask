public class Q3Q4Test extends Test {

    public Q3Q4Test(String label, CityGraph graph, int K, boolean storeAns) {
        super(label, graph, K, storeAns);
    }

    @Override
    public TestResult run() {
        Q3Q4Solution solution = new Q3Q4Solution();

        long begTime = System.currentTimeMillis();
        IAnswer answer = solution.solve(graph, K);
        long endTime = System.currentTimeMillis();

        if (!storeAns) answer = null;

        return new TestResult(this, endTime - begTime, answer);
    }
    
}
