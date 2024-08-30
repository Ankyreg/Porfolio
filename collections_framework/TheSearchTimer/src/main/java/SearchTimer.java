import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeSet;

public class SearchTimer {

    public ArrayList<String> generationVehicleNumbers(){
    ArrayList <String> autoNum = new ArrayList<>();
    String letters = "CMTBAPONEY";
    int[] reg = new int [198];
    for (int i = 1; i < reg.length; i++) {
        reg[i] += i;
    }
    int[] num = new int[9];
    int temp = 111;
    for (int i = 0; i < num.length; i++) {
        num[i] = temp;
        temp += 111;
    }

    for (int numOfArea : reg) {
        for (int firstLetter = 0; firstLetter < letters.length(); firstLetter++) {
            for (int firstLetterOfLastPart = 0; firstLetterOfLastPart < letters.length(); firstLetterOfLastPart++) {
                for (int secondLetter = 0; secondLetter < letters.length(); secondLetter++) {
                    for (int number : num) {
                        autoNum.add(letters.charAt(firstLetter) + "" + number + "" + letters.charAt(firstLetterOfLastPart) +
                                "" + letters.charAt(secondLetter)
                                + "" + numOfArea);
                    }
                }
            }
        }
    }
    return autoNum;
}

    public void getNumberBruteForce(String input, ArrayList<String> auto) {
        long start = System.nanoTime();
        boolean found =  auto.contains(input);
        long end = System.nanoTime();
        long result = end - start;

        if (found) {
            System.out.println("Brute force search: number found, search took: " + result + " nanoseconds");
        } else {
            System.out.println("Brute force search: no number found, search took: " + result + " nanoseconds");
        }
    }

    public void getNumberBinarySearch(String input, ArrayList<String> auto){
        Collections.sort(auto);
        long start = System.nanoTime();
        int index = Collections.binarySearch(auto, input);
        long end = System.nanoTime();
        long result = end - start;
        if (index < 0 ){
            System.out.println("Binary search: number not found, search took: " + result + " nanoseconds");
        } else {
            System.out.println("Binary search: number found, search took: " + result + " nanoseconds");
        }
    }

    public void getNumberHashSet (String input, ArrayList<String> auto){
        HashSet<String> set = new HashSet<>(auto);
        long start = System.nanoTime();
        boolean found = set.contains(input);
        long end = System.nanoTime();
        long result = end - start;
        if (found){
            System.out.println("HashSet search: number found, search took: " + result + " nanoseconds");
        } else {
            System.out.println("HashSet search: number not found, search took: " + result + " nanoseconds");
        }
    }

    public void getNumberTreeSet (String input, ArrayList<String> auto){
        TreeSet<String> treeSet = new TreeSet<>(auto);
        long start = System.nanoTime();
        boolean found = treeSet.contains(input);
        long end = System.nanoTime();
        long result = end - start;
        if (found){
            System.out.println("TreeSet search: number found, search took: " + result + " nanoseconds");
        } else {
            System.out.println("TreeSet search: number not found, search took: " + result + " nanoseconds");
        }
    }
}
