/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuizProgram;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author william
 */
public class FileInputOutput {

    public static void append(String textToAppend, String filename) {

        try (FileWriter fileWriter = new FileWriter(filename, true)) {// true to append
            fileWriter.write(textToAppend);
            fileWriter.write(System.lineSeparator()); // append a new line after the text
        } catch (IOException e) {
            System.err.println("An error occurred while appending to file: " + filename + " " + e.getMessage());
        }
    }

    public static void overwrite(String newText, String filename) {
        try (FileWriter fileWriter = new FileWriter(filename, false)) { // false to overwrite
            fileWriter.write(newText);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    public static String read(String filename) {
        String content = ""; //initialsise string
        try {
            content = new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage()); 
       }
        return content;
    }

}
