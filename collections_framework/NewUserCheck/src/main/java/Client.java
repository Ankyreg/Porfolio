public class Client implements Comparable<Client> {

    private final String name;
    private final String phone;
    private final String email;

    public Client(String name, String phone, String mail){
        this.name = name;
        this.phone = phone;
        this.email = mail;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    /**
     * If objects match at least one field, they are considered to be the same
     */
    @Override
    public int compareTo(Client o) {
        int mailComparison = email.compareTo(o.getEmail());
        if (mailComparison != 0){
            return phone.compareTo(o.getPhone());
        }
        return mailComparison;
    }
}

