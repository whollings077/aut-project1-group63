/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuizProgram;

/**
 *
 * @author william
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class EndlessMode {

    private static final Scanner scanner = new Scanner(System.in); // scanner instance for the whole class
    private static Set<Question> askedQuestions = new HashSet<>(); // using another type of collection for the marking criteria
    private static int correctAnswersCount = 0;
    private static final Logger endlesslog = new Logger();

    public static void play(String difficulty) { // starts endless mode
        while (true) {
            Question question = fetchUniqueQuestion(difficulty);
            
            if (question == null) {
                System.out.println("No more unique questions available. Restarting...");
                askedQuestions.clear();
                continue;
            }

            boolean correct = askSingleQuestion(question);
            if (!correct) {
                break;
            }
            correctAnswersCount++;

            if (!askToContinue()) {
                break;
            }
        }

        conclude();
    }

    private static Question fetchUniqueQuestion(String difficulty) { // gets a unique question of the specified difficulty that has not been asked before.
        List<Question> questions = API.fetchQuestions(difficulty);
        for (Question question : questions) {
            if (!askedQuestions.contains(question)) {
                askedQuestions.add(question);
                return question; //A unique question 
            }
        }
        return null;
    }

    private static void conclude() {
        System.out.println("Endless mode concluded. You answered " + correctAnswersCount + " questions correctly!");
        endlesslog.write("Endless mode correct answers " + correctAnswersCount + "\n");
        correctAnswersCount = 0;
        askedQuestions.clear();
        Main.runGame(); // return to main game loop
    }

    public static boolean askSingleQuestion(Question question) {
        System.out.println("\n" + question.getQuestion());

        List<String> options = new ArrayList<>(question.getIncorrect_Answers());
        options.add(question.getCorrect_Answer());
        Collections.shuffle(options);

        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }

        System.out.println("Your answer (1-" + options.size() + "):");
        try {
            int userAnswerIndex = scanner.nextInt() - 1;

            if (userAnswerIndex < 0 || userAnswerIndex >= options.size()) {
                System.out.println("Please enter a valid number.\n");
                return false;
            }

            if (options.get(userAnswerIndex).equals(question.getCorrect_Answer())) {
                System.out.println("\nCorrect!");
                return true;
            } else {
                System.out.println("\nIncorrect. The correct answer was: " + question.getCorrect_Answer());
                return false;
            }
        } catch (Exception e) {
            System.out.println("Please enter a valid number.\n");
            return false;
        }
    }

    public static boolean askToContinue() {// handles asking the user if they want to continue playing
        System.out.println("\nWould you like to continue? (1. Yes / 2. No)");
        try {
            int input = scanner.nextInt();
            return input == 1;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Exiting endless mode.");
            return false;
        }
    }
}
