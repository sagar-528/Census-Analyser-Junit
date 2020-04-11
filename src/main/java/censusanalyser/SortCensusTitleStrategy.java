package censusanalyser;

import censusanalyser.DAO.IndiaCensusDAO;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SortCensusTitleStrategy {

    static Map<Parameter, Comparator> sortParameterComparator = new HashMap<>();

    enum Parameter{
        STATE, POPULATION, AREA, DENSITY
    }

    public SortCensusTitleStrategy() {
    }

    public static Comparator getParameter(SortCensusTitleStrategy.Parameter parameter){
        Comparator<IndiaCensusDAO> stateComparator = Comparator.comparing(census -> census.state);
        Comparator<IndiaCensusDAO> areaComparator = Comparator.comparing(census -> census.areaInSqKm);
        Comparator<IndiaCensusDAO> populationComparator = Comparator.comparing(census -> census.population);
        Comparator<IndiaCensusDAO> densityComparator = Comparator.comparing(census -> census.densityPerSqKm);

        sortParameterComparator.put(Parameter.STATE, stateComparator);
        sortParameterComparator.put(Parameter.AREA, areaComparator);
        sortParameterComparator.put(Parameter.POPULATION, populationComparator);
        sortParameterComparator.put(Parameter.DENSITY, densityComparator);

        Comparator<IndiaCensusDAO> comparator =sortParameterComparator.get(parameter);
        return comparator;
    }
}
