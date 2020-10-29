import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * A tool used to load graph and file.
 */
public class Loader {
    public final static double DENSE_RATE = 0.66;

    /**
     * Load input graph from given files
     * @param roadFilePath the road net file to read
     * @param hospFilePath the hospital file to read
     * @return the city graph defined by the given files
     */
    public final CityGraph fromFile(String roadFilePath, String hospFilePath) {
        return loadGraphFromFile(roadFilePath, hospFilePath);
    }
    
    /**
     * Load a random input graph with the given node number, edge number and hospital number 
     * @param nodeNum the number of node of graph to be generated
     * @param edgeNum the number of edge of graph to be generated
     * @param hospitalNum the number of hospital in graph to be generated
     * @return a city graph with the given node number, edge number and hospital number
     */
    public final CityGraph randomGraph(int nodeNum, long edgeNum, int hospitalNum) {
        return loadRandomGraph(nodeNum, edgeNum, hospitalNum);
    }

    /**
     * Load a file as output for the answer.
     * @param filePath the file used as output file
     * @return a PrintStream binded to the given file
     */
    public final PrintStream output(String filePath) {
        return loadOutputFile(filePath);
    }

    /**
     * Load a printer that will discard all the output it received
     * @return a printer that will discard all the output it received
     */
    public PrintStream noOutput() {
        return new PrintStream(OutputStream.nullOutputStream());
    }
    

    /**
     * Load input graph from given files
     * @param roadFilePath the road net file to read
     * @param hospFilePath the hospital file to read
     * @return the city graph defined by the given files
     */
    public CityGraph loadGraphFromFile(String roadFilePath, String hospFilePath) {
        CityGraph.Builder builder = new CityGraph.Builder();

        loadEdgesFromFile(roadFilePath, builder);
        
        loadHospitalFromFile(hospFilePath, builder);
        
        CityGraph graph = builder.build();

        return graph;
    }


    /**
     * Load a random input graph with the given node number, edge number and hospital number 
     * @param nodeNum the number of node of graph to be generated
     * @param edgeNum the number of edge of graph to be generated
     * @param hospitalNum the number of hospital in graph to be generated
     * @return a city graph with the given node number, edge number and hospital number
     */
    public CityGraph loadRandomGraph(int nodeNum, long edgeNum, int hospitalNum) {
        // Arguments validation
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
       
        if (nodeNum < hospitalNum || hospitalNum < 0) {
            throw new IllegalArgumentException(
                "The input hospital number "+hospitalNum+
                " exceeds legal range [0, "+nodeNum+"].");
        } 

        CityGraph.Builder builder = new CityGraph.Builder(nodeNum);
        Random rand = new Random();

        // Generate edges
        // if it is a relatively dense graph, fill all the edge first.
        if (edgeNum > DENSE_RATE * maxEdge) {
            for (int i = 0; i < nodeNum; i++) {
                for (int j = i+1; j < nodeNum; j++) {
                    builder.addEdge(i, j);
                }
            }
        }
        while (builder.edgeNum() != edgeNum) {
            int a = rand.nextInt(nodeNum);
            int b = rand.nextInt(nodeNum);

            if (builder.edgeNum() < edgeNum) {
                builder.addEdge(a, b);
            } else {
                builder.removeEdge(a, b);
            }
        }

        // Generate hospital
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

    /**
     * Load a file as output for the answer.
     * @param filePath the file used as output file
     * @return a PrintStream binded to the given file
     */
    public PrintStream loadOutputFile(String filePath) {
        PrintStream printer = null;

        try {
            printer = new PrintStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return printer;
    }

    /**
     * Generate a hospital definition file on a city.
     * @param filePath the output file for the definition of hospital.
     * @param nodeNum the number of node in the city.
     * @param hospitalNum the number of hospital to generate
     */
    public void generateRandHospital(String filePath, int nodeNum, int hospitalNum) {
        // Arguments validation
        if (nodeNum < 0) {
            throw new IllegalArgumentException(
                "The input edge number "+nodeNum+" is smaller than 0.");
        }
       
        if (nodeNum < hospitalNum || hospitalNum < 0) {
            throw new IllegalArgumentException(
                "The input hospital number "+hospitalNum+
                " exceeds legal range [0, "+nodeNum+"].");
        } 
        
        // Generate hospital
        Set<Integer> hospSet = new HashSet<>(2 * hospitalNum);
        Random rand = new Random();
        
        while (hospSet.size() < hospitalNum) {
            int randH = rand.nextInt(nodeNum);
            hospSet.add(randH);
        }
        
        // output the result
        PrintStream out = getPrinter(filePath);
        out.println("# " + hospitalNum);
        for (int h: hospSet) {
            out.println(h);
        }
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
    
    private PrintStream getPrinter(String filePath) {
        PrintStream printer = null;

        try {
            printer = new PrintStream(filePath);
        } catch(FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return printer;
    }

    public static void main(String[] args) {
        Loader loader = new Loader();
        loader.generateRandHospital("real_road/TX/hospital.txt", 1379917, 1377);
    }
}
