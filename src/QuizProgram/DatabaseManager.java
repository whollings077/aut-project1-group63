/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuizProgram;

/**
 *
 * @author wholl
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.derby.drda.NetworkServerControl;

public class DatabaseManager {
    

    private static final String DB_URL = "jdbc:derby:QuizDB;create=true";
    private static final String USER = "app";
    private static final String PASS = "app";
    private NetworkServerControl server;
    
    public DatabaseManager() {
        setupTables();
    }
   

    private void setupTables() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            // creates Millionaires table if it does not exist
            String createMillionairesTable = "CREATE TABLE Millionaires ("
                    + "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "Name VARCHAR(255),"
                    + "LifelineCount INT,"
                    + "Difficulty VARCHAR(255))";

            try {
                stmt.executeUpdate(createMillionairesTable);
            } catch (SQLException e) {
                if (!"X0Y32".equals(e.getSQLState())) { // X0Y32: Table already exists
                    e.printStackTrace();
                }
            }

            // create NearMillionaires table if it does not exist
            String createNearMillionairesTable = "CREATE TABLE NearMillionaires ("
                    + "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "Winnings INT,"
                    + "Name VARCHAR(255),"
                    + "LifelineCount INT,"
                    + "Difficulty VARCHAR(255))";

            try {
                stmt.executeUpdate(createNearMillionairesTable);
            } catch (SQLException e) {
                if (!"X0Y32".equals(e.getSQLState())) { // X0Y32: Table already exists
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMillionaire(String name, int lifelineCount, String difficulty) {
        String sql = "INSERT INTO Millionaires (Name, LifelineCount, Difficulty) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, lifelineCount);
            pstmt.setString(3, difficulty);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNearMillionaire(int winnings, String name, int lifelineCount, String difficulty) {
        String sql = "INSERT INTO NearMillionaires (Winnings, Name, LifelineCount, Difficulty) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, winnings);
            pstmt.setString(2, name);
            pstmt.setInt(3, lifelineCount);
            pstmt.setString(4, difficulty);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> getMillionaires() {
        List<Object[]> data = new ArrayList<>();
        String sql = "SELECT Name, LifelineCount, Difficulty FROM Millionaires";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Object[] row = {rs.getString("Name"), rs.getInt("LifelineCount"), rs.getString("Difficulty")};
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public List<Object[]> getNearMillionaires() {
        List<Object[]> data = new ArrayList<>();
        String sql = "SELECT Winnings, Name, LifelineCount, Difficulty FROM NearMillionaires";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Object[] row = {rs.getInt("Winnings"), rs.getString("Name"), rs.getInt("LifelineCount"), rs.getString("Difficulty")};
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
        
    public void shutdownDerbyServer() {
        if (server != null) {
            try {
                server.shutdown();
                System.out.println("Derby server shut down.");
            } catch (Exception e) {
                System.err.println("Error shutting down Derby server: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
