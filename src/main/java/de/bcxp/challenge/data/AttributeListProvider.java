package de.bcxp.challenge.data;

import java.util.Map;

/**
 * Provider for attributes that can be used to create data objects
 */
public interface AttributeListProvider{

  /**
   * Returns the next attribute list with data for a new data object
   * @return A map that maps attribute name to attribute value
   */
  Map<String, String> getNextAttributeList();

  /**
   * Checks if another attribute list can be created
   * @return true if another attribute list can be returned, else false
   */
  boolean hasNewAttributeList();
}
