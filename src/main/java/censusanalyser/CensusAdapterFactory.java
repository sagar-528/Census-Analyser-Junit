package censusanalyser;

import censusanalyser.DAO.censusDAO;

import java.util.Map;

public class CensusAdapterFactory {

    public Map<String, censusDAO> censusFactory(CensusAnalyser.Country country, String... csvFilePath) throws CensusAnalyserException{
        if (country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusAdapter().loadCensusData(country, csvFilePath);
        else if (country.equals(CensusAnalyser.Country.US))
            return new USCensusAdapter().loadCensusData(country, csvFilePath);
        return null;
    }
}
