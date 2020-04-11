package censusanalyser.DAO;

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
        stateCode = next.stateId;
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
}
