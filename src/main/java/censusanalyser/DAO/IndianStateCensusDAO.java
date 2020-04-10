package censusanalyser.DAO;

import censusanalyser.IndiaStateCensusCSV;
import censusanalyser.IndiaStateCodeCSV;

public class IndianStateCensusDAO {
    public String state;
    public long population;
    public long areaInSqKm;
    public int densityPerSqKm;

    public IndianStateCensusDAO(IndiaStateCensusCSV indianStateCensusCSV) {
        state = indianStateCensusCSV.state;
        population = indianStateCensusCSV.population;
        areaInSqKm = indianStateCensusCSV.areaInSqKm;
        densityPerSqKm = indianStateCensusCSV.densityPerSqKm;
    }
}
