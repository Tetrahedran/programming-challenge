package de.bcxp.challenge.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Generic file reader for csv files
 */
public class CSVFileReader implements AttributeListProvider, AutoCloseable {
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
   */
  private void extractAttributeNames(){
    if(!hasNewAttributeList()){
      throw new IllegalArgumentException("csv file " + path + " is empty");
    }

    String header = consumeLine();

    if(header.trim().isEmpty()){
      throw new IllegalArgumentException("Csv file " + path + " contains blank header");
    }

    String[] parts = header.split(separator);
    attributeNames = Arrays.stream(parts).map(String::trim).collect(Collectors.toList());
  }

  /**
   * Returns the currently saved line and sets the field to an empty string
   * @return the currently saved line
   */
  private String consumeLine(){
    String tmp = this.line;
    this.line = "";
    return tmp;
  }

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
