package de.bcxp.challenge.logic;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

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
   * @throws IllegalArgumentException If the provided list of data is empty
   * @throws NoSuchElementException If the provided list contains no minimum element
   * @throws NullPointerException If any idProvider returns null value
   *
   */
  public static <T> String getIdentifierForMinimumMetric(
    List<T> data,
    IdentifierProvider<T> idProvider,
    MetricProvider<T> metricProvider)
  {
    Objects.requireNonNull(data);
    Objects.requireNonNull(idProvider);
    Objects.requireNonNull(metricProvider);

    if(data.isEmpty()){
      throw new IllegalArgumentException("The provided data list was empty");
    }

    String id = data.stream()
      .peek(item -> checkIfNonNull(idProvider.getIdentifier(item), item))
      .min(Comparator.comparingDouble(metricProvider::getMetric))
      .map(idProvider::getIdentifier).orElseThrow(()->new NoSuchElementException("A minimum value couldn't be found"));
    return id;
  }

  /**
   * Searches for the element with the highest metric and returns its identifier
   * @param data List of data objects
   * @param idProvider Provider for an identifier for class T
   * @param metricProvider Provider for the relevant metric
   * @param <T> class of the data objects
   * @return The identifier of the data object with the highest metric
   * @throws IllegalArgumentException If the provided list of data is empty
   * @throws NoSuchElementException If the provided list contains no maximum element
   * @throws NullPointerException If any idProvider returns null value
   */
  public static <T> String getIdentifierForMaximumMetric(
    List<T> data,
    IdentifierProvider<T> idProvider,
    MetricProvider<T> metricProvider
  ){
    Objects.requireNonNull(data);
    Objects.requireNonNull(idProvider);
    Objects.requireNonNull(metricProvider);

    if(data.isEmpty()){
      throw new IllegalArgumentException("The provided data list was empty");
    }

    String id = data.stream()
      .peek(item -> checkIfNonNull(idProvider.getIdentifier(item), item))
      .max(Comparator.comparingDouble(metricProvider::getMetric))
      .map(idProvider::getIdentifier).orElseThrow(()->new NoSuchElementException("A maximum value couldn't be found"));
    return id;
  }

  /**
   * Checks if objectToCheck is non-null
   * @param objectToCheck Object which has to be non-null
   * @param source source of objectToCheck
   * @throws NullPointerException If objectToCheck is null
   */
  private static void checkIfNonNull(Object objectToCheck, Object source){
    Objects.requireNonNull(objectToCheck, "Object " + source.toString() + " contains null value");
  }

}
