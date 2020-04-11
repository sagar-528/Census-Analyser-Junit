package censusanalyser;

import censusanalyser.DAO.IndiaCensusDAO;
import censusanalyser.DAO.censusDAO;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class CensusAnalyser {

    List<censusDAO> csvFileList;
    Map<String, censusDAO> csvFileMap;

    public CensusAnalyser() {
        this.csvFileMap = new HashMap<String, censusDAO>();
    }

    public int loadStateCensusData(String csvFilePath) throws CensusAnalyserException{
        csvFileMap = new CensusLoader().loadCensusData(csvFilePath, IndiaStateCensusCSV.class);
        return csvFileMap.size();
    }

    public int loadUSCensusData(String usCensusCsvFilePath) throws CensusAnalyserException{
        csvFileMap = new CensusLoader().loadCensusData(usCensusCsvFilePath, USCensusCSV.class);
        return csvFileMap.size();
    }

    public int loadStateCode(String indiaCensusCSVFilePath) throws CensusAnalyserException
    {
        int count = 0;

        try( Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCSVFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCodeCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> stateCodeCSVIterator;

            StreamSupport.stream(csvIterable.spliterator(),false)
                    .filter(csvState ->csvFileMap.get(csvState.stateName)!= null)
                    .forEach(censusCSV ->csvFileMap.get(censusCSV.stateName).state = censusCSV.stateCode);
            return count;
        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public String getStateWiseSortedData(String csvFilePath) throws CensusAnalyserException
    {
        if (csvFileMap == null || csvFileMap.size() == 0){
            throw new CensusAnalyserException("Data empty", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Map<String, Comparator<IndiaCensusDAO>> comparatorMap = new HashMap<String, Comparator<IndiaCensusDAO>>();
        Comparator<censusDAO> censusComparator = Comparator.comparing(census -> census.state);
        comparatorMap.put("state", Comparator.comparing(census -> census.state));
        List<censusDAO> censusDAOS = csvFileMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS, censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusDAOS);
        return sortedStateCensusJson;
    }

   private void sort(List<censusDAO> censusDAOS, Comparator<censusDAO> censusComparator) throws CensusAnalyserException
   {
        for (int iterator = 0; iterator < censusDAOS.size(); iterator++){
            for (int innerIterator = 0; innerIterator < censusDAOS.size() - iterator -1; innerIterator++){
                censusDAO census1 = censusDAOS.get(innerIterator);
                censusDAO census2 = censusDAOS.get(innerIterator + 1);

                if (censusComparator.compare(census1, census2) > 0){
                    censusDAOS.set(innerIterator, census2);
                    censusDAOS.set(innerIterator, census1);
                }
            }
        }
   }
}
