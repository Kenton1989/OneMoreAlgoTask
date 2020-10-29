import java.io.PrintStream;

public abstract class Answer {
    public abstract void printAns(PrintStream printer);
    
    public void printAns() {
        printAns(System.out);
    }
}
