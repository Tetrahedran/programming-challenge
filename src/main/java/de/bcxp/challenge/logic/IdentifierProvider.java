package de.bcxp.challenge.logic;

/**
 * Functional interface for providers that provide an identifier for a given data object
 * @param <T> class of the data objects
 */
@FunctionalInterface
public interface IdentifierProvider<T>{

  /**
   * @param data data to be processed
   * @return A string identifier for data
   */
  String getIdentifier(T data);
}
