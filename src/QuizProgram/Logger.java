/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuizProgram;

/**
 *
 * @author william
 */

public class Logger {
    private final String filePath = "log.txt"; // The file path remains unchanged

    public Logger() {
        // Constructor remains empty as there's no need to initialize anything specific
    }

    public void write(String message) {
        // Directly use the FileInputOutput.append method to write the message
        FileInputOutput.append(message, filePath);
    }
}