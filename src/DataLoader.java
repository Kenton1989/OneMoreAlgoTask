import java.util.Random;

public class DataLoader {
    public final static double DENSE_RATE = 0.66;

    public CityGraph loadGraphFromFile(String roadFilePath, String hospFilePath) {
        CityGraph.Builder builder = new CityGraph.Builder();
        loadEdgesFromFile(roadFilePath, builder);
        loadHospitalFromFile(hospFilePath, builder);
        return builder.build();
    }

    public CityGraph loadRandomGraph(int nodeNum, int edgeNum, int hospitalNum) {
        int maxEdge = nodeNum * (nodeNum - 1) / 2;

        if (maxEdge < edgeNum || edgeNum < 0) {
            throw new IllegalArgumentException(
                "The input edge number "+edgeNum+
                " exceeds legal range [0, "+maxEdge+"].");
        }

        CityGraph.Builder builder = new CityGraph.Builder(nodeNum);
        
        // if it is a relatively dense graph, fill all the edge first.
        if (edgeNum > DENSE_RATE * maxEdge) {
            for (int i = 0; i < nodeNum; i++) {
                for (int j = i+1; j < nodeNum; j++) {
                    builder.addEdge(i, j);
                }
            }
        }

        Random rand = new Random();
        
        while (builder.edgeNum() != edgeNum) {
            int a = rand.nextInt(nodeNum);
            int b = rand.nextInt(nodeNum);

            if (builder.edgeNum() < edgeNum) {
                builder.addEdge(a, b);
            } else {
                builder.removeEdge(a, b);
            }
        }

        return builder.build();
    }

    private void loadEdgesFromFile(String filePath, CityGraph.Builder builder) {


    } 
    private void loadHospitalFromFile(String filePath, CityGraph.Builder builder) {


    } 
}
