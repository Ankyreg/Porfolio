public abstract class Client {
    private double amount;

    private double getCheckingAccount() {
        return amount;
    }

    public double getAmount(){
        System.out.println("On your account " + getCheckingAccount() + " money.");
        return amount;
    }

    public void take(double input) {
        if (amount >= input) {
            amount = amount - input;
            System.out.println("Take your money");
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    public void put(double input) {
        if (input < 1) {
            System.out.println("wrong input");
        } else {
            amount = amount + input;
            System.out.println("Account replenished");
        }
    }
}
