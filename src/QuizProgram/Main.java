package QuizProgram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String difficulty = "medium"; //for testing

        List<Question> questions = API.fetchQuestions(difficulty);

        //Test to show that it works ;)
        System.out.println(questions);
        System.out.println();
        System.out.println(questions.get(0).getQuestion());
        System.out.println(questions.get(0).getCorrect_Answer());
        System.out.println(questions.get(0).getIncorrect_Answers());
        try (Scanner scanner = new Scanner(System.in) // scanner for the CLI, we need to fix the parser as html stuff is appering in the output 
        ) {
            for (Question question : questions) {
                System.out.println(question.getQuestion());
                List<String> options = new ArrayList<>(question.getIncorrect_Answers());
                options.add(question.getCorrect_Answer()); //adds the correct answer to the list of  answer options for a question
                Collections.shuffle(options); //this shuffles the collection of questions so that the correct one is in a random location
                
                for (int i = 0; i < options.size(); i++) {
                    System.out.println((i + 1) + ". " + options.get(i));    // Display the shuffled list of options to the user
                }
                //prompt the user to choose an answer
                System.out.println("Your answer (1-" + options.size() + "):");
                int userAnswerIndex = scanner.nextInt() - 1;
                //check if the selected option equals the correct answer
                if (options.get(userAnswerIndex).equals(question.getCorrect_Answer())) {
                    System.out.println("Correct!");
                } else {
                    System.out.println("Incorrect. The correct answer is: " + question.getCorrect_Answer());
                }
            }
            // closes the scanner after the cli
        }
    }
}
