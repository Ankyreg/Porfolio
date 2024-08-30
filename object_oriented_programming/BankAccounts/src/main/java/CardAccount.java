public class CardAccount extends BankAccount{
    /**
     * the percentage of the incoming argument is calculated.
     * The argument and 1% of the argument are subtracted from the total parameter.
     * @param input amount
     */
    public void getMoney(double input) {
        if (input == 0){
            System.out.println("Cancel");
        } else {
            double commission = 0.01D;
            double temp = input * commission;
            System.out.println("Attention! Commission: " + temp + " money");
            if (this.getAmount() >= input + temp) {
                System.out.println("Take your money");
                this.setTotal(this.getAmount() - input - temp);
                System.out.println(this.getAmount() + " money left on your account");
            } else {
                System.out.println("Insufficient funds!");
            }
        }
    }

}
