package censusanalyser;

import censusanalyser.DAO.IndiaCensusDAO;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SortByField {

    Map<Parameter, Comparator> sortParameterComparator = new HashMap<>();

    public enum Parameter {

       STATE, POPULATION, AREA, DENSITY;
    }

    public SortByField() {
    }

    public Comparator getParameter(SortByField.Parameter parameter){
        Comparator<IndiaCensusDAO> stateComparator = Comparator.comparing(census -> census.state);
        Comparator<IndiaCensusDAO> areaComparator = Comparator.comparing(census -> census.areaInSqKm);
        Comparator<IndiaCensusDAO> populationComparator = Comparator.comparing(census -> census.population);
        Comparator<IndiaCensusDAO> densityComparator = Comparator.comparing(census -> census.densityPerSqKm);

        this.sortParameterComparator.put(Parameter.STATE, stateComparator);
        this.sortParameterComparator.put(Parameter.AREA, areaComparator);
        this.sortParameterComparator.put(Parameter.POPULATION, populationComparator);
        this.sortParameterComparator.put(Parameter.DENSITY, densityComparator);

        Comparator<IndiaCensusDAO> comparator = this.sortParameterComparator.get(parameter);
        return comparator;
    }
}
