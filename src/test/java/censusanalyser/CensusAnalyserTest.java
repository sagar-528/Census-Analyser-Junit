package censusanalyser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest
{

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_TYPE = "./src/test/resources/censusData.txt";
    private static final String WRONG_CSV_FILE_DELIMITER = "./src/test/resources/censusInvalidDelimiter.csv";
    private static final String WRONG_CSV_FILE_HEADER = "./src/test/resources/censusInvalidHeader.csv";
    private static final String INDIA_STATE_CODE_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords()
    {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndianStateCodeCSVFileReturnCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfStateCodes = censusAnalyser.loadIndiaCensusData(INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals(37,numOfStateCodes);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException()
    {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileType_ShouldThrowException()
    {
        try {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_DELIMITER);
        } catch (CensusAnalyserException e)
        {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_DELIMITER, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_HEADER);
        } catch (CensusAnalyserException e)
        {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_HEADER, e.type);
        }
    }
}
