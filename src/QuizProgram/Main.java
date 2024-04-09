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
}
