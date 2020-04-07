package censusanalyser;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder implements IcsvBuilder{

    public <T> Iterator<T> getCSVFileIterator(Reader reader, Class<T> csvClass) throws CSVBuilderException {
        try{
            CSVReader csvReader = new CSVReader(reader);
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader).withType(csvClass).build();
            return csvToBean.iterator();
        }catch (IllegalStateException e){
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    @Override
    public <T> int getCount(Iterator<T> csvRecords)
    {
        int noOfRecords = 0;
        while(csvRecords.hasNext()){
            noOfRecords++;
            csvRecords.next();
        }
        return noOfRecords;
    }
}
