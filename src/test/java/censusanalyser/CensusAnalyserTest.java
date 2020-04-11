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
            sortedCensusData = censusAnalyser.getStateWiseSortedData(SortByField.Parameter.STATE);
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
            sortedCensusData = censusAnalyser.getStateWiseSortedData(SortByField.Parameter.STATE);
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

    @Test
    public void givenTheStateCensusCSVFile_WhenSortedOnArea_ShouldReturnSortedList()
    {
        String sortedCensusData = null;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_CENSUS_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getStateWiseSortedData(SortByField.Parameter.AREA);
            IndiaStateCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCensusCSV[].class);
            Assert.assertEquals("Rajasthan", censusCSV[censusCSV.length - 1].state);
        }catch(CensusAnalyserException e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenSortedOnPopulation_ShouldReturnSortedList()
    {
        String sortedCensusData = null;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_CENSUS_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getStateWiseSortedData(SortByField.Parameter.POPULATION);
            IndiaStateCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSV[censusCSV.length - 1].state);
        }catch(CensusAnalyserException e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenSortedOnDensity_ShouldReturnSortedList()
    {
        String sortedCensusData = null;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_CENSUS_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getStateWiseSortedData(SortByField.Parameter.DENSITY);
            IndiaStateCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCensusCSV[].class);
            Assert.assertEquals("Bihar", censusCSV[censusCSV.length - 1].state);
        }catch(CensusAnalyserException e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusDATA_WhenSortedOnState_ShouldReturnSortedResults() {
        String sortedCensusData = null;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getStateWiseSortedData(SortByField.Parameter.STATE);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Wyoming", censusCSV[censusCSV.length - 1].State);
        }catch(CensusAnalyserException e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusDATA_WhenSortedOnPopulation_ShouldReturnSortedResults() {
        String sortedCensusData = null;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getStateWiseSortedData(SortByField.Parameter.POPULATION);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("California", censusCSV[censusCSV.length - 1].State);
        }catch(CensusAnalyserException e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusDATA_WhenSortedOnDensity_ShouldReturnSortedResults() {
        String sortedCensusData = null;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getStateWiseSortedData(SortByField.Parameter.DENSITY);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("District of Columbia", censusCSV[censusCSV.length - 1].State);
        }catch(CensusAnalyserException e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusDATA_WhenSortedOnArea_ShouldReturnSortedResults() {
        String sortedCensusData = null;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH);
            sortedCensusData = censusAnalyser.getStateWiseSortedData(SortByField.Parameter.AREA);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alaska", censusCSV[censusCSV.length - 1].State);
        }catch(CensusAnalyserException e){
            e.printStackTrace();
        }
    }
}
