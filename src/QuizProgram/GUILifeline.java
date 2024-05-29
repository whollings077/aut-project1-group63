/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuizProgram;

import static QuizProgram.MainFrame.fiftyFiftyButton;
import static QuizProgram.MainFrame.skipButton;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;

/**
 *
 * @author GGPC
 */
class GUILifeline {

    static boolean fiftyFiftyUsed = false;
    static boolean skipUsed = false;

    public static void fiftyFifty(List<String> options, String correctAnswer, JButton... buttons) {
        //Check if player has already used lifeline or if question is true/false
        if (fiftyFiftyUsed || options.size() == 2) {
            return; 
        }

        //Change color of button and change boolean
        fiftyFiftyButton.setBackground(new Color(255, 153, 153));
        fiftyFiftyUsed = true;
        
        List<Integer> incorrectAnswers = new ArrayList<>();
        for (int i = 0; i < options.size(); i++) {
            if (!options.get(i).equals(correctAnswer)) {
                incorrectAnswers.add(i);
            }
        }

        //Hide the buttons with the incorrect answers
        buttons[incorrectAnswers.get(0)].setVisible(false);
        buttons[incorrectAnswers.get(1)].setVisible(false);
    }

    public static void skip(Question question, List<String> options) {
        //Check if player has already used lifeline
        if (skipUsed) {
            return;
        }
        
        skipButton.setBackground(new Color(255, 153, 153));
        skipUsed = true;
        
        String correctAnswer = question.getCorrect_Answer();
        
        for (int i = 0; i < options.size(); i++) {
        if (options.get(i).equals(correctAnswer)) {
            GUIGameplay.checkAnswer(i + 1, question, options);
            break;
        }
    }
        
    }

}
