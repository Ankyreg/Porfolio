
public class Main {
    public static void main(String... a) {

        Formatter formatter = new Formatter();
        try {
            Person person = formatter.personCreator(new Person());
            System.out.println(person.toString());
        } catch (Exception e ){
            System.out.println("failed to create a properly formatted object: " + e.getMessage());
        }
    }
}