import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class DataLoader {
    public final static double DENSE_RATE = 0.66;
    
    public CityGraph fromFile(String roadFilePath, String hospFilePath) {
        return loadGraphFromFile(roadFilePath, hospFilePath);
    }
    
    public CityGraph random(int nodeNum, int edgeNum, int hospitalNum) {
        return loadRandomGraph(nodeNum, edgeNum, hospitalNum);
    }
    
    public CityGraph loadGraphFromFile(String roadFilePath, String hospFilePath) {
        CityGraph.Builder builder = new CityGraph.Builder();

        loadEdgesFromFile(roadFilePath, builder);
        
        loadHospitalFromFile(hospFilePath, builder);
        
        CityGraph graph = builder.build();

        return graph;
    }


    public CityGraph loadRandomGraph(int nodeNum, int edgeNum, int hospitalNum) {
        if (nodeNum < 0) {
            throw new IllegalArgumentException(
                "The input edge number "+nodeNum+" is smaller than 0.");
        }
        
        long maxEdge = (long)nodeNum * (nodeNum - 1) / 2;

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

       
        if (builder.nodeNum() < hospitalNum || hospitalNum < 0) {
            throw new IllegalArgumentException(
                "The input hospital number "+hospitalNum+
                " exceeds legal range [0, "+builder.nodeNum()+"].");
        } 

        // if hospitals are relatively dense, set all nodes as hospitals first.
        if (hospitalNum > DENSE_RATE * builder.nodeNum()) {
            for (int i = 0; i < builder.nodeNum(); i++) {
                builder.setHospital(i);
            }
        }

        while (builder.hospitalNum() != hospitalNum) {
            int h = rand.nextInt(builder.nodeNum());

            if (builder.hospitalNum() > hospitalNum) {
                builder.unsetHospital(h);
            } else {
                builder.setHospital(h);
            }
        }

        return builder.build();
    }

    private void loadEdgesFromFile(String filePath, CityGraph.Builder builder) {
        Scanner sc = getScanner(filePath);
        while (sc.hasNextLine()) {
            // Skip comment lines
            if (sc.hasNext("#")) {
                sc.nextLine();
                continue;
            }

            // After skipping comment lines, if no number can be found,
            // stop reading.
            if (!sc.hasNextInt()) {
                break;
            }

            int a, b;
            a = sc.nextInt();
            b = sc.nextInt();

            builder.addEdge(a, b);
        }

    } 
    private void loadHospitalFromFile(String filePath, CityGraph.Builder builder) {
        Scanner sc = getScanner(filePath);

        while (sc.hasNextLine()) {
            // Skip comment lines
            while (sc.hasNext("#")) {
                sc.nextLine();
            }

            // After skipping comment lines, if no number can be found,
            // stop reading.
            if (!sc.hasNextInt()) {
                break;
            }
            
            // Read hospital and set
            int h = sc.nextInt();
            builder.setHospital(h);
        }


    } 

    private Scanner getScanner(String filePath) {
        Scanner sc = null;

        try {
            sc = new Scanner(new FileInputStream(filePath));
        } catch(FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return sc;
    }

    public static void main(String[] args) {
        DataLoader loader = new DataLoader();
        // CityGraph graph = loader.loadRandomGraph(1_000_000, 10_000_000, 1000);
    }
}
