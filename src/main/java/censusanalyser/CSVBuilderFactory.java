package censusanalyser;

import Com.Bridgelabz.ICSVBuilder;
import Com.Bridgelabz.OpenCSVBuilder;

public class CSVBuilderFactory {
    public static ICSVBuilder createCSVBuilder(){
        return new OpenCSVBuilder();
    }
}
