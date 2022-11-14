package de.bcxp.challenge.data;

import de.bcxp.challenge.model.CountryData;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

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
      int population;
      try {
        Number number = NumberFormat.getInstance(Locale.GERMANY).parse(attributes.get(POPULATION_IDENTIFIER));
        population = number.intValue();
      }
      catch(ParseException pe){
        throw new IllegalArgumentException("number " + attributes.get(POPULATION_IDENTIFIER) + " doesn't match" +
          "the requested number format",pe);
      }
      double area = Double.parseDouble(attributes.get(AREA_IDENTIFIER));

      CountryData cData = new CountryData(name, population, area);
      dataList.add(cData);
    }

    return dataList;
  }
}
