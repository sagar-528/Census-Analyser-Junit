package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest
{

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_CENSUS_WRONG_DELIMITER = "./src/test/resources/censusInvalidDelimiter.csv";
    private static final String INDIA_CENSUS_CSV_MISSING_HEADER = "./src/test/resources/censusInvalidHeader.csv";
    private static final String INDIA_CENSUS_EMPTY_FILE = "./src/test/resources/EmptyCsv.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusFile.csv";

    @Test
    public void givenIndianCensus_CSVFile_ReturnsCorrectRecords()
    {
        int numOfRecord = 0;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            numOfRecord = censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecord);
        }catch (CensusAnalyserException e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenWrongDelimiter_InIndiaCensusData_ShouldThrowCustomExceptionType()
    {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_WRONG_DELIMITER, INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES,e.type);
        }
    }

    @Test
    public void givenMissingHeader_InIndiaCensusData_ShouldThrowCustomExceptionType()
    {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_MISSING_HEADER, INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES,e.type);
        }
    }

    @Test
    public void givenEmptyCsvFile_ShouldThrowCustomExceptionType()
    {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_EMPTY_FILE , INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException()
    {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            censusAnalyser.loadCensusData(WRONG_CSV_FILE_PATH, INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenSortedOnWrongState_ShouldReturnWrongSortedList()
    {
        String sortedCensusData = null;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            sortedCensusData = censusAnalyser.getStateWiseSortedData(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaStateCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCensusCSV[].class);
            Assert.assertNotEquals("Goa", censusCSV[0].state);
        }catch(CensusAnalyserException e){
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenSortedOnState_ShouldReturnSortedList()
    {
        String sortedCensusData = null;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_CENSUS_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getStateWiseSortedData(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaStateCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        }catch(CensusAnalyserException e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusDATA_ShouldReturnCorrectRecords() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
        int data = 0;
        try {
            data = censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, data);
        }catch (CensusAnalyserException e){
            e.printStackTrace();
        }
    }
}
