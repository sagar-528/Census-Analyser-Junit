package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class CensusAnalyserTest
{
    CensusAnalyser censusAnalyser = new CensusAnalyser();

    private static final String INDIA_STATE_CODE_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_CENSUS_WRONG_DELIMITER = "./src/test/resources/censusInvalidDelimiter.csv";
    private static final String INDIA_CENSUS_CSV_MISSING_HEADER = "./src/test/resources/censusInvalidHeader.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusFile.csv";

    @Test
    public void givenIndianCensus_CSVFile_ReturnsCorrectRecords()
    {
        try {
            int numOfRecord = censusAnalyser.loadStateCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecord);
        }catch (CensusAnalyserException e){  }
    }

    @Test
    public void givenIndiaCensusData_WithWrongDelimiter_ShouldThrowCustomExceptionType()
    {
        try {
            censusAnalyser.loadStateCensusData(INDIA_CENSUS_WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithMissingHeader_ShouldThrowCustomExceptionType()
    {
        try {
            censusAnalyser.loadStateCensusData(INDIA_CENSUS_CSV_MISSING_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES,e.type);
        }
    }

    @Test
    public void givenIndianStateCode_CSVFile_ReturnCorrectRecords()
    {
        try {
            int numOfStateCodes = censusAnalyser.loadStateCode(INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals(37,numOfStateCodes);
        } catch (CensusAnalyserException e)
        {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException()
    {
        try {
            censusAnalyser.loadStateCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenSortedOnState_ShouldReturnSortedList()
    {
        String sortedCensusData = null;
        try {
            sortedCensusData = censusAnalyser.getStateWiseSortedData(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaStateCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        }catch(CensusAnalyserException e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenTheStateCensusCSVFile_WhenSortedOnWrongState_ShouldReturnWrongSortedList()
    {
        String sortedCensusData = null;
        try {
            sortedCensusData = censusAnalyser.getStateWiseSortedData(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaStateCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCensusCSV[].class);
            Assert.assertNotEquals("Goa", censusCSV[0].state);
        }catch(CensusAnalyserException e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCENSUSDATA_ShouldReturnCorrectRecords() {
        int data = 0;
        try {
            data = censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, data);
        }catch (CensusAnalyserException e){
            e.printStackTrace();
        }
    }
}
