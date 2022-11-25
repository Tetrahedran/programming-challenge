package de.bcxp.challenge.model;

import java.util.Objects;

public class CountryData {
  private final String name;
  private final long population;
  private final double area;

  public CountryData(String name, long population, double area){
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CountryData that = (CountryData) o;
    return population == that.population && Double.compare(that.area, area) == 0 && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, population, area);
  }
}
