import java.util.Calendar;
import java.util.Date;

public class DepositAccount extends BankAccount{
    private  Calendar lastIncome = Calendar.getInstance();
    private final Calendar now = Calendar.getInstance();

    /**
     * The method increments the variable total and simultaneously sets the date
     * {@link #lastIncome} by adding one month to the current date
     * @param input amount
     */

    public void takeMoney(double input) {
        if (input <= 0) {
            System.out.println("Cancel");
        } else {
            this.setTotal(this.getAmount() + input);
            Date date = new Date();
            lastIncome.setTime(date);
            lastIncome.add(Calendar.MONTH, 1);
            System.out.println("Done!");
        }
    }
/**
 *The method of debiting the account. Compares the current date to
 * {@link #lastIncome} and if it less than a month has passed since the deposit
 * was made, the variable total {@link #getAmount()} does not change. Otherwise, total is decremented.
 */
    public void getMoney(double input) {
        if (input == 0) {
            System.out.println("Cancel");
        } else {
            Date date = new Date();
            now.setTime(date);
            if (now.before(lastIncome)) {
                System.out.println("Funds not yet available for withdrawal. You can do it " + lastIncome.getTime());
            } else if (this.getAmount() >= input) {
                System.out.println("Take your money");
                this.setTotal(this.getAmount() - input);
                System.out.println(this.getAmount() + " money left on your account");
            } else {
                System.out.println("Insufficient funds!");
            }

        }
    }

}
