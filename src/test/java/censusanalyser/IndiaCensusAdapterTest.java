package censusanalyser;

import censusanalyser.DAO.censusDAO;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class IndiaCensusAdapterTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_CSV_STATE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String INDIA_CENSUS_WRONG_DELIMITER = "./src/test/resources/censusInvalidDelimiter.csv";
    private static final String INDIA_CENSUS_CSV_MISSING_HEADER = "./src/test/resources/censusInvalidHeader.csv";
    private static final String INDIA_CENSUS_EMPTY_FILE = "./src/test/resources/EmptyCsv.csv";

    @Test
    public void givenIndiaCensusData_ThroughCensusAdapter_ReturnsCorrectRecords()
    {
        CensusAdapter censusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, censusDAO> censusDAOMap = censusAdapter.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_CSV_STATE_PATH);
            Assert.assertEquals(29, censusDAOMap.size());
        }catch (CensusAnalyserException e){
            e.printStackTrace();
        }
    }

    @Test
    public void givenWrongDelimiter_ThroughCensusAdapter_ShouldReturnsCustomExceptionType()
    {
        CensusAdapter censusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, censusDAO> censusDAOMap = censusAdapter.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_WRONG_DELIMITER, INDIA_CENSUS_CSV_FILE_PATH);
        }catch (CensusAnalyserException e){
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES, e.type);
        }
    }

    @Test
    public void givenMissingHeader_ThroughCensusAdapter_ShouldReturnsCustomExceptionType()
    {
        CensusAdapter censusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, censusDAO> censusDAOMap = censusAdapter.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_MISSING_HEADER, INDIA_CENSUS_CSV_FILE_PATH);
        }catch (CensusAnalyserException e){
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES, e.type);
        }
    }

    @Test
    public void givenEmptyCSVFile_ThroughCensusAdapter_ShouldReturnsCustomExceptionType()
    {
        CensusAdapter censusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, censusDAO> censusDAOMap = censusAdapter.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_EMPTY_FILE, INDIA_CENSUS_CSV_FILE_PATH);
        }catch (CensusAnalyserException e){
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_INTERNAL_ISSUES, e.type);
        }
    }
}
