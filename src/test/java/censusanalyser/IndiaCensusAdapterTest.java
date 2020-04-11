package censusanalyser;

import censusanalyser.DAO.censusDAO;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class IndiaCensusAdapterTest {

    private static final String INDIA_STATE_CODE_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_CENSUS_WRONG_DELIMITER = "./src/test/resources/censusInvalidDelimiter.csv";
    private static final String INDIA_CENSUS_CSV_MISSING_HEADER = "./src/test/resources/censusInvalidHeader.csv";
    private static final String INDIA_CENSUS_EMPTY_FILE = "./src/test/resources/EmptyCsv.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusFile.csv";


    @Test
    public void givenIndianCensus_ThroughCensusAdapter_ReturnsCorrectRecords()
    {
        CensusAdapter censusAdapter = new IndiaCensusAdapter();
        try {
             Map<String, censusDAO> censusDAOMap = censusAdapter.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals(29, censusDAOMap.size());
        }catch (CensusAnalyserException e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenWrongDelimiter_InIndiaCensusData_ReturnsCorrectRecords()
    {
        CensusAdapter censusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, censusDAO> censusDAOMap = censusAdapter.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_WRONG_DELIMITER, INDIA_CENSUS_CSV_FILE_PATH);
        }catch (CensusAnalyserException e){
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES, e.type);
        }
    }

    @Test
    public void givenWrongHeader_InIndiaCensusData_ReturnsCorrectRecords()
    {
        CensusAdapter censusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, censusDAO> censusDAOMap = censusAdapter.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_MISSING_HEADER, INDIA_CENSUS_CSV_FILE_PATH);
        }catch (CensusAnalyserException e){
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES, e.type);
        }
    }

    @Test
    public void givenEmptyCsvFile_ReturnsCustomExceptionType()
    {
        CensusAdapter censusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, censusDAO> censusDAOMap = censusAdapter.loadCensusData(CensusAnalyser.Country.INDIA, WRONG_CSV_FILE_PATH, INDIA_CENSUS_CSV_FILE_PATH);
        }catch (CensusAnalyserException e){
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenData_WhenReverseSortedByAreaUsingInterface_ShouldReturnSortedOutput(){
        try{
            CensusAdapter censusAdapter = new IndiaCensusAdapter();
            Map<String, censusDAO> censusDAOMap = new CensusAdapterFactory().censusFactory(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_FILE_PATH);
            CensusAnalyser censusAnalyser = new CensusAnalyser(censusDAOMap);
            String sortedCensusData = censusAnalyser.getStateWiseSortedData(INDIA_CENSUS_CSV_FILE_PATH);
            censusDAO[] CsvDataList = new Gson().fromJson(sortedCensusData, censusDAO[].class);
            Assert.assertEquals("Rajasthan", CsvDataList[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}
