package censusanalyser;

import censusanalyser.DAO.IndiaCensusDAO;

import java.util.Comparator;

public class SortByStateStrategy implements IndianCensusData {
    @Override
    public Comparator censusFileStrategy() {
        return Comparator.<IndiaCensusDAO, Integer>comparing(census -> Integer.valueOf(census.state));
    }
}
