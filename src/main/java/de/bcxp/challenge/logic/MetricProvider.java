package de.bcxp.challenge.logic;

/**
 * Functional interface for providers that provide metrics of given data object
 * @param <T> class of the processed data objects
 */
@FunctionalInterface
public interface MetricProvider<T> {

  /**
   * @param data Data to be processed
   * @return a double metric from data
   */
  double getMetric(T data);
}
