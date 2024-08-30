import com.sun.tools.javac.Main;

import java.util.Scanner;

public class Start {
    public static void main (String ... arg){
        Scanner scanner = new Scanner(System.in);
        Client client;
        for (;;){
            System.out.println("""
                    Service for individuals - click "1"\s
                    Services for legal entities - click "2"\s
                    Services for individual businessman - click "3\"""");
            String input = scanner.nextLine();
            if (input.matches("1")){
               client = new PhysicalPerson();
            } else if (input.matches("2")){
                client = new LegalPerson();
            } else if (input.matches("3")){
                client = new IndividualBusinessman();
            } else {
                System.out.println("Wrong command");
                continue;
            }
            label:
            for (;;){
                System.out.println("""
                     Enter the transaction number:\s
                     "1" - replenish the account; \s
                     "2" - withdraw funds from the account;\s
                     "3" - find out the balance;\s
                     "4" - exit to the main menu;""");
                String command = scanner.nextLine();
                switch (command) {
                    case "1": {
                        System.out.println("Enter amount");
                        String sum = scanner.nextLine();
                        client.put(Double.parseDouble(sum));
                        break;
                    }
                    case "2": {
                        System.out.println("Enter amount");
                        String sum = scanner.nextLine();
                        client.take(Double.parseDouble(sum));
                        break;
                    }
                    case "3":
                        client.getAmount();
                        break;
                    case "4":
                        break label;
                    default:
                        System.out.println("Wrong command");
                        break;
                }
            }

        }
    }
}
