package QuizProgram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CLI {

    public static String start() {
        String difficulty = new String();
        int input = 0;
        int diffInput = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Who Wants to Be a Millionaire!\n\nEnter a number to navigate the menu. Enter '0' at any time to exit.\n1. How to Play\n2. Start Game\n3. Leaderboard");

        Logger clilog = new Logger();
        clilog.open(); // open log file

        OUTER:
        while (true) {
            try {
                input = scanner.nextInt();
                switch (input) {
                    case 1:
                    case 2:
                    case 3:
                        // Assuming there's an OUTER label for a loop that encloses this snippet
                        break OUTER;
                    case 0:
                        exit();
                        break; // This break is technically not needed after exit() but included for consistency
                    default:
                        System.out.println("Please input a valid number.\n");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Please input a valid number.\n");
                scanner.next(); // Clears the scanner buffer to avoid infinite loop
            }
        }

        switch (input) {
            case 1: //If user selects "How to Play"
                System.out.println("\nIn this game you will be asked 10 random questions at a difficulty of your choosing!\nYou will be presented with 4 possible answers but only ONE will be correct.\n");
                System.out.println("You have two lifelines which can each help you out with a question if you're stuck.\n50:50 will get rid of two incorrect answers (Only works on questions with 4 answers)\nSkip will skip the current question.\n");
                System.out.println("To answer questions, simply type in the number corresponding to your answer.\nTo activate your lifeline, input 5 or 6 instead of answering.\n");
                System.out.println("Each question you answer will earn you more money and if you get all 10 correct, you win $1,000,000! Get one wrong and it's back to zero!");
                System.out.println("At certain points, you will be able to cash out the money that you've earned so far and secure your place on the leaderboard.");

                clilog.write("\nUser selected How to Play");
            case 2: //If user selects "Start Game"
                System.out.println("\nPlease choose a difficulty.\n1. Easy\n2. Medium\n3. Hard");

                OUTER:
                /*                while (true) {
                    try {
                        diffInput = scanner.nextInt();
                        switch (diffInput) {
                            case 1, 2, 3 -> {
                                break OUTER;
                            }
                            case 0 ->
                                exit();
                            default ->
                                System.out.println("Please input a valid number.\n");
                        }
                    } catch (Exception e) {
                        System.out.println("Please input a valid number.\n");
                        clilog.write("User input invaid number\n");
                        scanner.next();
                    }
                }*/
                while (true) {//attempted fix
                    try {
                        diffInput = scanner.nextInt();
                        if (diffInput >= 1 && diffInput <= 3) {
                            break; // break loop if valid difficulty is chosen
                        } else if (diffInput == 0) {
                            exit();
                        } else {
                            System.out.println("Please input a valid number.\n");
                            clilog.write("User input invalid number for difficulty selection\n");
                        }
                    } catch (Exception e) {
                        System.out.println("Please input a valid number.\n");
                        scanner.next(); // clear the buffer
                        clilog.write("Exception caught during difficulty selection. User input may not be a number.\n");
                    }
                }
                //Setting the user's chosen difficulty
/*                switch (diffInput) {
                    case 1 -> {
                        difficulty = "easy";
                        clilog.write("User selected easy difficulty\n");
                    }
                    case 2 -> {
                        difficulty = "medium";
                        clilog.write("User selected medium difficulty\n");
                    }
                    case 3 -> {
                        difficulty = "hard";
                        clilog.write("User selected hard difficulty\n");
                    }
                }
                 */
                switch (diffInput) { //another fix
                    case 1:
                        difficulty = "easy";
                        clilog.write("User selected easy difficulty\n");
                        break;
                    case 2:
                        difficulty = "medium";
                        clilog.write("User selected medium difficulty\n");
                        break;
                    case 3:
                        difficulty = "hard";
                        clilog.write("User selected hard difficulty\n");
                        break;
                    default:
                        //should not be reachable due to the validation above
                        break;
                }

        }
        clilog.close(); // close logger
        return difficulty;

    }

    public static void ask(List<Question> questions) {
        int winnings = 0;
        int currentQuestionNumber = 0;
        boolean fiftyFiftyUsed = false;
        boolean skipUsed = false;

        //Hashmap where Key = Question Number and Value = Prize Money
        Map<Integer, Integer> questionPrizes = Map.ofEntries(
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

                //Tells the user about their available lifelines
                if (fiftyFiftyUsed == false && skipUsed == false) {
                    System.out.println("\nYou have both lifelines available (5,6)");
                } else if (fiftyFiftyUsed == false && skipUsed == true) {
                    System.out.println("\nYou have your Skip lifeline available (6)");
                } else if (fiftyFiftyUsed == true && skipUsed == false) {
                    System.out.println("\nYou have your 50:50 lifeline available (5)");
                } else if (fiftyFiftyUsed == true && skipUsed == true) {
                    System.out.println("\nYou have no lifelines available");
                }

                //Prompts the user to choose an answer
                System.out.println("Your answer (1-" + options.size() + "):");
                while (true) {
                    try {
                        int userAnswerIndex = scanner.nextInt() - 1;

                        switch (userAnswerIndex) {
                            case -1: // Exits
                                exit();

                            case 4: // Uses 50:50
                                if (fiftyFiftyUsed == true) {
                                    System.out.println("\nYou have already used your 50:50 lifeline.");
                                    continue;
                                }
                                if (options.size() != 4) { // If the question is a true or false 
                                    System.out.println("\nYou cannot use your 50:50 lifeline for this question.");
                                    continue;
                                }

                                Lifeline.fiftyFifty(options, question.getCorrect_Answer());
                                fiftyFiftyUsed = true;
                                System.out.println("\n" + question.getQuestion());
                                for (int i = 0; i < options.size(); i++) {
                                    System.out.println((i + 1) + ". " + options.get(i)); // Display the updated options 
                                }
                                continue;

                            case 5: // Uses Skip
                                if (skipUsed == true) {
                                    System.out.println("\nYou have already used your skip lifeline.");
                                    continue;
                                }

                                System.out.println("\nSkipping the current question...");
                                skipUsed = true;

                                // Changing variables as if the user got the answer correct
                                currentQuestionNumber++;
                                winnings = questionPrizes.get(currentQuestionNumber);
                                userAnswerIndex = options.indexOf(question.getCorrect_Answer());
                                break;
                        }

                        //Error handling if user enters invalid number
                        if (userAnswerIndex < 0 || userAnswerIndex >= options.size()) {
                            System.out.println("Please enter a valid number.\n");
                            continue;
                        }

                        //check if the selected option equals the correct answer
                        if (options.get(userAnswerIndex).equals(question.getCorrect_Answer())) {
                            currentQuestionNumber++;
                            winnings = questionPrizes.get(currentQuestionNumber);
                            System.out.println("\nCorrect!\nYour current winnings are $" + winnings);
                        } else {
                            System.out.println("\nIncorrect. The correct answer is: " + question.getCorrect_Answer());
                            System.out.println("You lose! Your final winnings were $" + winnings);
                            Thread.sleep(1500); // Pause for effect
                            System.out.println("\nWould you like to try again?\n1. Yes\n2. No");

                            OUTER:
                            while (true) {
                                try {
                                    int loseInput = scanner.nextInt();
                                    /*                                    switch (loseInput) {
                                        case 1 -> {
                                            System.out.println("");
                                            Main.runGame(); // Restarts gane
                                            return;
                                        }
                                        case 2 ->
                                            exit(); // Quits game
                                        default ->
                                            System.out.println("Please input a valid number.");
                                    }
                                     */
                                    switch (loseInput) {
                                        case 1:
                                            System.out.println("");
                                            Main.runGame(); // Restarts game
                                            return;

                                        case 2:
                                            exit(); // Quits game

                                        default:
                                            System.out.println("Please input a valid number.");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Please input a valid number.\n");
                                    scanner.next();
                                }
                            }
                        }

                        //Pausing for a second so the user isn't bombarded with too much text at once
                        Thread.sleep(1000);
                        break;
                    } catch (Exception e) {
                        System.out.println("Please enter a valid number.\n");
                        scanner.next();
                    }
                }

                // Win state ~ When leaderboard is added, this will set score and name on leaderboard
                if (winnings == 1000000) {
                    System.out.println("\nCongratulations! You got all 10 questions correct and won a million dollars!");
                    System.out.println("Head over to the leaderboard to see your name!");
                    break;
                }
            }
        }

    }

    private static void exit() {
        System.out.println("\nExiting the game...\nThanks for playing!");
        System.exit(0);
    }
}
