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
import java.io.*;
import java.nio.file.*;

/**
 *
 * @author wholl
 */
public class LoggerTest {

    private static final String LOG_FILE = "log.txt";

    @BeforeClass
    public static void setUpClass() {
        // Clean up log file before all tests
        try {
            Files.deleteIfExists(Paths.get(LOG_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDownClass() {
        // Clean up log file after all tests
        try {
            Files.deleteIfExists(Paths.get(LOG_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        // Clean up log file before each test
        try {
            Files.deleteIfExists(Paths.get(LOG_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        // Clean up log file after each test
        try {
            Files.deleteIfExists(Paths.get(LOG_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test of write method, of class Logger.
     */
    @Test
    public void testWrite() {
        Logger logger = new Logger();
        String message1 = "This is the first log message.";
        String message2 = "This is the second log message.";

        logger.write(message1);
        logger.write(message2);

        String expectedContent = message1 + System.lineSeparator() + message2 + System.lineSeparator();
        String actualContent = FileInputOutput.read(LOG_FILE);

        assertEquals(expectedContent, actualContent);
    }

}
