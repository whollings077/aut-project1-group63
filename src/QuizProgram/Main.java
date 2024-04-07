package QuizProgram;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		String difficulty = "medium"; //for testing
		
		List<Question> questions = API.fetchQuestions(difficulty);
		
		System.out.println(questions);
	}

}
