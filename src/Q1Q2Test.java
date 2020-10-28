public class Q1Q2Test extends Test {

    public Q1Q2Test(String label, CityGraph graph, boolean storeAns) {
        super(label, graph, null, storeAns);
    }
    
    @Override
    public TestResult run() {
        Q1Q2Solution solution = new Q1Q2Solution();

        long begTime = System.currentTimeMillis();
        IAnswer answer = solution.solve(graph);
        long endTime = System.currentTimeMillis();

        if (!storeAns) answer = null;

        return new TestResult(this, endTime - begTime, answer);
    }
}
