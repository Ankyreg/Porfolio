import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    public static void main (String ... arg){


        while (true){
            System.out.println("Select an account: 1 - bank account. 2 - depositary account. 3 - card account");
            String command = scanner.nextLine();
            BankAccount account = getBankAccount(command);
            if (account == null){
                System.out.println("Wrong command");
                break;
            } else {
                doWork(account);
            }
        }
    }

    /**
     *Depending on the user input, the further action with the previously selected account
     * {@link #getBankAccount(String)} is determined: debit, top up the account, checking the account balance
     * @param account
     */
    private static void doWork(BankAccount account) {
        String commandNext;
        label:
        while (true){
            System.out.println("To replenish the account, enter \"1\", to receive money, enter \"2\", " +
                    "for information, enter \"3\". To change the account \"4\"");
            commandNext = scanner.nextLine();
            String amount;
            switch (commandNext) {
                case "1":
                    System.out.println("Enter amount");
                    amount = scanner.nextLine();
                    account.takeMoney(checkAmount(amount));
                    break;
                case "2":
                    System.out.println("how much you want to withdraw");
                    amount = scanner.nextLine();
                    account.getMoney(checkAmount(amount));
                    break;
                case "3":
                    System.out.println(account.getAmount() + " money left on your account");
                    break;
                case "4":
                    break label;
                default:
                    System.out.println("wrong command");
                    break;
            }
        }
    }
    //check if the input corresponds to a number double or int
    private static double checkAmount (String amount){
        if (amount.matches("\\d+\\.\\d+") || amount.matches("\\d+")){
           return Double.parseDouble(amount);
        } else return 0;
    }

    /**
     * Depending on the user input, it is determined what type of class is initialized
     * @param command
     * @return
     */
    private static BankAccount getBankAccount (String command){
        BankAccount account = null;
        if (command.equals("2")){
            account = new DepositAccount();
            System.out.println("depositary account");
        }
        if (command.equals("3")){
            account = new CardAccount();
            System.out.println("card account");
        }
        if (command.equals("1")){
            account = new BankAccount();
            System.out.println("bank account");
        }
        return account;
    }
}
