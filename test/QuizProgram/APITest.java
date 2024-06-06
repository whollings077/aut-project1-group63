/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package QuizProgram;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author wholl
 */
public class APITest {
    
    public APITest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of fetchQuestions method, of class API.
     */
    @Test
    public void testFetchQuestions() {
        System.out.println("fetchQuestions");
        String difficulty = "easy";  // Setting a valid difficulty level
        List<Question> result = API.fetchQuestions(difficulty);
        
        // Check that the result is not null
        assertNotNull("Result should not be null", result);
        
        // Check that the result contains the expected number of questions
        assertEquals("Result should contain 10 questions", 10, result.size());
        
        // check that each question in the result has the expected difficulty, this should never really fail but its an interesting test anyway
        for (Question q : result) {
            assertEquals("Question difficulty should be " + difficulty, difficulty, q.getDifficulty());
        }
    }
}
