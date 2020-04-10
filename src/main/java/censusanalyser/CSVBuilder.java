package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;

public class CSVBuilder implements IcsvBuilder{

    @Override
    public <T> HashMap<T> getCSVFileMap(Reader reader, Class csvClass) throws CSVBuilderException {
        try{
                CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
                csvToBeanBuilder.withType(csvClass);
                csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
                CsvToBean<T> csvToBean = csvToBeanBuilder.build();
                List list = csvToBean.parse();
                HashMap<Integer, Object> map = new HashMap<>();
                for (Object record.list){
                    map.put(count, record);
                    count++;
            }
                return (HashMap<T, T>) map;
            }catch (IllegalStateException e){
                    throw new CSVBuilderException(e.getMessage(),
                        CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
                }
    }
}
