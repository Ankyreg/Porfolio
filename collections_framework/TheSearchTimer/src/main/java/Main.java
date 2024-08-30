import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main (String ... a){
        SearchTimer searchTimer = new SearchTimer();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter num of car. Format instance: C444CA19");
        String input = scanner.nextLine();
        ArrayList<String> numbersList = searchTimer.generationVehicleNumbers();
        System.out.println(numbersList.size());
        searchTimer.getNumberBruteForce(input,numbersList);
        searchTimer.getNumberTreeSet(input,numbersList);
        searchTimer.getNumberHashSet(input,numbersList);
        searchTimer.getNumberBinarySearch(input,numbersList);

    }
}
