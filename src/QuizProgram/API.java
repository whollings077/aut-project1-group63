package QuizProgram;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.commons.text.StringEscapeUtils;

/**
 *
 * @author Matthew Warn
 */
public class API {

    private String difficulty;

    public static List<Question> fetchQuestions(String difficulty) {
        List<Question> parsedQuestions = new ArrayList<>();
        String response = "";

        try {
            //Taking the user's chosen difficulty and getting the API URL for that difficulty
            String encodedDifficulty = URLEncoder.encode(difficulty, "UTF-8");
            URL url = new URL("https://opentdb.com/api.php?amount=10&difficulty=" + encodedDifficulty);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            //Reading the data from the API
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            //Adding the data from the API to a string line by line
            String line;

            while ((line = reader.readLine()) != null) {
                response += line;
            }

            reader.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Parsing the response from the API
        try {
            //Turning the string into a JSONArray
            JSONParser parser = new JSONParser();
            JSONObject responseObject = (JSONObject) parser.parse(response);
            JSONArray parsedArray = (JSONArray) responseObject.get("results");

            //Looping through the array and turning each item into a java object
            for (Object obj : parsedArray) {
                JSONObject result = (JSONObject) obj;

                //Retrieving all of the info needed for each question
                String type = (String) result.get("type");
                String category = (String) result.get("category");
                String question = StringEscapeUtils.unescapeHtml4((String) result.get("question")); //StringEscapeUtils fixes a bug where symbols would show up in HTML form
                String correct_answer = StringEscapeUtils.unescapeHtml4((String) result.get("correct_answer"));
                JSONArray incorrect_answersJsonArray = (JSONArray) result.get("incorrect_answers");
                List<String> incorrect_answers = new ArrayList<>();

                //Turning the JSONArray of incorrect answers into a regular ArrayList
                for (Object answer : incorrect_answersJsonArray) {
                    incorrect_answers.add(StringEscapeUtils.unescapeHtml4((String) answer));
                }

                //Creating a Question object with all the data that was just retrieved from the API
                Question q = new Question(type, difficulty, category, question, correct_answer, incorrect_answers);
                parsedQuestions.add(q);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Returning the final list with all of the questions as Question objects.
        return parsedQuestions;
    }
}
