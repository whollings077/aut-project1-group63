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
public class FileInputOutputTest {
    
    private static final String TEST_FILE = "testfile.txt";

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
        // delete test file after all tests
        try {
            Files.deleteIfExists(Paths.get(TEST_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        // delete test file before each test
        try {
            Files.deleteIfExists(Paths.get(TEST_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        // delete test file after each test
        try {
            Files.deleteIfExists(Paths.get(TEST_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Test of append method, of class FileInputOutput.
     */
    @Test
    public void testAppend() {
        String text1 = "Hello, world!";
        String text2 = "some appending text";

        FileInputOutput.append(text1, TEST_FILE);
        FileInputOutput.append(text2, TEST_FILE);

        String expectedContent = text1 + System.lineSeparator() + text2 + System.lineSeparator();
        String actualContent = FileInputOutput.read(TEST_FILE);

        assertEquals(expectedContent, actualContent);
    }

    /**
     * Test of overwrite method, of class FileInputOutput.
     */
    @Test
    public void testOverwrite() {
        String initialText = "Initial text.";
        String newText = "New text.";

        FileInputOutput.append(initialText, TEST_FILE);
        FileInputOutput.overwrite(newText, TEST_FILE);

        String actualContent = FileInputOutput.read(TEST_FILE);

        assertEquals(newText, actualContent);
    }

    /**
     * Test of read method, of class FileInputOutput.
     */
    @Test
    public void testRead() {
        String text = "Reading text.";

        FileInputOutput.overwrite(text, TEST_FILE);
        String actualContent = FileInputOutput.read(TEST_FILE);

        assertEquals(text, actualContent);
    }

    
}
