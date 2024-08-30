import java.util.Locale;

public class Person {

    private String name;
    private String surname;
    private String number;
    private String country;


    public void setCountry(String country) {
        this.country = country;
    }
    public String getCountry() {
        return country;
    }

     public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getNumber() {
        return number;
    }

    public String toString(){
         return "Name: ".concat(name).concat("\n").concat
                 ("Surname: ").concat(surname).concat("\n").concat
                 ("Phone: ").concat(number).concat("\n").concat("Region: ").concat(getCountry());
    }

}
