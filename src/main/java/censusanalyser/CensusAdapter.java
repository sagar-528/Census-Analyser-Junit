package censusanalyser;

import censusanalyser.DAO.censusDAO;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public abstract class CensusAdapter {

    Map<String, censusDAO> censusDAOMap = new HashMap<>();

    public abstract Map <String, censusDAO> loadCensusData(CensusAnalyser.Country country, String... csvFilePath) throws CensusAnalyserException;

    public <T> Map loadCensusData(Class<T> censusCSVClass, String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<T> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<T> censusDAOIterable = () -> censusCSVIterator;

            if (censusCSVClass.getName().equals("censusanalyser.IndiaStateCensusCSV")) {
                StreamSupport.stream(censusDAOIterable.spliterator(), false)
                        .map(IndiaStateCensusCSV.class::cast)
                        .forEach(censusCSV -> censusDAOMap.put(censusCSV.state, new censusDAO(censusCSV)));
            } else if (censusCSVClass.getName().equals("censusanalyser.USCensusCSV")) {
                StreamSupport.stream(censusDAOIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV -> censusDAOMap.put(censusCSV.State, new censusDAO(censusCSV)));
            }
            return censusDAOMap;
        } catch (IOException e) {

            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);

        } catch (RuntimeException e) {

            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }
}