package QuizProgram;

import java.util.List;

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
	}
}
