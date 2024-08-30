package exceptions;

/**
 class of exception on incorrect input of a string with data
 */
public class InvalidDateFormatException extends CustomerFormatException {
    private final String invalidDate;

    public InvalidDateFormatException(String invalidDate){
        this.invalidDate = invalidDate;
    }
    public String getMessage (){
        return "Invalid date format " + invalidDate;
    }
    public String getInvalidDate (){
        return invalidDate;
    }
}
