package censusanalyser.DAO;

import censusanalyser.IndiaStateCensusCSV;

public class IndianStateCensusDAO {
    public String state;
    public long population;
    public long areaInSqKm;
    public int densityPerSqKm;

    public IndianStateCensusDAO(IndiaStateCensusCSV indianStateCensusCSV) {
        this.state = state;
        this.population = population;
        this.areaInSqKm = areaInSqKm;
        this.densityPerSqKm = densityPerSqKm;
    }
}
