package censusanalyser;

import censusanalyser.DAO.censusDAO;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter {

    public Map<String, censusDAO> loadCensusData(CensusAnalyser.Country country, String... csvFilePath) throws CensusAnalyserException
    {
        return this.loadCensusData(USCensusCSV.class, csvFilePath[0]);
    }
}
