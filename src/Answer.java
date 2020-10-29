import java.io.PrintStream;

public abstract class Answer {
    /**
     * Print the answer with the given printer.
     * @param printer the printer used to print answer.
     */
    public abstract void printAns(PrintStream printer);
    
    /**
     * Print the answer to system standard output stream.
     */
    public void printAns() {
        printAns(System.out);
    }
}
