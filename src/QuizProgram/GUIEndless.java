/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuizProgram;

import static QuizProgram.GUIGameplay.darkGreen;
import java.awt.CardLayout;

import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.Timer;

/**
 *
 * @author GGPC
 */
public class GUIEndless {

    private static int questionNumber;
    private static List<String> currentOptions = new ArrayList<>();
    private static String currentCorrectAnswer;
    private static Question currentQuestion;
    private static JButton[] answerButtons = {
        MainFrame.answer5, MainFrame.answer6, MainFrame.answer7, MainFrame.answer8
    };
    private static Set<Question> askedQuestions = new HashSet<>();
    private static String difficulty;
    static List<Question> questions;

    
    //Constructor resets variables for when a user goes back to menu and then replays
    public GUIEndless(String difficulty) {
        questionNumber = 0;
        this.difficulty = difficulty;
        
        GUILifeline.resetLifelines();

        MainFrame.endlessQuestionText.setForeground(Color.BLACK);
        MainFrame.endlessQuestionText.setFont(MainFrame.endlessQuestionText.getFont().deriveFont(Font.PLAIN, 24f));
        MainFrame.skipButton1.setBackground(new Color(204, 255, 204));
        MainFrame.fiftyFiftyButton1.setBackground(new Color(204, 255, 204));
    }

    public static void play() {
        //Reset text formatting
        MainFrame.endlessQuestionText.setForeground(Color.BLACK);
        MainFrame.endlessQuestionText.setFont(MainFrame.endlessQuestionText.getFont().deriveFont(Font.PLAIN, 24f));
        
        //Fetching new questions if list is empty
        if (questions == null || questions.isEmpty()) {
            fetchUniqueQuestions(difficulty);
        }
       
        //Asking and then removing a question from the list
        askSingleQuestion(questions.remove(0));
    }

    // Gets a set of unique questions of the specified difficulty that has not been asked before.
    public static void fetchUniqueQuestions(String difficulty) {
        questions = API.fetchQuestions(difficulty);
        
        for (Question question : questions) {
            if (!askedQuestions.contains(question)) {
                askedQuestions.add(question);
            }
        }
    }
    
    //Basically the same thing as the GUIGameplay method
    public static void askSingleQuestion(Question question) {
        currentQuestion = question;
        currentCorrectAnswer = question.getCorrect_Answer();
        questionNumber++;

        List<String> options = new ArrayList<>(question.getIncorrect_Answers());
        currentOptions = options;
        options.add(question.getCorrect_Answer());
        Collections.shuffle(options);

        //debug
        System.out.println(question.getQuestion());
        System.out.println(options);
        System.out.println("\n" + question.getCorrect_Answer());

        MainFrame.endlessQuestionNo.setText("Question " + questionNumber + ":");
        MainFrame.endlessQuestionText.setText(question.getQuestion());

        for (JButton button : answerButtons) {
            button.setVisible(true);
        }

        enableAllButtons();

        //Remove two buttons if the question is True/False
        if (options.size() == 2) {
            MainFrame.answer7.setVisible(false);
            MainFrame.answer8.setVisible(false);
            MainFrame.answer5.setText(options.get(0));
            MainFrame.answer6.setText(options.get(1));
        } else {
            MainFrame.answer5.setText(options.get(0));
            MainFrame.answer6.setText(options.get(1));
            MainFrame.answer7.setText(options.get(2));
            MainFrame.answer8.setText(options.get(3));
        }

        //Removing previous listeners, or else variables will break.
        removeActionListeners(MainFrame.answer5);
        removeActionListeners(MainFrame.answer6);
        removeActionListeners(MainFrame.answer7);
        removeActionListeners(MainFrame.answer8);

        ActionListener answerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton clickedButton = (JButton) e.getSource();
                clickedButton.setEnabled(false);
                Object source = e.getSource();
                int selectedAnswer = 0;
                if (source == MainFrame.answer5) {
                    selectedAnswer = 1;
                } else if (source == MainFrame.answer6) {
                    selectedAnswer = 2;
                } else if (source == MainFrame.answer7) {
                    selectedAnswer = 3;
                } else if (source == MainFrame.answer8) {
                    selectedAnswer = 4;
                }

                checkAnswer(selectedAnswer, question, options);
            }
        };

        for (JButton button : answerButtons) {
            button.addActionListener(answerListener);
        }
    }
    
    public static void checkAnswer(int answerNumber, Question question, List<String> options) {
        //If user gets answer correct
        if (options.get(answerNumber - 1).equals(question.getCorrect_Answer())) {
            MainFrame.endlessQuestionText.setForeground(darkGreen);
            MainFrame.endlessQuestionText.setFont(MainFrame.endlessQuestionText.getFont().deriveFont(Font.BOLD, 32f));
            MainFrame.endlessQuestionText.setText("                 Correct!");
            disableAllButtons();  //Disables buttons so user can't multiclick
            
            //Pausing for 2 seconds before asking next question
            Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                play();
            }
            });
            timer.setRepeats(false);
            timer.start();
        }
        
        //If user gets answer wrong
        else {
            MainFrame.endlessQuestionText.setForeground(Color.RED);
            MainFrame.endlessQuestionText.setFont(MainFrame.endlessQuestionText.getFont().deriveFont(Font.BOLD));
            MainFrame.endlessQuestionText.setText("Incorrect! The correct answer was " + question.getCorrect_Answer());
            disableAllButtons();
            
            Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lose();
            }
            });
            timer.setRepeats(false);
            timer.start();
            
        }
    }

    public static void disableAllButtons() {
        MainFrame.answer5.setEnabled(false);
        MainFrame.answer6.setEnabled(false);
        MainFrame.answer7.setEnabled(false);
        MainFrame.answer8.setEnabled(false);
        MainFrame.fiftyFiftyButton.setEnabled(false);
        MainFrame.skipButton.setEnabled(false);
    }

    public static void enableAllButtons() {
        MainFrame.answer5.setEnabled(true);
        MainFrame.answer6.setEnabled(true);
        MainFrame.answer7.setEnabled(true);
        MainFrame.answer8.setEnabled(true);
        MainFrame.fiftyFiftyButton.setEnabled(true);
        MainFrame.skipButton.setEnabled(true);
    }

    //Loops through all listeners on a button and removes them.
    private static void removeActionListeners(JButton button) {
        ActionListener[] listeners = button.getActionListeners();
        for (ActionListener listener : listeners) {
            button.removeActionListener(listener);
        }
    }

    //When the user loses, shows the endPanel and tells their final score.
    private static void lose() {
        CardLayout cardLayout = (CardLayout) MainFrame.mainPanel.getLayout();
        cardLayout.show(MainFrame.mainPanel, "endPanel");
        MainFrame.endTitle.setText("You Lose!");
        MainFrame.endSubtitle.setText("Your final score was: " + (questionNumber - 1));
    }

    
    //Various getters
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
