
public class TestResult {
    private static final String[] HEADERS = {"Label", "V", "E", "H", "K", "Runtime(ms)"};
    private static final String ROW_FORMAT = "%-30s %-8s %-15s %-8s %-8s %-10s";
    public static final String TABLE_HEADER = String.format(ROW_FORMAT, HEADERS[0], HEADERS[1], HEADERS[2], HEADERS[3], HEADERS[4], HEADERS[5]);

    public Test test;
    public long time;
    public IAnswer answer;

    public TestResult(Test test, long time, IAnswer ans) {
        this.test = test;
        this.time = time;
        this.answer = ans;
    }

    @Override
    public String toString() {
        String vStr = String.valueOf(test.graph.V());
        String eStr = String.valueOf(test.graph.E());
        String hStr = String.valueOf(test.graph.H());
        String tStr = String.valueOf(time);
        String kStr = test.K == null ? "N/A" : String.valueOf(test.K);
        
        return String.format(ROW_FORMAT, test.label, vStr, eStr, hStr, kStr, tStr);
    }
}