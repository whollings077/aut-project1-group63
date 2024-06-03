/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuizProgram;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author william
 */
public class Leaderboard { //this code is really scuffed because i wrote it to work without needing to change any of the other classes

    private static final DatabaseManager dbManager = new DatabaseManager();

    public static void leaderboardPrompt(int winnings) {
        Logger leaderboardPromptLog = new Logger();
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWould you like to add your name and score to the leaderboard?\n1. Yes\n2. No");

        while (true) {
            try {
                int leadChoice = scanner.nextInt();

                switch (leadChoice) {
                    case 1: // Yes
                        leaderboardPromptLog.write("Leaderboard Choice Yes\n");
                        addToLeaderboard(winnings);
                        break;
                    case 2: // No
                        System.out.println("\n");
                        leaderboardPromptLog.write("Leaderboard Choice No Running Game\n");
                        Main.runGame();
                        break;
                    case 0: // Exit
                        Main.exit();
                        break;
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

    public static void addToLeaderboard(int winnings) { //now uses database manager class, this code is for the cli version of the game
        Logger addToLeaderboardlog = new Logger();
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter your name:");
        String playerName = scanner.nextLine();

        if (winnings == 1000000) {
            dbManager.addMillionaire(playerName, CLI.lifelineCount, CLI.leaderboardDiff);
            addToLeaderboardlog.write("Successfully added " + playerName + " to Millionaires\n");
        } else {
            dbManager.addNearMillionaire(winnings, playerName, CLI.lifelineCount, CLI.leaderboardDiff);
            addToLeaderboardlog.write("Successfully added " + playerName + " with winnings of " + winnings + " to NearMillionaires\n");
        }

        System.out.println("\n");
        Main.runGame();
    }

    public static void displayLeaderBoard() {
        Logger displayLeaderBoardlog = new Logger();
        Scanner scanner = new Scanner(System.in);

        DatabaseManager dbManager = new DatabaseManager();

        List<Object[]> millionaires = dbManager.getMillionaires();
        List<Object[]> nearMillionaires = dbManager.getNearMillionaires();

        nearMillionaires.sort(Comparator.comparing(o -> (Integer) o[0], Comparator.reverseOrder()));
        displayLeaderBoardlog.write("Displaying Leaderboards\n");

        System.out.println("\n~~~~~~~LIST OF MILLIONAIRES~~~~~~~");
        for (Object[] player : millionaires) {
            System.out.println("-~=" + player[0] + "=~- | Difficulty: " + player[2] + " | Lifelines Used: " + player[1]);
        }

        System.out.println("\nList of Near Millionaires:");
        for (Object[] player : nearMillionaires) {
            System.out.println(player[1] + " | Winnings: $" + player[0] + " | Difficulty: " + player[3] + " | Lifelines Used: " + player[2]);
        }

        System.out.println("\nEnter 0 to exit to main menu.");

        while (true) {
            try {
                int exitChoice = scanner.nextInt();

                switch (exitChoice) {
                    case 0:
                        displayLeaderBoardlog.write("User exited to main menu\n");
                        Main.runGame();
                        return;
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

}
