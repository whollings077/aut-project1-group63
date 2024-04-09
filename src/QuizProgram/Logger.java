/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuizProgram;

/**
 *
 * @author william
 */
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.IOException;

public class Logger {
    private PrintWriter pw = null;
    private final String filePath = "log.txt";

    public Logger() {
    }

    public void open() {
        try {
            // open the file from the path set in append mode
            pw = new PrintWriter(new FileOutputStream(filePath, true));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("\nFile open failed!\n");
        }
    }

    public void write(String message) {
        if (pw != null) {
            pw.println(message);
            pw.flush(); //ensure it is written immediately
        } else {
            System.out.println("use open() first logging is not initialised");
        }
    }

    public void close() {
        if (pw != null) {
            pw.close();
            pw = null; // close printwriter
        }
    }
    public static void main(String[] args) {
        Logger logtest = new Logger(); // Assuming the Logging class is already defined
        logtest.open();
        logtest.write("test");
        
    }
}
