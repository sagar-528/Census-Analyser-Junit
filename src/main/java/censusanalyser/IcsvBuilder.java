package censusanalyser;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;

public interface IcsvBuilder {
   public  <T> HashMap<T, T> getCSVFileMap(Reader reader, Class csvClass) throws CSVBuilderException;
}
