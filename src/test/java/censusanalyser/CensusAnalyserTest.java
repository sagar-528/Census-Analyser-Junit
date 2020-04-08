package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class CensusAnalyserTest
{
    CensusAnalyser censusAnalyser = new CensusAnalyser();

    private static final String INDIA_STATE_CODE_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String WRONG_CSV_FILE_TYPE = "./src/test/resources/censusData.txt";
    private static final String INDIA_STATE_CODE_WRONG_CSV_FILE_TYPE = "./src/test/resources/codeData.txt";
    private static final String WRONG_CSV_FILE_DELIMITER = "./src/test/resources/censusInvalidDelimiter.csv";
    private static final String INDIA_STATE_CODE_WRONG_CSV_FILE_DELIMITER = "./src/test/resources/codeInvalidDelimiter.csv";
    private static final String WRONG_CSV_FILE_HEADER = "./src/test/resources/censusInvalidHeader.csv";
    private static final String INDIA_STATE_CODE_WRONG_CSV_FILE_HEADER = "./src/test/resources/codeInvalidHeader.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() throws CSVBuilderException
    {
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
    }

    @Test
    public void givenIndianStateCodeCSVFileReturnCorrectRecords() {
        try {
            int numOfStateCodes = censusAnalyser.loadIndiaStateCode(INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals(37,numOfStateCodes);
        } catch (CSVBuilderException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException()
    {
        try {
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCode_WithWrongFile_ShouldThrowException()
    {
        try {
            censusAnalyser.loadIndiaCensusData(INDIA_STATE_CODE_WRONG_CSV_FILE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileType_ShouldThrowException()
    {
        try {
        censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_TYPE);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INVALID_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenIndiaStateCode_WithWrongFileType_ShouldThrowException()
    {
        try {
            censusAnalyser.loadIndiaCensusData(INDIA_STATE_CODE_WRONG_CSV_FILE_TYPE);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INVALID_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_DELIMITER);
        } catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INVALID_FILE_DELIMITER, e.type);
        }
    }


    @Test
    public void givenIndiaStateCode_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            censusAnalyser.loadIndiaCensusData(INDIA_STATE_CODE_WRONG_CSV_FILE_DELIMITER);
        } catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INVALID_FILE_DELIMITER, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileHeader_ShouldThrowException() {
        try {
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_HEADER);
        } catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INVALID_FILE_HEADER, e.type);
        }
    }

    @Test
    public void givenIndiaStateCode_WithWrongFileHeader_ShouldThrowException() {
        try {
            censusAnalyser.loadIndiaCensusData(INDIA_STATE_CODE_WRONG_CSV_FILE_HEADER);
        } catch (CSVBuilderException e)
        {
            Assert.assertEquals(CSVBuilderException.ExceptionType.INVALID_FILE_HEADER, e.type);
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenSortedOnState_ShouldReturnSortedList() throws CSVBuilderException
    {
        censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH );
        String sortedCensusData = censusAnalyser.getStateWiseSortedData();
        IndiaStateCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCensusCSV[].class);
        Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        Assert.assertEquals("West Bengal" , censusCSV[29].state);
    }

    @Test
    public void givenTheStateCodeCSVFile_WhenSortedOnStateCode_ShouldReturnSortedList() throws CSVBuilderException
    {
        censusAnalyser.loadIndiaStateCode(INDIA_STATE_CODE_FILE_PATH);
        String sortedStateCodeData = censusAnalyser.getStateCodeWiseSortedData();
        IndiaStateCodeCSV[] censusCSV = new Gson().fromJson(sortedStateCodeData, IndiaStateCodeCSV[].class);
        Assert.assertEquals("AD", censusCSV[0].stateCode);
        Assert.assertEquals("WB" , censusCSV[36].stateCode);
    }
}
