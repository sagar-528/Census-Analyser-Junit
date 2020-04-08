package censusanalyser;

import java.io.Reader;
import java.util.List;

public interface IcsvBuilder {
   public  <T> List<T> getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException;
}
