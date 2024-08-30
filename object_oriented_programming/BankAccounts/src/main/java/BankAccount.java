public class BankAccount {

    private double total;

    public double getAmount() {
        return total;
    }

    public void setTotal(double account) {
        total = account;
    }

    /**
     *If the incoming argument is zero, less than zero,
     *  or greater than the value of the variable total,
     *  then the variable total does not change. Otherwise, it is subtracted
     * @param input amount
     */
    public void getMoney(double input) {
        if (input <= 0){
            System.out.println("Cancel");
        } else  if (total >= input) {
            System.out.println("Take your money");
            total -= input;
            System.out.println( total + " money left on your account");
        } else {
            System.out.println("Insufficient funds!");
        }

    }

    public void takeMoney(double input) {
        if (input <= 0) {
            System.out.println("Cancel");
        } else {
            total += input;
            System.out.println("account replenished");
        }
    }




}
