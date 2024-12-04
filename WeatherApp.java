//For Backend Logic
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class WeatherApp {
    public static JSONObject getWeatherData(String locationName){
        JSONArray locationData= getLocationData(locationName);

        //Latitude and Longitude
        JSONObject location= (JSONObject) locationData.get(0);

        double latitude= (double) location.get("latitude");
        double longitude= (double) location.get("longitude");

        //Build URL using Location
        String urlString = "https://api.open-meteo.com/v1/forecast?"+"latitude=" + latitude+"&longitude="+longitude +"&hourly=temperature_2m,relativehumidity_2m,weather_code,windspeed_10m&timezone=America%2FLos_Angeles";

        try{
            HttpURLConnection conn= fetchApiResponse(urlString);

            assert conn != null;
            if(conn.getResponseCode()!=200){
                System.out.println("Error connecting");
                return null;
            }

            StringBuilder resultJSON= new StringBuilder();
            Scanner sc= new Scanner(conn.getInputStream());

            while(sc.hasNext()){
                resultJSON.append(sc.nextLine());
            }

            sc.close();
            conn.disconnect();

            JSONParser parser= new JSONParser();
            JSONObject resultJSONObj= (JSONObject) parser.parse(String.valueOf(resultJSON));

            JSONObject hourly= (JSONObject) resultJSONObj.get("hourly");

            JSONArray time= (JSONArray) hourly.get("time");

            int index= findIndex(time);

            JSONArray temperatureData= (JSONArray) hourly.get("temperature_2m");
            double temperature= (double) temperatureData.get(index);

            JSONArray weatherCode=(JSONArray) hourly.get("weather_code");
            String weatherCondition= convertWeatherCode((long) weatherCode.get(index));

            JSONArray relativeHumidity= (JSONArray) hourly.get("relativehumidity_2m");
            long humidity= (long) relativeHumidity.get(index);

            JSONArray windspeedData= (JSONArray) hourly.get("windspeed_10m");
            double windspeed= (double) windspeedData.get(index);

            JSONObject weatherData= new JSONObject();
            weatherData.put("temperature", temperature);
            weatherData.put("weather_condition",weatherCondition);
            weatherData.put("humidity",humidity);
            weatherData.put("windspeed", windspeed);

            return weatherData;

        }catch (Exception e){
            e.printStackTrace();
        }

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


    private static int findIndex(JSONArray timeList){
        String currentTime= getCurrentTime();
        for(int i=0; i<timeList.size(); i++){
            String time= (String) timeList.get(i);
            if(time.equalsIgnoreCase(currentTime)){
                return i;
            }
        }
        return 0;
    }

    public static String getCurrentTime(){
        LocalDateTime currentDatetime= LocalDateTime.now();
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");

        String formattedDateTime = currentDatetime.format(formatter);

        return formattedDateTime;
    }

    private static String convertWeatherCode(long weatherCode){
        String weatherCondition= "";
        if(weatherCode==0L){
            weatherCondition= "Clear";
        }else if(weatherCode<=3L && weatherCode>0L){
            weatherCondition= "Cloudy";
        }else if((weatherCode>=51 && weatherCode<=67L)||(weatherCode>=80L && weatherCode<=99L)){
            weatherCondition= "Rain";
        } else if (weatherCode>=71L && weatherCode<=77L) {
            weatherCondition= "Snow";
        }

        return weatherCondition;
    }
}
