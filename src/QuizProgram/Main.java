package QuizProgram;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        runGame();
    }
    
    public static void runGame(){
        String difficulty = CLI.start(); //Starting the game and asking for difficulty
        List<Question> questions = API.fetchQuestions(difficulty); 
        CLI.ask(questions);
    }
    
    public static void exit() {
        System.out.println("\nExiting the game...\nThanks for playing!");
        System.exit(0);
    }
}
