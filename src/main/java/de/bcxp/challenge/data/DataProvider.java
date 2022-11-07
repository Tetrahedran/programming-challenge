package de.bcxp.challenge.data;

import java.util.List;

/**
 * Generic provider for data objects made from data provided by AttributeListProvider
 * @param <T> Class of the data objects to be produced
 */
public interface DataProvider<T>{

  /**
   * returns all data objects that can be created from AttributeListProvider
   * @param provider Provider for data that can be used to generate data objects of Type t
   * @return A list of objects of type T
   */
  List<T> getDataObjectsFrom(AttributeListProvider provider);
}
