public class LegalPerson extends Client{
    @Override
    public void take(double input) {
        System.out.println("The commission fee is 1%");
        double commission = input * 0.01;
        super.take(input + commission);

    }

}
