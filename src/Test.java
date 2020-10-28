public abstract class Test {
    private static final String[] HEADERS = {"Label", "V", "E", "H", "K"};
    private static final String ROW_FORMAT = "%-60s %-8s %-15s %-8s %-8s";
    public static final String TABLE_HEADER = String.format(ROW_FORMAT, HEADERS[0], HEADERS[1], HEADERS[2], HEADERS[3], HEADERS[4]);


    public String label;
    public CityGraph graph;
    public Integer K;
    public boolean storeAns;

    public Test(String label, CityGraph graph, Integer K, boolean storeAns) {
        this.label = label;
        this.graph = graph;
        this.K = K;
        this.storeAns = storeAns;
    }

    @Override
    public String toString() {
        String vStr = String.valueOf(graph.V());
        String eStr = String.valueOf(graph.E());
        String hStr = String.valueOf(graph.H());
        String kStr = K == null ? "N/A" : String.valueOf(K);
        
        return String.format(ROW_FORMAT, label, vStr, eStr, hStr, kStr);
    }

    public abstract TestResult run();
}
