package de.bcxp.challenge;

import de.bcxp.challenge.data.CSVFileReader;
import de.bcxp.challenge.data.CountryDataFactory;
import de.bcxp.challenge.data.DataProvider;
import de.bcxp.challenge.data.WeatherDataFactory;
import de.bcxp.challenge.logic.BasicMetricEvaluator;
import de.bcxp.challenge.model.CountryData;
import de.bcxp.challenge.model.WeatherData;

import java.io.InputStream;
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
        List<WeatherData> wDataList = new ArrayList<>();
        String weatherDataFilePath = "de/bcxp/challenge/weather.csv";
        InputStream weatherStream = App.class.getClassLoader().getResourceAsStream(weatherDataFilePath);
        try(CSVFileReader weatherStringProvider = new CSVFileReader(weatherStream, weatherDataFilePath, ',')){
            DataProvider<WeatherData> weatherDataObjectProvider = new WeatherDataFactory();
            wDataList = weatherDataObjectProvider.getDataObjectsFrom(weatherStringProvider);
        }

        List<CountryData> cDataList = new ArrayList<>();
        String countryDataFilePath = "de/bcxp/challenge/countries.csv";
        InputStream countryStream = App.class.getClassLoader().getResourceAsStream(countryDataFilePath);
        try(CSVFileReader countryStringProvider = new CSVFileReader(countryStream, countryDataFilePath, ';')){
            DataProvider<CountryData> countryDataObjectProvider = new CountryDataFactory();
            cDataList = countryDataObjectProvider.getDataObjectsFrom(countryStringProvider);
        }

        String dayWithSmallestTempSpread = BasicMetricEvaluator.getIdentifierForMinimumMetric(
          wDataList, wData -> Integer.toString(wData.getDay()), WeatherData::getTemperatureSpread);     // Your day analysis function call …
        System.out.printf("Day with smallest temperature spread: %s%n", dayWithSmallestTempSpread);

        String countryWithHighestPopulationDensity = BasicMetricEvaluator.getIdentifierForMaximumMetric(
          cDataList, CountryData::getName, CountryData::getPopulationDensity);
        System.out.printf("Country with highest population density: %s%n", countryWithHighestPopulationDensity);
    }
}
