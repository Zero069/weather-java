import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Weather{
    final String weater_apiKey = "";

    public void run() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a city and i'll provide the weather details");
        String string = scanner.nextLine();
        String response = sendWeatherRequest(string);
        parseResponse(response);
    }

    private void parseResponse(String response) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response);
        JSONObject weather = (JSONObject) ((JSONArray) json.get("weather")).get(0);
        JSONObject data = (JSONObject) json.get("main");
        System.out.println(weather.get("main"));
        System.out.println(data.get("temp")+ "C");
    }

    private String sendWeatherRequest(String userInput){
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+ userInput + "&mode=json&units=metric&lang=en&appid=" +this.weater_apiKey);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent","Mozilla/5.0");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while((inputLine = in.readLine())!= null){
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            }
            else{
                System.out.println("GET request not found");
            }
        }
        catch (MalformedURLException e){
            System.out.println("Invalid URL");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return "";
    }

}
