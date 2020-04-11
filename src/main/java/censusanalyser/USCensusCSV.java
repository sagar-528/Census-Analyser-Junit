package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {
    @CsvBindByName(column = "State", required = true)
    public String State;

    @CsvBindByName(column = "State Id", required = true)
    public String StateId;

    @CsvBindByName(column = "Population", required = true)
    public int Population;

    @CsvBindByName(column = "Total area", required = true)
    public Double totalArea;

    @CsvBindByName(column = "Population Density", required = true)
    public Double populationDensity;

    public USCensusCSV(String state, String stateId, int population, double populationDensity, double totalArea) {
        State = state;
        StateId = stateId;
        Population = population;
        this.populationDensity = populationDensity;
        this.totalArea = totalArea;

    }

    @Override
    public String toString() {
        return "USCensusCSV{" +
                "State='" + State + '\'' +
                ", stateId='" + StateId + '\'' +
                ", Population=" + Population +
                ", totalArea=" + totalArea +
                ", populationDensity=" + populationDensity +
                '}';
    }
}
