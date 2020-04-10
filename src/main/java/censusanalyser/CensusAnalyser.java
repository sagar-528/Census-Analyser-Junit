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


    public int loadCensusData(String csvFilePath, Class csvClass) throws CSVBuilderException, IOException
    {
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            Iterator<IndianStateCensusCSV> csvIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCensusCSV.class);
            Integer count = 0;
            while (csvFileIterator.hasNext()){

            }
            censusHashMap = csvBuilder.getCSVFileMap(reader, csvClass);
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
        Comparator<IndiaStateCensusCSV> censusCSVComparator = Comparator.comparing(IndiaStateCensusDAO -> IndiaStateCensusDAO.state);
        this.sort(censusCSVComparator, censusHashMap);
        censusRecord = censusHashMap.values();
        String sortedStateCensusJson = new Gson().toJson(censusRecord);
        return sortedStateCensusJson;
    }

    public String getStateCodeWiseSortedData() throws CSVBuilderException
    {
        if (censusHashMap == null || censusHashMap.size() == 0){
            throw new CSVBuilderException("Data empty", CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaStateCodDAO> codeCSVComparator = Comparator.comparing(IndiaStateCodeDAO -> IndiaStateCodeDAO.stateCode);
        this.sort(codeCSVComparator, censusHashMap);
        censusRecord = censusHashMap.values();
        String sortedStateCodeJson = new Gson().toJson(censusHashMap);
        return sortedStateCodeJson;
    }

    public <T> void  sort(Comparator<T> censusCSVComparator, HashMap<Object, Object> CensusRecord)
    {
        for (int iterator = 0; iterator < CensusRecord.size()-1; iterator++){
            for (int inneriterator = 0; inneriterator < CensusRecord.size()-iterator - 1; inneriterator++){
                T census1 = (T) CensusRecord.get(inneriterator);
                T census2 = (T) CensusRecord.get(inneriterator+1);
                if (censusCSVComparator.compare(census1,census2) > 0){
                    CensusRecord.put(inneriterator, census2);
                    CensusRecord.put(inneriterator+1, census1);
                }
            }
        }
    }

}
