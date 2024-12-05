# WeatherApp

## Demo


https://github.com/user-attachments/assets/0bde59c5-f89c-4d0b-ad55-667ba07fb4dc


## Description

WeatherApp is a desktop application built in Java that provides weather information for a specific location. It uses the Open-Meteo API to fetch real-time weather data, including temperature, weather conditions, humidity, and wind speed.
Features

    User-Friendly GUI: Built with Swing for a smooth user experience.
    Real-Time Weather Data: Get live updates on temperature, weather conditions, humidity, and wind speed.
    Visual Indicators: Icons representing weather conditions.
    Location Search: Retrieve weather information for any location worldwide.

## Requirements

    Java 8 or higher.
    Dependencies:
        json-simple for handling JSON.
        javax.swing for GUI components.

## Usage

    Launch the Application: Run the above commands, and the GUI will open.

    Search for a Location:
        Enter the name of a location in the search field.
        Click the search button to fetch weather data.

    View Weather Details:
        Current temperature.
        Weather condition (Clear, Cloudy, Rain, Snow).
        Humidity percentage.
        Wind speed.

## Project Structure

    AppLauncher.java: Launches the application and initializes the GUI.
    WeatherApp.java: Handles backend logic, including API calls and data processing.
    WeatherAppGUI.java: Defines the graphical user interface and updates weather information.
