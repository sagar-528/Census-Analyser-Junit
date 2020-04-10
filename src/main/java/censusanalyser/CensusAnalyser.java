package censusanalyser;

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
    Collection<Object> stateCensusRecord = null;
    Collection<Object> sateCodeRecords = null;
    HashMap<Object, Object> stateCodeHashMap = null;
    HashMap<Object, Object> stateCensusHashMap = null;

    public int loadIndiaCensusData(String csvFilePath) throws CSVBuilderException, IOException
    {
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            stateCensusHashMap = csvBuilder.getCSVFileMap(reader, IndiaStateCensusCSV.class);
            return stateCensusHashMap.size();
        }catch (NoSuchFileException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.INVALID_FILE_DELIMITER);
        }
    }

    public int loadIndiaStateCode(String csvFilePath) throws CSVBuilderException, IOException
    {
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath)))
        {
            stateCodeHashMap = csvBuilder.getCSVFileMap(reader, IndiaStateCodeCSV.class);
            return stateCodeHashMap.size();
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
        if (stateCensusHashMap == null || stateCensusHashMap.size() == 0){
            throw new CSVBuilderException("Data empty", CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaStateCensusCSV> censusCSVComparator = Comparator.comparing(IndiaStateCensusCSV -> IndiaStateCensusCSV.state);
        this.sort(censusCSVComparator, stateCensusHashMap);
        stateCensusHashMap = stateCensusHashMap.values();
        String sortedStateCensusJson = new Gson().toJson(stateCensusRecord);
        return sortedStateCensusJson;
    }

    public String getStateCodeWiseSortedData() throws CSVBuilderException
    {
        if (stateCodeHashMap == null || stateCodeHashMap.size() == 0){
            throw new CSVBuilderException("Data empty", CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaStateCodeCSV> codeCSVComparator = Comparator.comparing(IndiaStateCodeCSV -> IndiaStateCodeCSV.stateCode);
        this.sort(codeCSVComparator, stateCodeHashMap);
        stateCodeHashMap = (HashMap<Object, Object>) stateCodeHashMap.values();
        String sortedStateCodeJson = new Gson().toJson(stateCodeHashMap);
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
