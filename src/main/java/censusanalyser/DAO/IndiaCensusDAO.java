package censusanalyser.DAO;

import censusanalyser.IndiaStateCensusCSV;

public class IndiaCensusDAO {
    public String state;
    public long population;
    public long areaInSqKm;
    public int densityPerSqKm;

    public IndiaCensusDAO(IndiaStateCensusCSV indianStateCensusCSV) {
        state = indianStateCensusCSV.state;
        population = indianStateCensusCSV.population;
        areaInSqKm = indianStateCensusCSV.areaInSqKm;
        densityPerSqKm = indianStateCensusCSV.densityPerSqKm;
    }
}
