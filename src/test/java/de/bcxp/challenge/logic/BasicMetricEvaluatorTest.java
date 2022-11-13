package de.bcxp.challenge.logic;

import de.bcxp.challenge.model.WeatherData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasicMetricEvaluatorTest {

  @Test
  void getIdentifierForMinSingleItemTest(){
    int day = 1;
    WeatherData data = new WeatherData(day, 10, 20);
    List<WeatherData> dataList = List.of(data);
    String result = BasicMetricEvaluator.getIdentifierForMinimumValue(
      dataList, (dat) -> Integer.toString(dat.getDay()), WeatherData::getTemperatureSpread);
    assertEquals(Integer.toString(day), result);
  }

  @Test
  void getIdentifierForMinMultipleItemTest(){
    int day1 = 1;
    int day2 = 2;
    int winnerDay = 3;

    WeatherData dat1 = new WeatherData(day1, 0,1);
    WeatherData dat2 = new WeatherData(day2,0,1);
    WeatherData winnerDat = new WeatherData(winnerDay,0,0);

    List<WeatherData> dataList = Arrays.asList(dat1, dat2, winnerDat);

    String result = BasicMetricEvaluator.getIdentifierForMinimumValue(dataList,
      dat -> Integer.toString(dat.getDay()), WeatherData::getTemperatureSpread);

    assertEquals(Integer.toString(winnerDay), result);
  }

  @Test
  void getIdentifierForMinEmptyList(){
    List<WeatherData> dataList = new ArrayList<>();
    assertThrows(IllegalArgumentException.class, () -> BasicMetricEvaluator.getIdentifierForMinimumValue(dataList,
      (data) -> Integer.toString(data.getDay()), WeatherData::getTemperatureSpread));
  }

  @Test
  void getIdentifierForMinMultipleMinData(){
    int day1 = 1;
    int winnerDay1 = 2;
    int winnerDay2 = 3;

    WeatherData dat1 = new WeatherData(day1, 0,1);
    WeatherData winnerDat1 = new WeatherData(winnerDay1,0,0);
    WeatherData winnerDat2 = new WeatherData(winnerDay2,0,0);

    List<WeatherData> dataList = Arrays.asList(dat1, winnerDat1, winnerDat2);

    String result = BasicMetricEvaluator.getIdentifierForMinimumValue(dataList,
      data -> Integer.toString(data.getDay()), WeatherData::getTemperatureSpread);

    List<String> expectedDays = Arrays.asList(Integer.toString(winnerDay1),
      Integer.toString(winnerDay2));

    assertTrue(expectedDays.contains(result));
  }
}