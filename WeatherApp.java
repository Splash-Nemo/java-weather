//For Backend Logic
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherApp {
    public static JSONObject getWeatherData(String locationName){
        JSONArray locationData= getLocationData(locationName);

        return null;
    }

    public static JSONArray getLocationData(String locationName){
        //Replace whitespace with '+' for API format
        locationName= locationName.replaceAll(" ", "+");

        //Create API-URL
        String urlString= "https://geocoding-api.open-meteo.com/v1/search?name="+locationName+"&count=10&language=en&format=json";

        try{
            //call API and get a response
            HttpURLConnection conn = fetchApiResponse(urlString);

            //200- successful
            //400- Bad Request
            //500- Internal server error

            if(conn.getResponseCode()!=200){
                System.out.println("Error connecting to API");
                return null;
            }else{
                StringBuilder resultJSON= new StringBuilder();

                Scanner sc= new Scanner(conn.getInputStream());

                while(sc.hasNext()){
                    resultJSON.append(sc.nextLine());
                }

                sc.close();
                conn.disconnect();

                JSONParser parser= new JSONParser();
                JSONObject resultJSONObject= (JSONObject) parser.parse(String.valueOf(resultJSON));

                JSONArray locationData= (JSONArray) resultJSONObject.get("results");

                return locationData;
             }

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private static HttpsURLConnection fetchApiResponse(String urlString){
        try{
            URL url= new URL(urlString);
            HttpsURLConnection conn= (HttpsURLConnection) url.openConnection();

            //setRequest method
            conn.setRequestMethod("GET");
            conn.connect();
            return conn;
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
}
