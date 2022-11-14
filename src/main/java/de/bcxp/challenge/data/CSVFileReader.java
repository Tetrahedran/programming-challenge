package de.bcxp.challenge.data;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Generic file reader for csv files
 * Has to be closed after use
 */
public class CSVFileReader implements AttributeListProvider, AutoCloseable {
  /**
   * Regex for detecting annotations in brackets in csv header fields
   */
  public static final String HEADER_ANNOTATION_REGEX = "\\s[(].*[)]";

  private final String path;
  private final String separator;

  private Scanner scanner;
  private List<String> attributeNames;
  private String line;

  /**
   * Constructor
   * @param stream Input stream for csv file to be processed
   * @param path Path of the csv file for logging purposes
   * @param separator Separator for columns used in csv file
   */
  public CSVFileReader(InputStream stream, String path, char separator){
    Objects.requireNonNull(stream);

    this.separator = Character.toString(separator);
    this.line = "";
    this.path = path;
    preprocessStream(stream);
  }

  /**
   * Preprocesses the stream by creating a scanner for it and extracting attribute names from underlying csv resource
   * @param stream Stream to csv resource
   */
  private void preprocessStream(InputStream stream){
    scanner = new Scanner(stream);
    extractAttributeNames();
  }

  /**
   * Extracts the names of the columns from csv file header and saves them as attribute names for further processing
   * The first line of the csv file that is not empty or not only contains white spaces is considered to be the header
   * Removes unit annotations in brackets after a white space, e.g. Area (km²) becomes just Area
   * @throws IllegalArgumentException if csv file is empty
   */
  private void extractAttributeNames(){
    if(!hasNewAttributeList()){
      throw new IllegalArgumentException("csv file " + path + " is empty");
    }

    String header = consumeLine();

    String[] parts = header.split(separator);
    attributeNames = Arrays.stream(parts)
      .map(String::trim)
      .map(head -> {
        if(head.matches(".*" + HEADER_ANNOTATION_REGEX)){
          String[] headerParts = head.split(HEADER_ANNOTATION_REGEX);
          head = headerParts[0].trim();
        }
        return head;
      })
      .collect(Collectors.toList());
  }

  /**
   * Returns the currently saved line and sets the field to an empty string
   * Removes non-printable UTF-8 chars
   * @return the currently saved line
   */
  private String consumeLine(){
    String tmp = this.line;
    // Bug Fix for invisible characters taken from
    // https://stackoverflow.com/questions/15520791/remove-non-printable-utf8-characters-except-controlchars-from-string
    tmp = tmp.replaceAll("\\p{C}", "");
    this.line = "";
    return tmp;
  }

  /**
   * Returns the next available attribute list that maps attribute name to attribute string value
   * @return an attribute list that maps attribute name to attribute string value
   * @throws RuntimeException if end of csv file is reached
   * @throws RuntimeException if the number of entries in a row doesn't match the number of headers in the csv file
   */
  @Override
  public Map<String, String> getNextAttributeList() {
    if(this.line.trim().isEmpty() && !hasNewAttributeList()){
      throw new RuntimeException("Reached end of file");
    }

    String line = consumeLine();
    String[] parts = line.split(separator);

    if(parts.length != attributeNames.size()){
      throw new RuntimeException("Line " + line + " contains a number of elements that does not match the " +
        "number of headers in file " + path);
    }

    Map<String, String> attributeMappings = new HashMap<>();
    for(int i = 0; i < parts.length; i++){
      attributeMappings.put(attributeNames.get(i), parts[i]);
    }

    return attributeMappings;
  }

  /**
   * Indicates whether a new AttributeList can be read from this AttributeListProvider.
   * Skips empty lines in csv files and stores current line in csv file in this.line to make it accessible for get-Method
   * Doesn't perform a skip operation if called multiple times without calling get-Method
   * @return true if there is a new Attribute List available, else false
   */
  @Override
  public boolean hasNewAttributeList() {
    boolean result;
    if(line.isEmpty()) {
      // Last line has already been consumed
      result = false;
      while (scanner.hasNext() && line.isEmpty()) {
        line = scanner.nextLine();
        if (! line.trim().isEmpty()) {
          result = true;
          break;
        }
      }
    }
    else{
      // Line from last call to hasNewAttributeList has not been consumed by getNextAttributeList
      result = true;
    }
    return result;
  }

  @Override
  public void close() {
    scanner.close();
  }
}
