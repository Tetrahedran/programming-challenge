package de.bcxp.challenge.data;

import de.bcxp.challenge.model.CountryData;
import de.bcxp.challenge.model.WeatherData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountryDataFactoryTest {
  CountryDataFactory factory;

  @BeforeEach
  void setUp(){
    factory = new CountryDataFactory();
  }

  /**
   * Tests normal data creation from attribute list
   */
  @Test
  void getDataObjectFromTest(){
    String name = "Test";
    int population = 0;
    double area = 1;
    CountryData expected = new CountryData(name, population, area);
    List<Map<String, String>> mockData = new ArrayList<>();
    Map<String, String> row1 = new HashMap<>();
    row1.put(CountryDataFactory.NAME_IDENTIFIER, name);
    row1.put(CountryDataFactory.POPULATION_IDENTIFIER, Integer.toString(population));
    row1.put(CountryDataFactory.AREA_IDENTIFIER, Double.toString(area));
    mockData.add(row1);
    AttributeListProvider provider = new TestAttributeListProvider(mockData);
    List<CountryData> dataList = factory.getDataObjectsFrom(provider);
    assertEquals(mockData.size(), dataList.size());
    CountryData cData = dataList.get(0);
    assertEquals(expected, cData);
  }

  /**
   * Tests data object creation from empty {@link AttributeListProvider}
   */
  @Test
  void getDataObjectsFromEmpty(){
    List<Map<String, String>> mockData = new ArrayList<>();
    AttributeListProvider provider = new TestAttributeListProvider(mockData);
    List<CountryData> dataList = factory.getDataObjectsFrom(provider);
    assertEquals(0, dataList.size());
  }
}
