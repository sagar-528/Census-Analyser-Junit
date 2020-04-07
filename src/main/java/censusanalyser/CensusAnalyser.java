package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;


public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException
    {
        if(!csvFilePath.contains(".csv"))
        {
            throw new CensusAnalyserException("Invalid file type",
                    CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        }

        try{
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            Iterator<IndiaCensusCSV> cencusCSVIterator = this.getCSVFileIterator(reader,IndiaCensusCSV.class);
            return this.getCount(cencusCSVIterator);
        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e)
        {
            if (e.getMessage().contains("header!"))
                throw new CensusAnalyserException(e.getMessage(),
                        CensusAnalyserException.ExceptionType.INVALID_FILE_HEADER);
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_DELIMITER);
        }
    }

    public int loadIndiaStateCode(String csvFilePath) throws CensusAnalyserException
    {
        if(!csvFilePath.contains(".csv"))
        {
            throw new CensusAnalyserException("Invalid file type",
                    CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        }
        try{
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            Iterator<IndiaStateCodeCSV> censusCSVIterator = this.getCSVFileIterator(reader,IndiaStateCodeCSV.class);
         return this.getCount(censusCSVIterator);
        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e)
        {
            if (e.getMessage().contains("header!"))
                throw new CensusAnalyserException(e.getMessage(),
                        CensusAnalyserException.ExceptionType.INVALID_FILE_HEADER);
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_DELIMITER);
        }
    }

    private <T> Iterator<T> getCSVFileIterator(Reader reader , Class<T> csvClass) throws CensusAnalyserException{
        try{
            CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<T> csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        }catch (IllegalStateException e){
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    private <T> int getCount(Iterator<T> iterator){
        Iterable<T> iterable = () -> iterator;
        int numOfEnteries = (int)StreamSupport.stream(iterable.spliterator(), false).count();
        return numOfEnteries;
    }
}
