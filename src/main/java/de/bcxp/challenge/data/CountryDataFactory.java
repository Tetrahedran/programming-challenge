package de.bcxp.challenge.data;

import de.bcxp.challenge.model.CountryData;

import java.util.*;

public class CountryDataFactory implements DataProvider<CountryData>{
  public static final String NAME_IDENTIFIER = "Name";
  public static final String POPULATION_IDENTIFIER = "Population";
  public static final String AREA_IDENTIFIER = "Area";

  /**
   * Matches european styled semantic integer numbers that contain a decimal comma with following zeros and
   * optional points as thousands separator, e.g. 1,00 or 1.234,0 or 1.234.567,000
   */
  public static final String EUROPEAN_NUMBER_REGEX = "[[\\d]{1,3}[.]]*[\\d]{3}[,][0]+";

  @Override
  public List<CountryData> getDataObjectsFrom(AttributeListProvider provider) {
    Objects.requireNonNull(provider);

    List<CountryData> dataList = new ArrayList<>();

    while(provider.hasNewAttributeList()){
      Map<String, String> attributes = provider.getNextAttributeList();
      String name = attributes.get(NAME_IDENTIFIER);
      String populationString = attributes.get(POPULATION_IDENTIFIER);

      if(populationString.matches(EUROPEAN_NUMBER_REGEX)){
        // remove thousands separator
        populationString = populationString.replaceAll("[.]", "");
        // remove comma and zeros after it
        populationString = populationString.split(",")[0];
      }
      long population = Long.parseLong(populationString);
      double area = Double.parseDouble(attributes.get(AREA_IDENTIFIER));

      CountryData cData = new CountryData(name, population, area);
      dataList.add(cData);
    }

    return dataList;
  }
}
