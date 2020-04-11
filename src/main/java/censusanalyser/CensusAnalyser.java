package censusanalyser;

import censusanalyser.DAO.censusDAO;
import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;


public class CensusAnalyser {

    List<censusDAO> csvFileList;
    public Country country;

    public CensusAnalyser(Country country) {
        this.country = country;
    }

    public CensusAnalyser(Map<String, censusDAO> censusDAOMap) {
        this.censusStateMap = new HashMap<>();
    }

    public enum Country {INDIA, US}
    Map<String, censusDAO> censusStateMap;

    public CensusAnalyser()
    {
        this.censusStateMap = new HashMap<String, censusDAO>();
    }

    public int loadCensusData(String... csvFilePath) throws CensusAnalyserException{
        censusStateMap = new CensusAdapterFactory().censusFactory (country, csvFilePath);
        return censusStateMap.size();
    }


    public String getStateWiseSortedData(String csvFilePath) throws CensusAnalyserException
    {
        if (censusStateMap == null || censusStateMap.size() == 0){
            throw new CensusAnalyserException("Data empty", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<censusDAO> censusComparator = Comparator.comparing(census -> census.state);

        ArrayList censusDTOS = censusStateMap.values().stream().sorted(censusComparator)
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));

        String sortedStateCensusJson = new Gson().toJson(censusDTOS);
        return sortedStateCensusJson;
   }
}
