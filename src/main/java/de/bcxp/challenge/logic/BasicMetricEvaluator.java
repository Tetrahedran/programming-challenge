package de.bcxp.challenge.logic;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class for basic evaluation of data object metrics
 */
public class BasicMetricEvaluator {

  /**
   * Searches for the element with the lowest metric and returns its identifier
   * @param data List of data objects
   * @param idProvider Provider for an identifier for class T
   * @param metricProvider Provider for the relevant metric
   * @param <T> class of the data objects
   * @return The identifier of the data object with the lowest metric
   */
  public static <T> String getIdentifierForMinimumValue(
    List<T> data,
    IdentifierProvider<T> idProvider,
    MetricProvider<T> metricProvider)
  {
    if(data.isEmpty()){
      throw new IllegalArgumentException("The provided data list was empty");
    }

    String id = data.stream()
      .min(Comparator.comparingDouble(metricProvider::getMetric))
      .map(idProvider::getIdentifier).orElseThrow(()->new NoSuchElementException("A minimum value couldn't be found"));
    return id;
  }

  public static <T> String getIdentifierForMaximumMetric(
    List<T> data,
    IdentifierProvider<T> idProvider,
    MetricProvider<T> metricProvider
  ){
    if(data.isEmpty()){
      throw new IllegalArgumentException("The provided data list was empty");
    }

    String id = data.stream()
      .max(Comparator.comparingDouble(metricProvider::getMetric))
      .map(idProvider::getIdentifier).orElseThrow(()->new NoSuchElementException("A maximum value couldn't be found"));
    return id;
  }

}
