import java.util.Scanner;

public class Main {
    private static final int MAX_CONTAINER = 12;
    private static final int MAX_BOX = 27;

    public static void main (String ... a){
        int track;
        int container;
        int box;

        System.out.println("How much boxes do you need to load? ");
        box = new Scanner(System.in).nextInt();
        container = box % MAX_BOX == 0 ? box / MAX_BOX : box / MAX_BOX + 1;
        track = container % MAX_CONTAINER == 0 ? container / MAX_CONTAINER : container / MAX_CONTAINER +1;
        System.out.println("It will take " + container + (container > 1 ? " containers and " : " container and ") + track
                + (track > 1 ? " tracks " : " track"));
        System.out.println("Loading begins...");

        int numBox = 1;
        int numContainer = 1;

        for (int i = 1; i <= track; i++){
            System.out.println("Track #" + i);
            int tempContainer = 1;
            while (tempContainer < MAX_CONTAINER && numContainer <= container){
                System.out.println("Container #" + numContainer);
                int tempBox = 1;
                while (tempBox <= MAX_BOX && numBox <= box){
                    System.out.println("Box #" + numBox);
                    numBox++;
                    tempBox++;
                }
                tempContainer++;
                numContainer++;
            }
        }
        System.out.println("Done!");
    }

}
