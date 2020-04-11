package censusanalyser;

import censusanalyser.DAO.censusDAO;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusLoader
{

    Map<String, censusDAO> censusStateMap = null;

    public CensusLoader() {

    }

    public <T> Map <String, censusDAO> loadCensusData(Class<T> censusCSVClass, String... csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<T> csvIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<T> censusCSVIterable = () -> csvIterator;
            if (censusCSVClass.getName().equals("censusanalyser.IndiaStateCensusCSV")) {
                StreamSupport.stream(censusCSVIterable.spliterator(), false)
                        .map(IndiaStateCensusCSV.class::cast)
                        .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new censusDAO(censusCSV)));
            } else if (censusCSVClass.getName().equals("censusanalyser.USCensusCSV")) {
                StreamSupport.stream(censusCSVIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV -> censusStateMap.put(censusCSV.State, new censusDAO(censusCSV)));
            }
            return censusStateMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (NullPointerException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES);
        }
    }


        public int loadStateCode(Map<String, censusDAO> censusStateMap, String indiaCensusCSVFilePath) throws CensusAnalyserException
        {

            try( Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCSVFilePath))) {
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<IndiaStateCodeCSV> stateCodeCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
                Iterable<IndiaStateCodeCSV> csvIterable = () -> stateCodeCSVIterator;

                StreamSupport.stream(csvIterable.spliterator(),false)
                        .filter(csvState ->censusStateMap.get(csvState.stateName)!= null)
                        .forEach(censusCSV ->censusStateMap.get(censusCSV.stateName).state = censusCSV.stateCode);
                return censusStateMap.size();
            }catch (IOException e) {
                throw new CensusAnalyserException(e.getMessage(),
                        CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
            }catch (CSVBuilderException e) {
                throw new CensusAnalyserException(e.getMessage(), e.type.name());
            }
        }
}

