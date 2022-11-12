package de.bcxp.challenge.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
   * @param filePath Path of csv file to be processed by this reader
   * @param separator Separator for columns used in csv file
   * @throws FileNotFoundException If file at filePath does not exist
   */
  public CSVFileReader(String filePath, char separator) throws FileNotFoundException{
    this.path = filePath;
    this.separator = Character.toString(separator);
    this.line = "";
    openFile();
  }

  /**
   * Opens the file at filePath and does preprocessing of the file
   * @throws FileNotFoundException If no file at filePath exists
   */
  private void openFile() throws FileNotFoundException {
    File csv = new File(path);
    scanner = new Scanner(csv);
    extractAttributeNames();
  }

  /**
   * Extracts the names of the columns from csv file header and saves them as attribute names for further processing
   */
  private void extractAttributeNames(){
    if(!hasNewAttributeList()){
      throw new IllegalArgumentException("csv file " + path + " is empty");
    }

    String header = this.line;

    if(header.trim().isEmpty()){
      throw new IllegalArgumentException("Csv file " + path + " contains blank header");
    }

    String[] parts = header.split(separator);
    attributeNames = Arrays.stream(parts).map(String::trim).toList();
  }

  @Override
  public Map<String, String> getNextAttributeList() {
    if(this.line.trim().isEmpty() && !hasNewAttributeList()){
      throw new RuntimeException("Reached end of file");
    }

    String line = this.line;
    this.line = "";
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
