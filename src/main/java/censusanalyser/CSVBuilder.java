package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.List;

public class CSVBuilder implements IcsvBuilder{

    @Override
    public <T> List<T> getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException {
        try{
                CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
                csvToBeanBuilder.withType(csvClass);
                csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
                CsvToBean<T> csvToBean = csvToBeanBuilder.build();
                return csvToBean.parse();
            }catch (IllegalStateException e){
                    throw new CSVBuilderException(e.getMessage(),
                        CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
                }
    }
}
