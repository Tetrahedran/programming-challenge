package de.bcxp.challenge;

import de.bcxp.challenge.data.AttributeListProvider;
import de.bcxp.challenge.data.CSVFileReader;
import de.bcxp.challenge.data.DataProvider;
import de.bcxp.challenge.data.WeatherDataFactory;
import de.bcxp.challenge.logic.BasicMetricEvaluator;
import de.bcxp.challenge.model.WeatherData;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 */
public final class App {

    /**
     * This is the main entry method of your program.
     * @param args The CLI arguments passed
     */
    public static void main(String... args) {

        // Your preparation code …
        List<WeatherData> data = new ArrayList<>();
        String filePath = "C:\\Users\\Patrick Hanselmann\\Documents\\Arbeit\\programming-challenge\\src\\main\\resources\\de\\bcxp\\challenge\\weather.csv";
        try(CSVFileReader provider = new CSVFileReader(filePath, ',')){
            DataProvider<WeatherData> dataProvider = new WeatherDataFactory();
            data = dataProvider.getDataObjectsFrom(provider);
        }
        catch(Exception fe){
            System.out.println(fe.getMessage());
        }

        String dayWithSmallestTempSpread = BasicMetricEvaluator.getIdentifierForMinimumValue(
          data, wData -> Integer.toString(wData.getDay()), WeatherData::getTemperatureSpread);     // Your day analysis function call …
        System.out.printf("Day with smallest temperature spread: %s%n", dayWithSmallestTempSpread);

        String countryWithHighestPopulationDensity = "Some country"; // Your population density analysis function call …
        System.out.printf("Country with highest population density: %s%n", countryWithHighestPopulationDensity);
    }
}
