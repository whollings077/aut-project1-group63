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

public class API {
	
	private String difficulty;
	
	public static List<Question> fetchQuestions(String difficulty){
		List<Question> questions = new ArrayList<>();
		
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
			String response = "";
			String line;
			
			while ((line = reader.readLine()) != null) {
			    response += line;
			}
			
			reader.close();

			//TEST - Just used this to show that the code works :)
			System.out.println(response);
			
			
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}

