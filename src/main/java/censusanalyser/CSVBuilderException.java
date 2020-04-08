package censusanalyser;

public class CSVBuilderException extends Exception {

    enum ExceptionType {
        CENSUS_FILE_PROBLEM,
        INVALID_FILE_DELIMITER, INVALID_FILE_HEADER, INVALID_FILE_TYPE, UNABLE_TO_PARSE, NO_CENSUS_DATA
    }

    ExceptionType type;
    public CSVBuilderException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
