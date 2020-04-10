package censusanalyser;

import censusanalyser.DAO.IndianStateCensusDAO;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.*;
import java.util.stream.StreamSupport;


public class CensusAnalyser {

    List<IndianStateCensusDAO> csvFileList;

    public CensusAnalyser() {
        this.csvFileList = new ArrayList<IndianStateCensusDAO>();
    }

    public int loadStateCensusData(String csvFilePath) throws CensusAnalyserException
    {
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
           ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
           Iterator<IndiaStateCensusCSV> csvIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCensusCSV.class);
            while (csvIterator.hasNext()){
               this.csvFileList.add(new IndianStateCensusDAO(csvIterator.next()));
            }
            return csvFileList.size();
        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public int loadStateCode(String indiaCensusCSVFilePath) throws CensusAnalyserException
    {
        try( Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCSVFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<IndiaStateCensusCSV> csvFileList = csvBuilder.getCSVFileList(reader, IndiaStateCensusCSV.class);
            return csvFileList.size();
        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    private <T> int getCount(Iterator<T> iterator){
        Iterable<T> iterable = () -> iterator;
        int nameOfEntries = (int) StreamSupport.stream(iterable.spliterator(),false).count();
        return nameOfEntries;
    }

    public String getStateWiseSortedData(String csvFilePath) throws CensusAnalyserException
    {
        loadStateCensusData(csvFilePath);
        if (csvFileList == null || csvFileList.size() == 0){
            throw new CensusAnalyserException("Data empty", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndianStateCensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.csvFileList);
        return sortedStateCensusJson;
    }

   private void sort(Comparator<IndianStateCensusDAO> censusComparator){
        for (int iterator = 0; iterator < csvFileList.size(); iterator++){
            for (int innerIterator = 0; innerIterator < csvFileList.size() - iterator -1; innerIterator++){
                IndianStateCensusDAO census1 = csvFileList.get(innerIterator);
                IndianStateCensusDAO census2 = csvFileList.get(innerIterator + 1);

                if (censusComparator.compare(census1, census2) > 0){
                    csvFileList.set(innerIterator, census2);
                    csvFileList.set(innerIterator, census1);
                }
            }
        }
   }
}
