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

    private static final String DB_URL = "jdbc:derby://localhost:1527/QuizDB;create=true";
    private static final String USER = "app";
    private static final String PASS = "app";

    static {
        // setup the tables if they dont exist
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            String createMillionairesTable = "CREATE TABLE Millionaires ("
                    + "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "Name VARCHAR(255),"
                    + "LifelineCount INT,"
                    + "Difficulty VARCHAR(255))";

            String createNearMillionairesTable = "CREATE TABLE NearMillionaires ("
                    + "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "Winnings INT,"
                    + "Name VARCHAR(255),"
                    + "LifelineCount INT,"
                    + "Difficulty VARCHAR(255))";

            stmt.executeUpdate(createMillionairesTable);
            stmt.executeUpdate(createNearMillionairesTable);
        } catch (SQLException e) {
            // exceptions like table already existing
        }
    }

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

    public static void addToLeaderboard(int winnings) {
        Logger addToLeaderboardlog = new Logger();
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter your name:");
        String playerName = scanner.nextLine();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            if (winnings == 1000000) {
                String sql = "INSERT INTO Millionaires (Name, LifelineCount, Difficulty) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, playerName);
                    pstmt.setInt(2, CLI.lifelineCount);
                    pstmt.setString(3, CLI.leaderboardDiff);
                    pstmt.executeUpdate();
                    addToLeaderboardlog.write("Successfully added " + playerName + " to Millionaires\n");
                }
            } else {
                String sql = "INSERT INTO NearMillionaires (Winnings, Name, LifelineCount, Difficulty) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, winnings);
                    pstmt.setString(2, playerName);
                    pstmt.setInt(3, CLI.lifelineCount);
                    pstmt.setString(4, CLI.leaderboardDiff);
                    pstmt.executeUpdate();
                    addToLeaderboardlog.write("Successfully added " + playerName + " with winnings of " + winnings + " to NearMillionaires\n");
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to update leaderboard. " + e.getMessage());
            addToLeaderboardlog.write("Failed to update leaderboard for " + playerName + " with exception: " + e.getMessage() + "\n");
        }

        System.out.println("\n");
        Main.runGame();
    }

    public static void displayLeaderBoard() {
        Logger displayLeaderBoardlog = new Logger();
        Scanner scanner = new Scanner(System.in);

        List<Player> millionaires = loadMillionaires();
        List<Player> nearMillionaires = loadNearMillionaires();

        Collections.sort(nearMillionaires, Comparator.comparing(Player::getWinnings).reversed());
        displayLeaderBoardlog.write("Displaying Leaderboards\n");

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
                        Main.runGame();
                        break;
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

    private static List<Player> loadMillionaires() {
        Logger loadMillionaireslog = new Logger();
        List<Player> millionaires = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Millionaires")) {

            while (rs.next()) {
                String name = rs.getString("Name");
                int lifelineCount = rs.getInt("LifelineCount");
                String difficulty = rs.getString("Difficulty");
                millionaires.add(new Player(name, 1000000, lifelineCount, difficulty));
            }
            loadMillionaireslog.write("Successfully loaded millionaires data\n");
        } catch (SQLException e) {
            System.err.println("Failed to load millionaires data: " + e.getMessage());
            loadMillionaireslog.write("Failed to load millionaires data: " + e.getMessage() + "\n");
        }

        return millionaires;
    }

    private static List<Player> loadNearMillionaires() {
        Logger loadNearMillionaireslog = new Logger();
        List<Player> nearMillionaires = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM NearMillionaires")) {

            while (rs.next()) {
                int winnings = rs.getInt("Winnings");
                String name = rs.getString("Name");
                int lifelineCount = rs.getInt("LifelineCount");
                String difficulty = rs.getString("Difficulty");
                nearMillionaires.add(new Player(name, winnings, lifelineCount, difficulty));
            }
            loadNearMillionaireslog.write("Successfully loaded near millionaires data\n");
        } catch (SQLException e) {
            System.err.println("Failed to load near millionaires data: " + e.getMessage());
            loadNearMillionaireslog.write("Failed to load near millionaires data: " + e.getMessage() + "\n");
        }

        return nearMillionaires;
    }
    
    public static List<Object[]> getMillionairesDataForGUI() { // Gets millionaires from the database for the gui to use
        List<Object[]> data = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT Name, LifelineCount, Difficulty FROM Millionaires")) {

            while (rs.next()) {
                Object[] row = {rs.getString("Name"), rs.getInt("LifelineCount"), rs.getString("Difficulty")};
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

}