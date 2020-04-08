package censusanalyser;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;


public class CensusAnalyser {
    IcsvBuilder csvBuilder = new CSVBuilderFactory().createCSVBuilder();
    List<IndiaStateCensusCSV> stateCensusRecord = null;
    List<IndiaStateCodeCSV> stateCodeRecords = null;

    public int loadIndiaCensusData(String csvFilePath) throws CSVBuilderException
    {
        this.checkValidCSVFile(csvFilePath);
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            stateCensusRecord = csvBuilder.getCSVFileList(reader, IndiaStateCensusCSV.class);
            return stateCensusRecord.size();
        }catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e) {
            if (e.getMessage().contains("header!"))
                throw new CSVBuilderException(e.getMessage(),
                        CSVBuilderException.ExceptionType.INVALID_FILE_HEADER);
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.INVALID_FILE_DELIMITER);
        }
    }

    private void checkValidCSVFile(String csvFilePath) throws CSVBuilderException
    {
        if(!csvFilePath.contains(".csv")) {
            throw new CSVBuilderException("Invalid file type",
                    CSVBuilderException.ExceptionType.INVALID_FILE_TYPE);
        }
    }

    public int loadIndiaStateCode(String csvFilePath) throws CSVBuilderException
    {
        this.checkValidCSVFile(csvFilePath);
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath)))
        {
            stateCodeRecords = csvBuilder.getCSVFileList(reader,IndiaStateCodeCSV.class);
            return stateCodeRecords.size();
        }catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e) {
            if (e.getMessage().contains("header!"))
                throw new CSVBuilderException(e.getMessage(),
                        CSVBuilderException.ExceptionType.INVALID_FILE_HEADER);
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.INVALID_FILE_DELIMITER);
        }
    }

    public String getStateWiseSortedData() throws CSVBuilderException
    {
        if (stateCensusRecord == null || stateCodeRecords == null){
            throw new CSVBuilderException("Data empty", CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaStateCensusCSV> censusCSVComparator = Comparator.comparing(indiaStateCensusCSV -> indiaStateCensusCSV.getState());
        this.sort(censusCSVComparator);
        String sortedStateCensusJson = new Gson().toJson(stateCensusRecord);
        return sortedStateCensusJson;
    }

    public void sort(Comparator<IndiaStateCensusCSV> censusCSVComparator)
    {
        for (int iterator = 0; iterator < stateCensusRecord.size()-1; iterator++){
            for (int inneriterator = 0; inneriterator < stateCensusRecord.size()-iterator; inneriterator++){
                IndiaStateCensusCSV census1 = stateCensusRecord.get(inneriterator);
                IndiaStateCensusCSV census2 = stateCensusRecord.get(inneriterator+1);
                if (censusCSVComparator.compare(census1,census2) > 0){
                    stateCensusRecord.set(inneriterator, census2);
                    stateCensusRecord.set(inneriterator+1, census1);
                }
            }
        }
    }
}
