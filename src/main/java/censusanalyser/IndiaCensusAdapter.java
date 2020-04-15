package censusanalyser;

import Com.Bridgelabz.CSVBuilderException;
import Com.Bridgelabz.ICSVBuilder;
import censusanalyser.DAO.censusDAO;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusAdapter extends CensusAdapter{
    public Map<String, censusDAO> loadCensusData(CensusAnalyser.Country country, String... csvFilePath) throws CensusAnalyserException {
        Map<String, censusDAO> censusStateMap = super.loadCensusData(IndiaStateCensusCSV.class, csvFilePath[0]);
        this.loadIndiaStateCode(censusStateMap, csvFilePath[1]);
        return censusStateMap;
    }

    public int loadIndiaStateCode(Map<String, censusDAO> censusDAOMap, String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> csvStateCode = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> stateCodeCSVS = () -> csvStateCode;

            StreamSupport.stream(stateCodeCSVS.spliterator(), false)
                    .filter(csvState -> censusDAOMap.get(csvState.stateName) != null)
                    .forEach(censusCSV -> censusDAOMap.get(censusCSV.stateName).state = censusCSV.stateCode);
            return censusDAOMap.size();
        } catch (IOException | CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
}

