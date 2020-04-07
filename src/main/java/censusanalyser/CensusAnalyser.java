package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class CensusAnalyser {
    IcsvBuilder csvBuilder = new CSVBuilderFactory().createCSVBuilder();
    public int loadIndiaCensusData(String csvFilePath) throws CSVBuilderException
    {
        this.checkValidCSVFile(csvFilePath);
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath)))
        {
            List<IndiaStateCensusCSV> csvRecords = csvBuilder.getCSVFileList(reader, IndiaStateCensusCSV.class);
            return csvRecords.size();
        }catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e)
        {
            if (e.getMessage().contains("header!"))
                throw new CSVBuilderException(e.getMessage(),
                        CSVBuilderException.ExceptionType.INVALID_FILE_HEADER);
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.INVALID_FILE_DELIMITER);
        }
    }

    private void checkValidCSVFile(String csvFilePath) throws CSVBuilderException
    {
        if(!csvFilePath.contains(".csv"))
        {
            throw new CSVBuilderException("Invalid file type",
                    CSVBuilderException.ExceptionType.INVALID_FILE_TYPE);
        }
    }

    public int loadIndiaStateCode(String csvFilePath) throws CSVBuilderException
    {
        this.checkValidCSVFile(csvFilePath);
        try( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath)))
        {
            List<IndiaStateCodeCSV> csvRecords = csvBuilder.getCSVFileList(reader,IndiaStateCodeCSV.class);
            return csvRecords.size();
        }catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e)
        {
            if (e.getMessage().contains("header!"))
                throw new CSVBuilderException(e.getMessage(),
                        CSVBuilderException.ExceptionType.INVALID_FILE_HEADER);
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.INVALID_FILE_DELIMITER);
        }
    }
}
