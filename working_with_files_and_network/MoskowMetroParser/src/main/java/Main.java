public class Main {

    static MetroJsonCreator metroJsonCreator = new MetroJsonCreator();

    public static void main(String[] args) {
        try {
            metroJsonCreator.createMetro();
            metroJsonCreator.getInformation();
        } catch (Exception e){
            e.printStackTrace();
        }



    }
}
