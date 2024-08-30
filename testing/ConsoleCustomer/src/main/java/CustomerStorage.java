import exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;

public class CustomerStorage {
    private HashMap<String,Customer> customers;
    private static final Logger logger = LoggerFactory.getLogger(CustomerStorage.class);


    public CustomerStorage (){
        customers = new HashMap<>();
        logger.trace("new Customer Storage was created");
    }

    public void addCustomer (String date) throws CustomerFormatException {
        String formatName = "[A-Z][a-z]+";
        String formatPhone = "\\+[0-9]{10,15}";
        String formatMail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        String [] components = date.split("\\s+");
        if (components.length != 4){
            throw new InvalidDateFormatException(date);
        }
        if (!components[0].matches(formatName) || !components[1].matches(formatName)){
            throw new InvalidFormatName(components[0] + " " + components[1]);
        }
        if (!components[2].matches(formatPhone)){
            throw new InvalidFormatPhone(components[2]);
        }
        if (!components[3].matches(formatMail)){
            throw new InvalidFormatEmail(components[3]);
        }
        Customer customer = new Customer(components[0] + " " + components[1], components[2], components[3]);
        logger.info("new customer was created:  " + customer.name() + " " + customer.mail() + " " + customer.phone());
        customers.put(components[3], customer);
        logger.trace("new customer was put into a storage - " + components[3] + " - key" );
        System.out.println("Done!");
    }

    public void showAllCustomers(){
        if (customers.isEmpty()){
            System.out.println("List is empty");
            logger.info("The attempt to call empty list");
        } else {
            customers.values().forEach(System.out::println);
            logger.trace("There was showed all the customers");
        }
    }

    public int getCustomerCount(){
        logger.trace("there was showed the size of customers list: " + customers.size());
        return customers.size();
    }

    public String removeCustomer (String email){
        if (customers.isEmpty()){
            logger.warn("The attempt to change the empty list");
            return "List is empty";
        }
        if (customers.containsKey(email)){
            String name = customers.get(email).name();
            customers.remove(email);
            logger.info("Customer " + name + " was deleted");
            return name + " was deleted";
        } else {
            logger.warn("there was the attempt to delete an entry with key: " + email + ". The entry doesn't exist");
            return "Customer with this email address doesn't exist ";
        }

    }
}
