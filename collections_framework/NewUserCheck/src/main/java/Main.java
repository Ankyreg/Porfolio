import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String... arg) {
        DateBase dateBase = new DateBase();
        List <Client> listClients = new ArrayList<>();
        listClients.add(new Client("Nick", "+89000","funy@gmail.com"));
        listClients.add(new Client("Nick", "+89001","postpost@gmail.com"));
        listClients.add(new Client("Nick", "+89001","locky@gmail.com"));
        listClients.add(new Client("Nick","+90002","funy@gmail.com"));

        for (int  i = 0; i < 5; i++ ) {
            dateBase.addClient();
        }
        dateBase.addClientsList(listClients);
        dateBase.printClients();

    }
}
