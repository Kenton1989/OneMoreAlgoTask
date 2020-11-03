package lab2algo;

import java.io.PrintStream;

public abstract class Answer {
    /**
     * Print the answer with the given printer.
     * @param printer the printer used to print answer.
     */
    protected abstract void printAnsImpl(PrintStream printer);
    
    /**
     * Print the answer to system standard output stream.
     */
    public final void printAns() {
        printAnsImpl(System.out);
    }

    /**
     * Print the answer with the given printer.
     * If the given printer is null, print nothing.
     * @param printer the printer used to print answer.
     */
    public final void printAns(PrintStream printer) {
        if (printer == null) {
            return;
        }
        System.out.println("Printing answer...");
        printAnsImpl(printer);
        System.out.println("Finished Printing");
    }
}