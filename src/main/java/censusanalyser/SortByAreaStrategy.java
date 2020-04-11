package censusanalyser;

import censusanalyser.DAO.IndiaCensusDAO;

import java.util.Comparator;

public class SortByAreaStrategy implements IndianCensusData{

    @Override
    public Comparator censusFileStrategy() {
        return Comparator.<IndiaCensusDAO, Integer>comparing(census -> Math.toIntExact(census.areaInSqKm));
    }
}
