/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuizProgram;

import static QuizProgram.CLI.lifelineCount;
import static QuizProgram.GUILifeline.fiftyFiftyUsed;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.swing.JButton;
import javax.swing.Timer;

/**
 *
 * @author GGPC
 */
public class GUIGameplay {

    int winnings = 0;
    private static int currentQuestionNumber = 0;
    static List<Question> questions;
    public static Color darkGreen = new Color(0, 100, 0); //Custom green colour
    private static List<String> currentOptions = new ArrayList<>();
    private static String currentCorrectAnswer;
    private static Question currentQuestion;
    private static JButton[] answerButtons = {
        MainFrame.answer1, MainFrame.answer2, MainFrame.answer3, MainFrame.answer4
    };

    //Hashmap where Key = Question Number and Value = Prize Money
    static Map<Integer, Integer> questionPrizes = Map.ofEntries(
            Map.entry(0, 0),
            Map.entry(1, 1000),
            Map.entry(2, 2500),
            Map.entry(3, 5000),
            Map.entry(4, 10000),
            Map.entry(5, 25000),
            Map.entry(6, 50000),
            Map.entry(7, 100000),
            Map.entry(8, 250000),
            Map.entry(9, 500000),
            Map.entry(10, 1000000)
    );

    public GUIGameplay(List<Question> questions) {
        this.questions = questions;
    }

    public static void askNextQuestion() {
        if (currentQuestionNumber < questions.size()) {
            Question question = questions.get(currentQuestionNumber);
            currentQuestion = question;
            currentCorrectAnswer = question.getCorrect_Answer();
            
            List<String> options = new ArrayList<>(question.getIncorrect_Answers());
            currentOptions = options;
            options.add(question.getCorrect_Answer()); //Adds the correct answer to the list of answer options for a question
            
            Collections.shuffle(options); //This shuffles the collection of questions so that the correct one is in a random location

            //debug
            System.out.println(question.getQuestion());
            System.out.println(options);

            //Display the question and the shuffled list of options to the user
            MainFrame.questionNumber.setText("Question " + (currentQuestionNumber + 1) + ":");
            MainFrame.winningsLabel.setText("Current Winnings: " + questionPrizes.get(currentQuestionNumber));
            MainFrame.questionText.setText(question.getQuestion());
            
            for (JButton button : answerButtons) {
                button.setEnabled(true);
                button.setVisible(true);
            }
            
            //Remove two buttons if the question is True/False
            if (options.size() == 2) {
                MainFrame.answer3.setVisible(false);
                MainFrame.answer4.setVisible(false);
                MainFrame.answer1.setText(options.get(0));
                MainFrame.answer2.setText(options.get(1));
            } else {
                MainFrame.answer1.setText(options.get(0));
                MainFrame.answer2.setText(options.get(1));
                MainFrame.answer3.setText(options.get(2));
                MainFrame.answer4.setText(options.get(3));
            }

            //Removing previous listeners, or else variables will break.
            removeActionListeners(MainFrame.answer1);
            removeActionListeners(MainFrame.answer2);
            removeActionListeners(MainFrame.answer3);
            removeActionListeners(MainFrame.answer4);

            ActionListener answerListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton clickedButton = (JButton) e.getSource();
                    clickedButton.setEnabled(false);
                    Object source = e.getSource();
                    int selectedAnswer = 0;
                    if (source == MainFrame.answer1) {
                        selectedAnswer = 1;
                    } else if (source == MainFrame.answer2) {
                        selectedAnswer = 2;
                    } else if (source == MainFrame.answer3) {
                        selectedAnswer = 3;
                    } else if (source == MainFrame.answer4) {
                        selectedAnswer = 4;
                    }

                    //Create a timer to disable button and prevent multiple clicks
                    Timer timer = new Timer(200, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            clickedButton.setEnabled(true);
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();

                    checkAnswer(selectedAnswer, question, options);
                }
            };
            
            for (JButton button : answerButtons) {
                button.addActionListener(answerListener);
            }
        }
    }

    public static void checkAnswer(int answerNumber, Question question, List<String> options) {
        //If user gets answer correct
        if (options.get(answerNumber - 1).equals(question.getCorrect_Answer())) {
            MainFrame.questionText.setForeground(darkGreen);
            MainFrame.questionText.setFont(MainFrame.questionText.getFont().deriveFont(Font.BOLD, 32f));
            MainFrame.questionText.setText("                 Correct!");
            MainFrame.answer1.setEnabled(false);
            MainFrame.answer2.setEnabled(false);
            MainFrame.answer3.setEnabled(false);
            MainFrame.answer4.setEnabled(false);
        }
        
        //If user gets answer wrong
        else {
            MainFrame.questionText.setForeground(Color.RED);
            MainFrame.questionText.setFont(MainFrame.questionText.getFont().deriveFont(Font.BOLD));
            MainFrame.questionText.setText("Incorrect! The correct answer was " + question.getCorrect_Answer());
            MainFrame.answer1.setEnabled(false);
            MainFrame.answer2.setEnabled(false);
            MainFrame.answer3.setEnabled(false);
            MainFrame.answer4.setEnabled(false);
        }
        
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentQuestionNumber++;
                MainFrame.questionText.setForeground(Color.BLACK);
                MainFrame.questionText.setFont(MainFrame.questionText.getFont().deriveFont(Font.PLAIN, 24f));
                askNextQuestion();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    //Loops through all listeners on a button and removes them.
    private static void removeActionListeners(JButton button) {
        ActionListener[] listeners = button.getActionListeners();
        for (ActionListener listener : listeners) {
            button.removeActionListener(listener);
        }
    }

    public static List<String> getCurrentOptions() {
        return currentOptions;
    }

    public static String getCurrentCorrectAnswer() {
        return currentCorrectAnswer;
    }

    public static JButton[] getAnswerButtons() {
        return answerButtons;
    }

    public static Question getCurrentQuestion() {
        return currentQuestion;
    }
    
    
}
