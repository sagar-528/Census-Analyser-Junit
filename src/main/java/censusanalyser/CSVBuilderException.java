package censusanalyser;

public class CSVBuilderException extends Exception {

    enum ExceptionType {
        CENSUS_FILE_PROBLEM,
        INVALID_FILE_DELIMITER, INVALID_FILE_HEADER, INVALID_FILE_TYPE, UNABLE_TO_PARSE
    }

    ExceptionType type;

    public CSVBuilderException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CSVBuilderException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
