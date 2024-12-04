import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGUI extends JFrame{

    private JSONObject weatherData;

    public WeatherAppGUI(){
        super("Weather-App");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 650);

        setLocationRelativeTo(null);
        setLayout(null);

        setResizable(false);

        addGUIComponents();
    }

    private void addGUIComponents(){
        //search field
        JTextField searchTextField= new JTextField();
        searchTextField.setBounds(15,25,350,30);

        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 20));

        add(searchTextField);

         //Weather Image
        JLabel weatherConditionImage= new JLabel(loadImage("imgs/cloudy.png"));

        weatherConditionImage.setBounds(0, 125, 450, 217);

        add(weatherConditionImage);

        //temperature text
        JLabel temperatureText= new JLabel("10 C");
        temperatureText.setBounds(0, 350,450, 54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));

        //center-align
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER
        );

        add(temperatureText);

        //Weather Description
        JLabel weatherConditionDescription= new JLabel("Cloudy");
        weatherConditionDescription.setBounds(0, 420, 450, 44);

        weatherConditionDescription.setFont(new Font("Dialog", Font.BOLD, 20));

        weatherConditionDescription.setHorizontalAlignment(SwingConstants.CENTER);

        add(weatherConditionDescription);

        //humidity image
        JLabel humidityImage= new JLabel(loadImage("imgs/humidity.png"));
        humidityImage.setBounds(15,500,74,66);
        add(humidityImage);

        //humidity text
        JLabel humidityText= new JLabel("<html><b>Humidity</b>\n100%</html>");
        humidityText.setBounds(90,500,85,55);
        humidityText.setFont(new Font("Dialog",Font.PLAIN, 14));
        add(humidityText);

        //windspeed image
        JLabel windspeedImage= new JLabel(loadImage("imgs/windspeed.png"));
        windspeedImage.setBounds(220,500,74,66);
        add(windspeedImage);

        //windspeed text
        JLabel windspeedText= new JLabel("<html><b>Wind-Speed</b>\n10km/h</html>");
        windspeedText.setBounds(310, 500, 85, 55);
        windspeedText.setFont(new Font("Dialog", Font.PLAIN, 14));
        add(windspeedText);

        //search button
        JButton searchButton= new JButton(loadImage("imgs/search.png"));

        //cursor appearance
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 25, 47, 30);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput= searchTextField.getText();

                if(userInput.replaceAll("\\s", "").length()<=0){
                    return;
                }

                //Retrieve weather data
                weatherData= WeatherApp.getWeatherData(userInput);

                String weatherCondition= (String) weatherData.get("weather_condition");

                //Update weather image
                switch(weatherCondition){
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("imgs/clear.png"));
                        break;

                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("imgs/cloudy.png"));
                        break;

                    case "Humidity":
                        weatherConditionImage.setIcon(loadImage("imgs/humidity.png"));
                        break;

                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("imgs/rain.png"));
                        break;

                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("imgs/snow.png"));
                        break;
                }

                double temperature= (double) weatherData.get("temperature");
                temperatureText.setText(temperature+" C");

                weatherConditionDescription.setText(weatherCondition);

                long humidity= (long) weatherData.get("humidity");
                humidityText.setText(("<html><b>Humidity</b> "+humidity+"%</html>"));

                double windspeed= (double) weatherData.get("windspeed");
                windspeedText.setText("<html><b>Wind-Speed</b> "+windspeed+"km/h</html>");


            }
        });

        add(searchButton);
    }

    // Images for GUI
    private ImageIcon loadImage(String resourcePath){
        try{
            BufferedImage image= ImageIO.read(new File(resourcePath));

            return new ImageIcon(image);
        }catch(IOException e){
            e.printStackTrace();
        }

        System.out.println("Could not find image path");
        return null;
    }
}
