package de.bcxp.challenge.model;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    WeatherData that = (WeatherData) o;
    return day == that.day && Float.compare(that.minimumTemperature, minimumTemperature) == 0 &&
      Float.compare(that.maximumTemperature, maximumTemperature) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(day, minimumTemperature, maximumTemperature);
  }

  @Override
  public String toString() {
    return "WeatherData{" +
      "day=" + day +
      ", minimumTemperature=" + minimumTemperature +
      ", maximumTemperature=" + maximumTemperature +
      '}';
  }
}
