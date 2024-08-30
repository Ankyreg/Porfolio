import java.util.*;

public class DateBase {

    private final HashSet<String> phones = new HashSet<>();
    private final HashSet<String> emails = new HashSet<>();
    private final TreeSet<Client> clients = new TreeSet<>();
    private final Scanner scanner = new Scanner(System.in);

    /**
     * A method of checking if this phone is in the set phones. Is called by {@link #addPhone(int)}
     * If this number already exists in the set, the method looks for an account with this phone in the list of clients
     * afterward calls the method of printing a message about restoring the account {@link #printRestoreMessage(String, String)}
     * and returns null. If number isn't exist,
     * method returns number.
     * @param phone client phone
     * @return String phone or null
     */

    private String checkPhone(String phone) {
        String checkPhone = phone;
        if (phones.add(phone)) {
            System.out.println("Phone was added");
            }
        if (clients.contains(new Client("",phone,""))) {
            Client client = clients.ceiling(new Client("", phone, ""));
            assert Objects.requireNonNull(client).getEmail() != null;
            printRestoreMessage(client.getEmail(), client.getName());
            checkPhone = null;
        }
        return checkPhone;
    }

    /**This method is called by {@link #addPhone(int)} to
     * checking the number input in the international format.
     * the number starts with + and consists of digits
     * @param phone client phone
     * @return
     */
    private boolean checkFormatPhone(String phone) {
        if (phone.matches("^\\+\\d+")) {
            return true;
        }
            System.out.println("Wrong format");
            return false;
    }
    /**
     * A method of checking if this email is in the set emails. Is called by {@link #addMail(int)}
     * If this email already exists in the set,the method looks for an account with this email in the list of clients
     * afterward calls the method of printing a message about restoring the account {@link #printRestoreMessage(String, String)}
     * and then it returns exception. If email isn't exist, the method returns this email
     */
    private String checkMail(String mail) throws Exception {
        if (emails.add(mail)) {
            System.out.println("mail was added");
        }
        if(clients.contains(new Client("","",mail))){
            Client client = clients.ceiling(new Client("","",mail));
            assert client != null;
            printRestoreMessage(client.getPhone(), client.getName());
            throw new Exception("account is being restored");
        }
        return mail;
    }

    /** A method is called by {@link #addMail(int)} to check format of user input
     * An email must contain @ symbol
     * @param mail email of client
     * @return
     */

    private boolean checkFormatMail(String mail) {
        if (mail.matches(".+@.+")) {
            return true;
        }
            System.out.println("Wrong mail address");
            return false;
    }

    /***
     * This is recursive method. is called by the method {@link #addClient()} If input is incorrect more 5 times, it
     * returns exception.
     * There is needed to enter number of phone. If number exists in the set phones, it calls exception.
     * If method {@link #checkPhone(String)} returns not null, the method returns user input.
     * @param currentDeep step recursion
     * @return
     */
    private String addPhone(int currentDeep) throws Exception {
        if (currentDeep == 5){
            throw new Exception("the number of incorrect entries is exceeded");
        }
        System.out.println("Enter your phone number starting with +");
        String inputPhone = scanner.nextLine();
        if (!checkFormatPhone(inputPhone) || inputPhone.isBlank()) {
            return addPhone(currentDeep+1);
        }
        if (checkPhone(inputPhone) == null) {
            throw new Exception("account is being restored");
        }
        return inputPhone;
    }

    /**
     *  This is recursive method. If input is incorrect more 5 times, it returns exception.
     *  There is needed to enter an email. If the email exists in the set phones, it returns null.
     *  If method {@link #checkMail(String)} returns not null, the method returns user input.
     * @param currentDeep step recursion
     * @return
     */

    private String addMail(int currentDeep) throws Exception {
        if (currentDeep == 5){
            throw new Exception("the number of incorrect entries is exceeded");
        }
        System.out.println("Enter your mail please");
        String inputMail = scanner.nextLine();
        if (inputMail.isBlank() || !checkFormatMail(inputMail)) {
            return addMail(currentDeep+1);
        }
        return checkMail(inputMail);
    }

    /** is called from {@link #checkMail(String)} {@link #checkPhone(String)}
     * A method prints a message of restore a client account
     * @param contact email or phone
     * @param name client name
     */
    private void printRestoreMessage (String contact, String name){
        int length = contact.length() / 3;
        String lastNum = contact.substring(length);
        System.out.println("You are already registered as " + name + ", you will receive a " +
                "message on your the specified contact and the associated contact *****" + lastNum);
    }

    /**
     * This method check a user input and returns the input if it is correct
     * @return
     */
    private String addName(int currentDeep) throws Exception {
        if (currentDeep == 5){
            throw new Exception("the number of incorrect entries is exceeded");
        }
        System.out.println("Enter your name");
        String inputName = scanner.nextLine();
        if (inputName.isBlank()) {
             return addName(currentDeep + 1);
        }
            return inputName;
    }

    /**
     * This method collects the received data and creates a new client object and adds it in the client set
     */
    public void addClient(){
        try {
            String phone = addPhone(0);
            String mail = addMail(0);
            String name = addName(0);
            Client client = new Client(name, phone, mail);
            clients.add(client);
            System.out.println("Done!");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints all clients
     */
    public void printClients (){
        StringBuilder sb = new StringBuilder();
        for (Client client : clients){
            sb.append("Name: ").append(client.getName()).append("\nPhone: ").append(client.getPhone()).append("\nEmail: ")
                            .append(client.getEmail()).append("\n_____________\n");
        }
        System.out.println(sb);
    }

    /**
     * The method adds someone else's collection to the list of clients.
     * Also adds phone and email to set phones and emails
     */
    public void addClientsList(Collection<Client> clientList){
        clients.addAll(clientList);
        for (Client client : clientList){
            emails.add(client.getEmail());
            phones.add(client.getPhone());
        }
    }
}
