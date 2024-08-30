import java.util.*;

public class PhoneBook {

    private final HashMap<String, Contact> allContacts = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    /**
     * method of filling the list with new contact objects.
     */
    public void start()  {
        System.out.println("To view all contacts, enter the LIST command to add a contact, type ADD");
        String input = scanner.nextLine();
        try {
            if (input.contains("LIST")) {
                    showAllContacts();
            } else if (input.contains("ADD")) {
                System.out.println("Enter a name");
                String name = scanner.nextLine();
                if (name.isBlank()) {
                    System.out.println("name is blank");
                    return;
                }
                if (allContacts.containsKey(name)) {
                        String editContact = editContact(name);
                        System.out.println(editContact + " was changed. " + allContacts.get(name).toString());
                        return;
                }
                System.out.println("Enter a phone");
                String phone = checkPhone(scanner.nextLine());
                Contact contact = new Contact(name, phone);
                allContacts.put(contact.name(), contact);
            } else {
                System.out.println("the command is wrong");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * method changes a phone of contact object
     * or returns exception
     *
     * @param name any string
     * @return string
     */
    private String editContact(String name) throws Exception {
        System.out.println("a contact with this name already exists, Do you want to change it? yes or not");
        String answer = scanner.nextLine();
        if (answer.contains("yes")) {
            System.out.println("Enter a new contact number");
            String newPhone = checkPhone(scanner.nextLine());
            return Objects.requireNonNull(allContacts.put(name, new Contact(name, newPhone))).toString();
        } else throw new Exception("Cancel");
    }

    /**
     * it provides a sorted map of contacts
     */
    public void showAllContacts() throws Exception {
        if (allContacts.isEmpty()) {
            throw new Exception("List is empty");
        }
        TreeMap<String, Contact> sortMap = new TreeMap<>(Comparator.naturalOrder());
        sortMap.putAll(allContacts);
        for (Contact contact : sortMap.values()) {
            System.out.println(contact.name() + " " + contact.phone() + "\n________");
        }
    }

    /**
     *The method checks the string format, the presence of a number in existing contacts.
     * if the phone is not found in the list and is written in the correct format, the method returns the phone.
     * Otherwise throws the appropriate exception
     * @param phone
     * @return String phone
     * @throws Exception
     */
    private String checkPhone(String phone) throws Exception {
        if (phone.isBlank() || !phone.matches("^\\+?\\d+")) {
            throw new Exception("Wrong number format");
        } else if (allContacts.containsValue(new Contact("", phone))) {
                for (Contact contact : allContacts.values()) {
                    if (contact.phone().equals(phone)) {
                        throw new Exception("Contact already exists as ");
                    }
                }
            }
        return phone;
    }
}
