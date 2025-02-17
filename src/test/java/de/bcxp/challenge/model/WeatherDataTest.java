package de.bcxp.challenge.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WeatherDataTest {

  @Test
  void positiveWeatherDataTest(){
    int day = 1;
    int minTemp = 10;
    int maxTemp = 20;
    WeatherData data = new WeatherData(day, minTemp, maxTemp);
    assertEquals(day, data.getDay());
    assertEquals(maxTemp - minTemp, data.getTemperatureSpread(),0.0001f);
  }

  @Test
  void positiveWeatherDataTestFloat(){
    int day = 1;
    float minTemp = 10.5f;
    float maxTemp = 20.7f;
    WeatherData data = new WeatherData(day, minTemp, maxTemp);
    assertEquals(maxTemp - minTemp, data.getTemperatureSpread(),0.0001f);
  }

  @Test
  void edgeCaseWeatherDataTest(){
    int day = 1;
    float minTemp = 0;
    float maxTemp = 0;
    WeatherData data = new WeatherData(day, minTemp, maxTemp);
    assertEquals(0.0, data.getTemperatureSpread(),0.00001f);
  }

  /**
   * Tests correct error when creating {@link WeatherData} but min temperature is above max temperature
   */
  @Test
  void errorCaseWeatherDataTest(){
    int day = 1;
    float minTemp = 20.5f;
    float maxTemp = 10.7f;
    assertThrows(IllegalArgumentException.class, () -> new WeatherData(day, minTemp, maxTemp));
  }
}
