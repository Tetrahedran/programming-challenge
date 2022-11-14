package de.bcxp.challenge.model;

public class CountryData {
  private final String name;
  private final int population;
  private final int area;

  public CountryData(String name, int population, int area){
    this.name = name;
    this.population = population;
    if(area == 0){
      throw new IllegalArgumentException("Area of a country can not be 0");
    }
    this.area = area;
  }

  public String getName(){
    return name;
  }

  public float getPopulationDensity(){
    return (float)population / area;
  }
}
