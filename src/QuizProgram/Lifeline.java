/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuizProgram;

import java.util.ArrayList;
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
        
        //Code didn't work when using original options, but making a copy works
        List<String> optionsCopy = new ArrayList<>(options);
        

        //Removing a random incorrect answer
        int removeIndex;
        
        while(true){
            removeIndex = random.nextInt(optionsCopy.size());
            
            if(removeIndex != correctIndex){
                options.remove(optionsCopy.get(removeIndex));
                break;
            }
        }

        //Removing a second random incorrect answer
        int secondRemoveIndex;
        
        while(true){
            secondRemoveIndex = random.nextInt(optionsCopy.size());
            if(secondRemoveIndex != correctIndex && secondRemoveIndex != removeIndex){
                options.remove(optionsCopy.get(secondRemoveIndex));
                break;
            }
        }
    }   
}
