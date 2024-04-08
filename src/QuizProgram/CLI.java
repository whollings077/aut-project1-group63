package QuizProgram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

//test!

public class CLI {
	public static String start() {
		String difficulty = new String();
		int input = 0;
		int diffInput = 0;
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to Who Wants to Be a Millionaire!\n\nEnter a number to navigate the menu.\n1. How to Play\n2. Start Game\n");
		
		//Checking if user input is valid
		while (true) {
            try {
                input = scanner.nextInt();
                if (input == 1 || input == 2) {
                    break;
                } else {
                    System.out.println("Please input a valid number.\n");
                }
            } catch (Exception e) {
                System.out.println("Please input a valid number.\n");
                scanner.next();
            }
        }
		
		switch(input) {
			case 1: //If user selects "How to Play"
				System.out.println("\nIn this game you will be asked 10 random questions at a difficulty of your choosing!\nYou will be presented with 4 possible answers but only ONE will be correct.\n");
				System.out.println("You have two lifelines which can each help you out with a question if you're stuck.\n50:50 will get rid of two incorrect answers. Skip will skip the current question.\n");
				System.out.println("To answer questions, simply type in the number corresponding to your answer.\nTo activate your lifeline, input 5 or 6 instead of answering.\n");
				System.out.println("Each question you answer will earn you more money and if you get all 10 correct, you win $1,000,000!");
			case 2: //If user selects "Start Game"
				System.out.println("\nPlease choose a difficulty.\n1. Easy\n2. Medium\n3. Hard\n");

				while (true) {
		            try {
		                diffInput = scanner.nextInt();
		                if (diffInput == 1 || diffInput == 2 || diffInput == 3) {
		                    break;
		                } else {
		                    System.out.println("Please input a valid number.\n");
		                }
		            } catch(Exception e) {
		                System.out.println("Please input a valid number.\n");
		                scanner.next();
		            }
		        }
			
			//Setting the user's chosen difficulty
			switch(diffInput) {
					case 1:
						difficulty = "easy";
						break;
					case 2:
						difficulty = "medium";
						break;
					case 3:
						difficulty = "hard";
						break;
				}
		}	
		return difficulty;
	}

	public static void ask(List<Question> questions) {
        try (Scanner scanner = new Scanner(System.in)) //Scanner for the CLI, we need to fix the parser as html stuff is appearing in the output 
        {
          for (Question question : questions) {
              System.out.println("\n" + question.getQuestion());
              List<String> options = new ArrayList<>(question.getIncorrect_Answers());
              options.add(question.getCorrect_Answer()); //Adds the correct answer to the list of  answer options for a question
              Collections.shuffle(options); //This shuffles the collection of questions so that the correct one is in a random location
              
              for (int i = 0; i < options.size(); i++) {
                  System.out.println((i + 1) + ". " + options.get(i)); //Display the shuffled list of options to the user
              }
              
              //Prompts the user to choose an answer
              System.out.println("\nYour answer (1-" + options.size() + "):");
              while(true) {
            	  try {
            		  int userAnswerIndex = scanner.nextInt() - 1;
            		  
            		  //Error handling if user enters invalid number
            		  if (userAnswerIndex < 0 || userAnswerIndex >= options.size()) {
            	            System.out.println("Please enter a valid number.\n");
            	            continue;
            	      }
            		 
                      //check if the selected option equals the correct answer
                      if (options.get(userAnswerIndex).equals(question.getCorrect_Answer())) {
                          System.out.println("\nCorrect!");
                      } else {
                          System.out.println("\nIncorrect. The correct answer is: " + question.getCorrect_Answer());
                      }
                      
                      //Pausing for a second so the user isn't bombarded with too much text at once
                      Thread.sleep(1000);
                      break;
            	  }
            	  catch(Exception e) {
            		  System.out.println("Please enter a valid number.\n");
            		  scanner.next();
            	  }
              }
             
          }
      }
		
	}
	
	
}
