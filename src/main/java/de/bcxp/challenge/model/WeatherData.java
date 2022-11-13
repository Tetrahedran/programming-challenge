package de.bcxp.challenge.model;

public class WeatherData {
  private final int day;
  private final float minimumTemperature;
  private final float maximumTemperature;

  public WeatherData(int day, float minimumTemperature, float maximumTemperature){
    this.day = day;
    if(minimumTemperature > maximumTemperature){
      throw new IllegalArgumentException("The minimum temperature " + minimumTemperature + " is higher than the maximum" +
        "temperature " + maximumTemperature);
    }
    this.minimumTemperature = minimumTemperature;
    this.maximumTemperature = maximumTemperature;
  }

  public int getDay(){
    return day;
  }

  public float getTemperatureSpread(){
    return maximumTemperature - minimumTemperature;
  }
}
