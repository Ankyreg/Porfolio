package exceptions;

/**
 * exception class on invalid format in name input
 */
public class InvalidFormatName extends CustomerFormatException {
    private String invalidName;

    public InvalidFormatName (String invalidName){
        this.invalidName = invalidName;
    }

    public String getMessage (){
        return "There was entered the first or last name in the wrong format - " + invalidName;
    }

    public String getInvalidName (){
        return invalidName;
    }



}
