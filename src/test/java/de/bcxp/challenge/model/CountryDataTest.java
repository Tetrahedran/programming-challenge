package de.bcxp.challenge.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CountryDataTest {
  @Test
  void createCountryDataTest(){
    String name = "Test";
    int population = 1;
    double area = 2;
    CountryData data = new CountryData(name, population, area);
    assertEquals(name, data.getName());
    assertEquals(population / area, data.getPopulationDensity(), 0.0001);
  }

  @Test
  void zeroAreaTest(){
    assertThrows(IllegalArgumentException.class, () -> new CountryData("", 0, 0));
  }
}
