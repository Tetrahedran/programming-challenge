package de.bcxp.challenge.data;

import de.bcxp.challenge.model.CountryData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CountryDataFactory implements DataProvider<CountryData>{
  public static final String NAME_IDENTIFIER = "Name";
  public static final String POPULATION_IDENTIFIER = "Population";
  public static final String AREA_IDENTIFIER = "Area";

  @Override
  public List<CountryData> getDataObjectsFrom(AttributeListProvider provider) {
    Objects.requireNonNull(provider);

    List<CountryData> dataList = new ArrayList<>();

    while(provider.hasNewAttributeList()){
      Map<String, String> attributes = provider.getNextAttributeList();
      String name = attributes.get(NAME_IDENTIFIER);
      int population = Integer.parseInt(attributes.get(POPULATION_IDENTIFIER));
      double area = Double.parseDouble(attributes.get(AREA_IDENTIFIER));

      CountryData cData = new CountryData(name, population, area);
      dataList.add(cData);
    }

    return dataList;
  }
}
