package de.bcxp.challenge.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CSVFileReaderTest {

  public CSVFileReader reader;

  @AfterEach
  void cleanUp(){
    if(reader != null){
      reader.close();
    }
  }

  /**
   * Tests correct error when {@link CSVFileReader} is created with empty csv file
   */
  @Test
  void createCSVFileReaderEmptyFileTest(){
    String path = "de/bcxp/challenge/Empty.csv";
    InputStream stream = getClass().getClassLoader().getResourceAsStream(path);
    assertThrows(IllegalArgumentException.class, () -> reader = new CSVFileReader(stream, path, ';'));
  }

  /**
   * Tests correct error when {@link CSVFileReader} is created with white space only csv file
   */
  @Test
  void createCSVFileReaderWhiteSpaceFileTest(){
    String path = "de/bcxp/challenge/WhiteSpace.csv";
    InputStream stream = getClass().getClassLoader().getResourceAsStream(path);
    assertThrows(IllegalArgumentException.class, () -> reader = new CSVFileReader(stream, path, ';'));
  }

  /**
   * Tests correct yielding of Attribute List of getNextAttributeList
   */
  @Test
  void getNextAttributeListTest(){
    String firstCol = "First";
    String secondCol = "Second";
    String thirdCol = "Third";
    String firstExpect = "1";
    String secondExpect = "2.65";
    String thirdExpct = "Hallo";
    String path = "de/bcxp/challenge/SimpleFile.csv";

    InputStream stream = getClass().getClassLoader().getResourceAsStream(path);
    reader = new CSVFileReader(stream, path, ';');

    Map<String, String> attributeList = reader.getNextAttributeList();
    assertTrue(attributeList.containsKey(firstCol));
    assertTrue(attributeList.containsKey(secondCol));
    assertTrue(attributeList.containsKey(thirdCol));

    assertEquals(firstExpect,attributeList.get(firstCol));
    assertEquals(secondExpect, attributeList.get(secondCol));
    assertEquals(thirdExpct, attributeList.get(thirdCol));
  }

  /**
   * Tests correct order when using getNextAttributeList with multiple lines in input file
   */
  @Test
  void getNextAttributeListMultiLineTest(){
    String firstCol = "First";
    String secondCol = "Second";
    String[][] results = {{"a", "1"},{"b", "2"},{"c", "3"}};
    String path = "de/bcxp/challenge/MultiLineFile.csv";

    InputStream stream = getClass().getClassLoader().getResourceAsStream(path);
    reader = new CSVFileReader(stream, path, ';');

    int i = 0;
    while(reader.hasNewAttributeList()) {
      Map<String, String> attributeList = reader.getNextAttributeList();
      assertTrue(attributeList.containsKey(firstCol));
      assertTrue(attributeList.containsKey(secondCol));

      assertEquals(results[i][0], attributeList.get(firstCol));
      assertEquals(results[i][1], attributeList.get(secondCol));
      i++;
    }
    assertEquals(3, i);
  }

  /**
   * Tests correct yielding of Attribute List of getNextAttributeList when csv file contains blank lines
   */
  @Test
  void getNextAttributeListWithBlankLinesInFileTest(){
    String firstCol = "First";
    String secondCol = "Second";
    String thirdCol = "Third";
    String firstExpect = "1";
    String secondExpect = "2.65";
    String thirdExpct = "Hallo";
    String path = "de/bcxp/challenge/SimpleFileWithBlankLines.csv";

    InputStream stream = getClass().getClassLoader().getResourceAsStream(path);
    reader = new CSVFileReader(stream, path, ';');

    Map<String, String> attributeList = reader.getNextAttributeList();
    assertTrue(attributeList.containsKey(firstCol));
    assertTrue(attributeList.containsKey(secondCol));
    assertTrue(attributeList.containsKey(thirdCol));

    assertEquals(firstExpect,attributeList.get(firstCol));
    assertEquals(secondExpect, attributeList.get(secondCol));
    assertEquals(thirdExpct, attributeList.get(thirdCol));
  }

  /**
   * Tests correct yielding of Attribute List of getNextAttributeList when csv file contains white spaces in header
   */
  @Test
  void getNextAttributeListWithWhiteSpacesInHeader(){
    String firstCol = "First";
    String secondCol = "Second";
    String thirdCol = "Third";
    String path = "de/bcxp/challenge/SimpleFileWithWhiteSpacesInHeader.csv";

    InputStream stream = getClass().getClassLoader().getResourceAsStream(path);
    reader = new CSVFileReader(stream, path, ';');

    Map<String, String> attributeList = reader.getNextAttributeList();
    assertTrue(attributeList.containsKey(firstCol));
    assertTrue(attributeList.containsKey(secondCol));
    assertTrue(attributeList.containsKey(thirdCol));
  }

  /**
   * Tests correct Error when calling getNextAttributeList but there is no new list available
   */
  @Test
  void getNextAttributeListAtEOF(){
    String path = "de/bcxp/challenge/SimpleFile.csv";
    InputStream stream = getClass().getClassLoader().getResourceAsStream(path);
    reader = new CSVFileReader(stream, path, ';');
    reader.getNextAttributeList();
    assertThrows(RuntimeException.class, () -> reader.getNextAttributeList());
  }

  /**
   * Tests correct error when one line in csv file contains a number of elements that doesn't match the number
   * of headers in this file
   */
  @Test
  void getNextAttributeListIncorrectNumberOfColsInRow(){
    String path = "de/bcxp/challenge/IncorrectNumberCols.csv";
    InputStream stream = getClass().getClassLoader().getResourceAsStream(path);
    reader = new CSVFileReader(stream, path, ';');
    assertThrows(RuntimeException.class, () -> reader.getNextAttributeList());
  }

  /**
   * Tests hasNewAttributeList with csv file containing one next list
   */
  @Test
  void hasNextAttributeListSingleLineTest(){
    String path = "de/bcxp/challenge/SimpleFile.csv";
    InputStream stream = getClass().getClassLoader().getResourceAsStream(path);
    reader = new CSVFileReader(stream, path, ';');

    assertTrue(reader.hasNewAttributeList());

    reader.getNextAttributeList();

    assertFalse(reader.hasNewAttributeList());
  }

  /**
   * Tests hasNewAttributeList with csv file containing multiple next lists
   */
  @Test
  void hasNextAttributeListMultiLineTest(){
    String path = "de/bcxp/challenge/MultiLineFile.csv";
    InputStream stream = getClass().getClassLoader().getResourceAsStream(path);
    reader = new CSVFileReader(stream, path, ';');

    assertTrue(reader.hasNewAttributeList());

    reader.getNextAttributeList();

    assertTrue(reader.hasNewAttributeList());

    reader.getNextAttributeList();

    assertTrue(reader.hasNewAttributeList());

    reader.getNextAttributeList();

    assertFalse(reader.hasNewAttributeList());
  }

  /**
   * Tests hasNewAttributeList with csv file containing blank lines
   */
  @Test
  void hasNextAttributeListBlankLinesTest(){
    String path = "de/bcxp/challenge/SimpleFileWithBlankLines.csv";
    InputStream stream = getClass().getClassLoader().getResourceAsStream(path);
    reader = new CSVFileReader(stream, path, ';');

    assertTrue(reader.hasNewAttributeList());

    reader.getNextAttributeList();

    assertFalse(reader.hasNewAttributeList());
  }
}
