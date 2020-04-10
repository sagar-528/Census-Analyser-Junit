package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class OpenCSVBuilder implements ICSVBuilder {


    @Override
    public <T> Iterator<T> getCSVFileIterator(Reader reader, Class<T> csvClass) throws CSVBuilderException {
        return this.getCSVBean(reader,csvClass).iterator();
    }

    @Override
    public <T> List<T> getCSVFileList(Reader reader, Class<T> csvClass) throws CSVBuilderException {
        return this.getCSVBean(reader, csvClass).parse();
    }

    private <T> CsvToBean<T> getCSVBean(Reader reader, Class<T> csvClass) throws CSVBuilderException{
        try{
            CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            return csvToBeanBuilder.build();
        }catch (IllegalStateException e){
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}
