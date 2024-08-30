package exceptions;
/**
 * exception class on invalid format in an email input
 */

public class InvalidFormatEmail extends CustomerFormatException {
    private String invalidEmail;

    public InvalidFormatEmail (String invalidEmail){
        this.invalidEmail = invalidEmail;
    }

    public String getMessage (){
        return "Invalid format email: " + invalidEmail;
    }

    public String getInvalidEmail(){
        return invalidEmail;
    }
}
