import exceptions.CustomerFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {
    private final static Logger logger = LoggerFactory.getLogger(Main.class);
    private final static String addCommand = "add Jon Smith +0578656432 jonsmith@rumbler.com";
    private final static String commandExample = "\t" + addCommand + "\n\t" + "list" + "\n\t" +
            "count" + "\n\t" + "remove jonsmith@rumbler.com";
    private static final String helpText = "Command examples:\n" + commandExample;

    public static void main(String... a) {
        logger.info("The program starts");
        Scanner scanner = new Scanner(System.in);
        CustomerStorage executor = new CustomerStorage();
        for (; ; ) {
            System.out.println(helpText);
            String nextCommand = scanner.nextLine();
            try {
                String[] token = nextCommand.split("\s", 2);
                String command = token[0];
                boolean isTwoToken = token.length == 2;
                if (command.matches("add") && isTwoToken){
                    executor.addCustomer(token[1]);
                    logger.trace("Consumer " + token[1] + " was added");
                } else if (command.matches("list")){
                    executor.showAllCustomers();
                } else if (command.matches("count")){
                    System.out.println("There are " + executor.getCustomerCount() + " customers");
                } else if (command.matches("remove") && isTwoToken){
                    System.out.println(executor.removeCustomer(token[1]));
                    logger.trace("Customer " + token[1] + " was removed from storage");
                } else {
                    System.out.println("wrong command");
                    logger.info("there was a wrong enter: " + nextCommand);
                }
            }
            catch (CustomerFormatException e){
                logger.error("Error - " + e.fillInStackTrace());
                System.out.println(e.getMessage());
            }

        }
    }
}
