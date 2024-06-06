/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package QuizProgram;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;
import java.util.List;

/**
 *
 * @author wholl
 */
public class DatabaseManagerTest {

    private DatabaseManager dbManager;

    public DatabaseManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        dbManager = new DatabaseManager();
    }

    @After
    public void tearDown() {
        // Clean up test records after each test
        try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/QuizDB;create=true", "app", "app"); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM Millionaires WHERE Name IN ('John Doe', 'Alice Brown')");
            stmt.executeUpdate("DELETE FROM NearMillionaires WHERE Name IN ('Jane Smith', 'Bob White')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test of addMillionaire method, of class DatabaseManager.
     */
    @Test
    public void testAddMillionaire() {
        String name = "John Doe";
        int lifelineCount = 3;
        String difficulty = "easy";

        dbManager.addMillionaire(name, lifelineCount, difficulty);
        List<Object[]> millionaires = dbManager.getMillionaires();

        assertNotNull(millionaires);
        assertTrue(millionaires.stream().anyMatch(m -> m[0].equals(name) && m[1].equals(lifelineCount) && m[2].equals(difficulty))); // this line was written by ChatGPT, I dont entirely understand it but it seems to work
    }

    /**
     * Test of addNearMillionaire method, of class DatabaseManager.
     */
    @Test
    public void testAddNearMillionaire() {
        int winnings = 500000;
        String name = "Jane Smith";
        int lifelineCount = 2;
        String difficulty = "medium";

        dbManager.addNearMillionaire(winnings, name, lifelineCount, difficulty);
        List<Object[]> nearMillionaires = dbManager.getNearMillionaires();

        assertNotNull(nearMillionaires);
        assertTrue(nearMillionaires.stream().anyMatch(nm -> nm[0].equals(winnings) && nm[1].equals(name) && nm[2].equals(lifelineCount) && nm[3].equals(difficulty))); // this line was written by ChatGPT
    }

    /**
     * Test of getMillionaires method, of class DatabaseManager.
     */
    @Test
    public void testGetMillionaires() {
        dbManager.addMillionaire("John Doe", 3, "easy");
        dbManager.addMillionaire("Alice Brown", 1, "hard");

        List<Object[]> millionaires = dbManager.getMillionaires();

        assertNotNull(millionaires);
        assertTrue(millionaires.stream().anyMatch(m -> m[0].equals("John Doe")));
        assertTrue(millionaires.stream().anyMatch(m -> m[0].equals("Alice Brown")));
    }

    /**
     * Test of getNearMillionaires method, of class DatabaseManager.
     */
    @Test
    public void testGetNearMillionaires() {
        dbManager.addNearMillionaire(500000, "Jane Smith", 2, "medium");
        dbManager.addNearMillionaire(750000, "Bob White", 3, "hard");

        List<Object[]> nearMillionaires = dbManager.getNearMillionaires();

        assertNotNull(nearMillionaires);
        assertTrue(nearMillionaires.stream().anyMatch(nm -> nm[1].equals("Jane Smith")));
        assertTrue(nearMillionaires.stream().anyMatch(nm -> nm[1].equals("Bob White")));
    }

}
