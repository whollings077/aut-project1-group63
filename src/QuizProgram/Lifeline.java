/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuizProgram;

import java.util.List;
import java.util.Random;

/**
 *
 * @author Matthew Warn
 */
public class Lifeline {
    public static void fiftyFifty(List<String> options, String correctAnswer){
        Random random = new Random();
        int correctIndex = options.indexOf(correctAnswer);

        //Removing a random incorrect answer
        int removeIndex;
        do {
            removeIndex = random.nextInt(options.size());
        } while (removeIndex == correctIndex);
        options.remove(removeIndex);

        //Removing a second random incorrect answer
        int secondRemoveIndex;
        do {
            secondRemoveIndex = random.nextInt(options.size());
        } while (secondRemoveIndex == correctIndex || secondRemoveIndex == removeIndex);
        options.remove(secondRemoveIndex);
    }   
}
