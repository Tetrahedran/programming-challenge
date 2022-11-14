package de.bcxp.challenge.model;

public class CountryData {
  private final String name;
  private final int population;
  private final double area;

  public CountryData(String name, int population, double area){
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

  public double getPopulationDensity(){
    return population / area;
  }
}
