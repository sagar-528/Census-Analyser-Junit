package censusanalyser.DAO;

import censusanalyser.IndiaStateCensusCSV;
import censusanalyser.IndiaStateCodeCSV;

public class IndianStateCensusDAO {
    public String state;
    public String stateCode;
    public int srNo;
    public int tin;
    public long population;
    public long areaInSqKm;
    public int densityPerSqKm;

    public IndianStateCensusDAO(IndiaStateCodeCSV indiaStateCodeCSV){
        this.srNo = indiaStateCodeCSV.srNo;
        this.state = indiaStateCodeCSV.stateName;
        this.tin = indiaStateCodeCSV.tin;
        this.stateCode = indiaStateCodeCSV.stateCode;
    }
    public IndianStateCensusDAO(IndiaStateCensusCSV indianStateCensusCSV) {
        this.state = indianStateCensusCSV.state;
        this.population = indianStateCensusCSV.population;
        this.areaInSqKm = indianStateCensusCSV.areaInSqKm;
        this.densityPerSqKm = indianStateCensusCSV.densityPerSqKm;
    }
}
