package de.bcxp.challenge.data;


import java.util.List;
import java.util.Map;

/**
 * {@link AttributeListProvider} for test purposes.
 * Allows to mock csv files by input Map
 */
public class TestAttributeListProvider implements AttributeListProvider{
  private final List<Map<String, String>> data;
  private int i;

  public TestAttributeListProvider(List<Map<String, String>> data){
    this.data = data;
    i = 0;
  }

  @Override
  public Map<String, String> getNextAttributeList() {
    if(hasNewAttributeList()){
      Map<String, String> result = data.get(i);
      i++;
      return result;
    }
    else{
      throw new IllegalArgumentException("Tried to access next attribute list, but no new attribute list is available");
    }
  }

  @Override
  public boolean hasNewAttributeList() {
    return i < data.size();
  }
}
