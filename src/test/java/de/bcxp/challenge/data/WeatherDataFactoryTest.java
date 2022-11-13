package de.bcxp.challenge.data;

import de.bcxp.challenge.model.WeatherData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherDataFactoryTest {
  WeatherDataFactory factory;

  @BeforeEach
  void setUp(){
    factory = new WeatherDataFactory();
  }

  @Test
  void getDataObjectsFromTest(){
    int day = 1;
    int minTemp = 0;
    int maxTemp = 1;
    WeatherData expected = new WeatherData(day, minTemp, maxTemp);
    List<Map<String, String>> mockData = new ArrayList<>();
    Map<String, String> row1 = new HashMap<>();
    row1.put(WeatherDataFactory.DAY_IDENTIFIER, Integer.toString(day));
    row1.put(WeatherDataFactory.MINIMUM_TEMP_IDENTIFIER, Integer.toString(minTemp));
    row1.put(WeatherDataFactory.MAXIMUM_TEMP_IDENTIFIER, Integer.toString(maxTemp));
    mockData.add(row1);
    AttributeListProvider provider = new TestAttributeListProvider(mockData);
    List<WeatherData> dataList = factory.getDataObjectsFrom(provider);
    assertEquals(mockData.size(), dataList.size());
    WeatherData wData = dataList.get(0);
    assertEquals(expected, wData);
  }

  @Test
  void getDataObjectsFromEmpty(){
    List<Map<String, String>> mockData = new ArrayList<>();
    AttributeListProvider provider = new TestAttributeListProvider(mockData);
    List<WeatherData> dataList = factory.getDataObjectsFrom(provider);
    assertEquals(0, dataList.size());
  }
}
