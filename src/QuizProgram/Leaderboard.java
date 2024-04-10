/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuizProgram;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Matthew Warn
 */
public class Leaderboard {

    //Asks if the user would like to log their score
    public static void leaderboardPrompt(int winnings) {
        Logger leaderboardPromptLog = new Logger();
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWould you like to add your name and score to the leaderboard?\n1. Yes\n2. No");

        OUTER:
        while (true) {
            try {
                int leadChoice = scanner.nextInt();

                switch (leadChoice) {
                    case 1: //Yes
                        leaderboardPromptLog.write("Leaderboard Choice Yes\n");
                        addToLeaderboard(winnings);
                    case 2: //No
                        System.out.println("\n");
                        leaderboardPromptLog.write("Leaderboard Choice No Running Game\n");
                        Main.runGame();
                    case 0: //Exit
                        Main.exit();

                    default:
                        System.out.println("Please input a valid number.\n");
                }
            } catch (Exception e) {
                System.out.println("Please input a valid number.\n");
                leaderboardPromptLog.write("Leaderboard choice error\n");
                scanner.next();
            }
        }
    }

    public static void addToLeaderboard(int winnings) {
        //Getting player's name
        Logger addToLeaderboardlog = new Logger();
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter your name:");
        String playerName = scanner.nextLine();

        try {
            //Write player's name and winnings to the appropriate leaderboard file
            if (winnings == 1000000) {
                try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("millionaires.txt", true)))) {
                    String data = playerName + " " + CLI.lifelineCount + " " + CLI.leaderboardDiff;
                    FileInputOutput.append(data, "millionaires.txt");
                    addToLeaderboardlog.write("Successfully added " + playerName + " to millionaires.txt\n");
                }
            } else {
                try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("near_millionaires.txt", true)))) {
                    String data = winnings + " " + playerName + " " + CLI.lifelineCount + " " + CLI.leaderboardDiff;
                    FileInputOutput.append(data, "near_millionaires.txt");
                    addToLeaderboardlog.write("Successfully added " + playerName + " with winnings of " + winnings + " to near_millionaires.txt\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to update leaderboard." + e.getMessage());
            addToLeaderboardlog.write("Failed to update leaderboard for " + playerName + " with exception: " + e.getMessage() + "\n");
        }

        //Takes player back to main menu
        System.out.println("\n");
        Main.runGame();
    }

    public static void displayLeaderBoard() {
        Logger displayLeaderBoardlog = new Logger();

        Scanner scanner = new Scanner(System.in);

        //A list of player objects for each leaderboard
        List<Player> millionaires = loadMillionaires();
        List<Player> nearMillionaires = loadNearMillionaires();

        //Sorting the near millionaires by winnings, highest to lowest
        Collections.sort(nearMillionaires, Comparator.comparing(Player::getWinnings).reversed());
        displayLeaderBoardlog.write("Displaying Leaderboards\n");
        //Printing Leaderboards
        System.out.println("\n~~~~~~~LIST OF MILLIONAIRES~~~~~~~");
        for (Player player : millionaires) {
            System.out.println("-~=" + player.getName() + "=~- | Difficulty: " + player.getDifficulty() + " | Lifelines Used: " + player.getLifelineCount());
        }

        System.out.println("\nList of Near Millionaires:");
        for (Player player : nearMillionaires) {
            System.out.println(player.getName() + " | Winnings: $" + player.getWinnings() + " | Difficulty: " + player.getDifficulty() + " | Lifelines Used: " + player.getLifelineCount());
        }

        System.out.println("\nEnter 0 to exit to main menu.");

        while (true) {
            try {
                int exitChoice = scanner.nextInt();

                switch (exitChoice) {
                    case 0:
                        displayLeaderBoardlog.write("User exited to main menu\n");
                        Main.runGame(); //Back to main menu

                    default:
                        displayLeaderBoardlog.write("Invalid number input for exiting leaderboard display\n");
                        System.out.println("Please input a valid number.");
                }
            } catch (Exception e) {
                System.out.println("Please input a valid number.");
                displayLeaderBoardlog.write("Exception caught during leaderboard display exit choice: " + e.getMessage() + "\n");
                scanner.next();
            }
        }
    }

    //Reads the millionaires text file and creates Player objects
    private static List<Player> loadMillionaires() {
        Logger loadMillionaireslog = new Logger();
        List<Player> millionaires = new ArrayList<>();

        try {
            String data = FileInputOutput.read("millionaires.txt");
            String[] lines = data.split("\\r?\\n");

            for (String line : lines) {
                String[] parts = line.split("\\s+"); //Separates each line by whitespaces
                String name = parts[0];
                int lifelineCount = Integer.parseInt(parts[1]);
                String difficulty = parts[2];

                //Adds player to the ArrayList
                millionaires.add(new Player(name, 1000000, lifelineCount, difficulty));
            }
            loadMillionaireslog.write("Successfully loaded millionaires data\n");
        } catch (Exception e) {
            System.err.println("Failed to load millionaires data: " + e.getMessage());
            loadMillionaireslog.write("Failed to load millionaires data: " + e.getMessage() + "\n");
        }
        return millionaires;
    }

    //Same as loadMillionaires()
    private static List<Player> loadNearMillionaires() {
        Logger loadNearMillionaireslog = new Logger();
        List<Player> nearMillionaires = new ArrayList<>();

        try {
            String data = FileInputOutput.read("near_millionaires.txt");
            String[] lines = data.split("\\r?\\n");

            for (String line : lines) {
                String[] parts = line.split("\\s+");
                int winnings = Integer.parseInt(parts[0]);
                String name = parts[1];
                int lifelineCount = Integer.parseInt(parts[2]);
                String difficulty = parts[3];

                nearMillionaires.add(new Player(name, winnings, lifelineCount, difficulty));
            }
            loadNearMillionaireslog.write("Successfully loaded near millionaires data.\n");
        } catch (Exception e) {
            System.err.println("Failed to load near millionaires data: " + e.getMessage());
            loadNearMillionaireslog.write("Failed to load near millionaires data: " + e.getMessage() + "\n");
        }

        return nearMillionaires;
    }
}
