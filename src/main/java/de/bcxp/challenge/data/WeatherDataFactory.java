package de.bcxp.challenge.data;

import de.bcxp.challenge.model.WeatherData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WeatherDataFactory implements DataProvider<WeatherData>{
  public static final String MAXIMUM_TEMP_IDENTIFIER = "MxT";
  public static final String MINIMUM_TEMP_IDENTIFIER = "MnT";
  public static final String DAY_IDENTIFIER = "Day";


  @Override
  public List<WeatherData> getDataObjectsFrom(AttributeListProvider provider) {

    Objects.requireNonNull(provider);

    List<WeatherData> data = new ArrayList<>();

    while(provider.hasNewAttributeList()){
      Map<String, String> attributes = provider.getNextAttributeList();
      int day = Integer.parseInt(attributes.get(DAY_IDENTIFIER));
      int maxTemp = Integer.parseInt(attributes.get(MAXIMUM_TEMP_IDENTIFIER));
      int minTemp = Integer.parseInt(attributes.get(MINIMUM_TEMP_IDENTIFIER));

      WeatherData wData = new WeatherData(day, minTemp, maxTemp);
      data.add(wData);
    }

    return data;
  }
}
