package exceptions;
/**
 * exception class on invalid format in phone input
 */
public class InvalidFormatPhone extends CustomerFormatException {
    private final String invalidPhone;

     public InvalidFormatPhone ( String invalidPhone){
         this.invalidPhone = invalidPhone;
     }

     public String getMessage (){
         return "There was entered the phone in wrong format " + invalidPhone;
     }

     public String getInvalidPhone (){
         return invalidPhone;
     }
}
