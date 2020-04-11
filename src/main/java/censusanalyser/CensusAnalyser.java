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


    public String getFieldWiseSortedData(SortByField.Parameter parameter) throws CensusAnalyserException
    {
        if (censusStateMap == null || censusStateMap.size() == 0){
            throw new CensusAnalyserException("Data empty", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        SortByField sortByField = new SortByField();
        Comparator<censusDAO> censusComparator = sortByField.getParameter(parameter);

        ArrayList censusDTOS = censusStateMap.values().stream().sorted(censusComparator)
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));

        String sortedStateCensusJson = new Gson().toJson(censusDTOS);
        return sortedStateCensusJson;
   }

   public String censusData(SortByField.Parameter parameter, SortByField.Parameter density) throws CensusAnalyserException
   {
       if (censusStateMap == null || censusStateMap.size() == 0){
           throw new CensusAnalyserException("Data empty", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
       }
       SortByField sortByField = new SortByField();
       Comparator<censusDAO> censusComparator = sortByField.getParameter(parameter).thenComparing(sortByField.getParameter(density));

       ArrayList censusDTOS = censusStateMap.values().stream().sorted(censusComparator)
               .map(censusDAO -> censusDAO.getCensusDTO(country))
               .collect(Collectors.toCollection(ArrayList::new));

       String sortedStateCensusJson = new Gson().toJson(censusDTOS);
       return sortedStateCensusJson;
   }

   private String getFieldWiseUniqueCensusData(List<censusDAO> censusDAOSData) throws CensusAnalyserException
   {
       for (int iterator = 0; iterator < censusDAOSData.size(); iterator++){
           for (int innerIterator = iterator + 1; innerIterator < censusDAOSData.size(); innerIterator++){
               if ((censusDAOSData.get(iterator).population) == (censusDAOSData.get(innerIterator).population))
               {
                   if ((censusDAOSData.get(iterator).populationDensity) < (censusDAOSData.get(iterator + 1).populationDensity))
                   {
                       censusDAO censusDAOTemp = censusDAOSData.get(iterator);
                       censusDAOSData.add(iterator, censusDAOSData.get(iterator + 1));
                       censusDAOSData.add(iterator + 1, censusDAOTemp);
                   }
               }
           }
       }
       String sortedStateCensusJson = new Gson().toJson(censusDAOSData);
       return sortedStateCensusJson;
   }
}
