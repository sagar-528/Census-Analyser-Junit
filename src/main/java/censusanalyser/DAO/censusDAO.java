package censusanalyser.DAO;

import censusanalyser.CensusAnalyser;
import censusanalyser.IndiaStateCensusCSV;
import censusanalyser.USCensusCSV;

public class censusDAO {

    public String state;
    public String stateCode;
    public int population;
    public double totalArea;
    public double populationDensity;

    public censusDAO(USCensusCSV next) {
        state = next.State;
        stateCode = next.StateId;
        population = next.Population;
        totalArea = next.totalArea;
        populationDensity = next.populationDensity;
    }

    public censusDAO(IndiaStateCensusCSV next) {
        state = next.state;
        population = next.population;
        totalArea = next.areaInSqKm;
        populationDensity = next.densityPerSqKm;
    }

    public Object getCensusDTO(CensusAnalyser.Country country){
        if (country.equals(CensusAnalyser.Country.US))
            return new USCensusCSV(state, stateCode, population, populationDensity, totalArea);
            return new IndiaStateCensusCSV(state, population, (int) populationDensity, (int) totalArea);
    }
}
