package censusanalyser;

import censusanalyser.DAO.IndiaCensusDAO;

import java.util.Comparator;

public class SortByDensityStrategy implements IndianCensusData {
    @Override
    public Comparator censusFileStrategy() {
        return Comparator.<IndiaCensusDAO, Integer>comparing(census -> census.densityPerSqKm);
    }
}
