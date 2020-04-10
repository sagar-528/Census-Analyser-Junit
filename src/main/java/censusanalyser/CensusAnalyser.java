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


public class CensusAnalyser {
    IcsvBuilder csvBuilder = new CSVBuilderFactory().createCSVBuilder();
    Collection<IndianStateCensusDAO> censusRecord = null;
    HashMap<Integer, IndianStateCensusDAO> censusHashMap = null;


    public int loadStateCensusData(String csvFilePath) throws CSVBuilderException, IOException
    {
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            Iterator<IndianStateCensusCSV> csvIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCensusCSV.class);
            Integer count = 0;
            while (csvFileIterator.hasNext()){
                IndianStateCensusDAO indianStateCensusDAO = new IndianStateCensusDAO(csvFileIterator.next());
                this.censusHashMap.put(count,indianStateCensusDAO);
                count++;
            }
            return censusHashMap.size();
        }catch (NoSuchFileException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.INVALID_FILE_DELIMITER);
        }
    }

    public int loadStateCodeData(String csvFilePath) throws CSVBuilderException, IOException
    {
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            Iterator<IndianStateCodeCSV> csvIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Integer count = 0;
            while (csvFileIterator.hasNext()){
                IndianStateCensusDAO indianStateCensusDAO = new IndianStateCensusDAO(csvFileIterator.next());
                this.censusHashMap.put(count,indianStateCensusDAO);
                count++;
            }
            return censusHashMap.size();
        }catch (NoSuchFileException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.INVALID_FILE_DELIMITER);
        }
    }

    public void checkValidCSVFile(File csvFilePath) throws CSVBuilderException
    {
        String fileName = csvFilePath.getName();
        String extension = null;
        if (fileName.lastIndexOf(".")!= -1 && fileName.lastIndexOf(".")!= 0)
            extension = fileName.substring(fileName.lastIndexOf(".")+1);
        if(!(extension.equals(".csv"))) {
            throw new CSVBuilderException("Invalid file type",
                    CSVBuilderException.ExceptionType.INVALID_FILE_TYPE);
        }
    }

    public String getStateWiseSortedData() throws CSVBuilderException
    {
        if (censusHashMap == null || censusHashMap.size() == 0){
            throw new CSVBuilderException("Data empty", CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<Map.Entry<Integer, IndiaStateCensusDAO>> censusCSVComparator = Comparator.comparing(census -> census.getValue().state);
        LinkedHashSet<Integer, IndianStateCensusDAO> sortedByValue = this.sort(censusCSVComparator);
        censusRecord = sortedByValue.values();
        String sortedStateCensusJson = new Gson().toJson(censusRecord);
        return sortedStateCensusJson;
    }

    public String getStateCodeWiseSortedData() throws CSVBuilderException
    {
        if (censusHashMap == null || censusHashMap.size() == 0){
            throw new CSVBuilderException("Data empty", CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<Map.Entry<Integer, IndiaStateCensusDAO>> censusCSVComparator = Comparator.comparing(census -> census.getValue().stateCode);
        LinkedHashSet<Integer, IndianStateCensusDAO> sortedByValue = this.sort(censusCSVComparator);
        censusRecord = sortedByValue.values();
        String sortedStateCodeJson = new Gson().toJson(censusRecord);
        return sortedStateCodeJson;
    }

    public LinkedHashSet<Integer, IndianStateCensusDAO> sort(Comparator censusCSVComparator)
    {
        Set<Map.Entry<Integer, IndianStateCensusDAO>> entries = censusHashMap.entrySet();
        List<Map.Entry<Integer, IndianStateCensusDAO>> listOfEntries = new ArrayList<Integer, IndianStateCensusDAO>(listOfEntries.size());
        Collection.sort(listOfEntries, censusCSVComparator);
        LinkedHashSet<Integer, IndianStateCensusDAO> sortedByValue = new LinkedHashSet<Integer, IndianStateCensusDAO>(listOfEntries.size());
        //Copying entries from List to Map
        for (Map.Entry<Integer, IndianStateCensusDAO>entry : listOfEntries){
            sortedByValue.put(entry.getKey(), entry.getValue());
        }
        return sortedByValue;
    }
}
