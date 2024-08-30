public class IndividualBusinessman extends Client{
    private double commission;

    /**
     * replenishment with a commission of 1% if the amount is less
     * than 1,000 money. And replenishment with a commission of 0.5%
     * if the amount is greater than or equal to 1,000 money.
     * @param input
     */
    @Override
    public void put(double input) {
        if (input < 1000 && input > 0) {
            commission = 0.01;
            System.out.println("The commission fee is 1%");
        }
        if (input >= 1000) {
            commission = 0.005;
            System.out.println("The commission fee is 0.5%");
        }
        double percent = input * commission;
        super.put( input - percent);
        System.out.println("The commission fee is " + percent + " money");
    }
}
