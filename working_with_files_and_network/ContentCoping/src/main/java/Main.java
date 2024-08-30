import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main (String ... a) throws IOException {
        Path from;
        Path target;
        Scanner scanner = new Scanner(System.in);

            System.out.println("Enter the absolute path to the copied folder");
            from = getValidFile(scanner);
            System.out.println("Specify the absolute path where you want to copy the file");
            target = getValidFile(scanner);
            try {
                Files.walkFileTree(from, new MyFilesVisitor(from,target));
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        private static Path getValidFile (Scanner scanner){
        Path path;
        do {
            String pathString = scanner.nextLine();
            path = Paths.get(pathString);

            if (isValidDirectory(path)){
                System.out.println("the wrong path. Come again");
            }
        } while (isValidDirectory(path));

        return path;
        }

        private static boolean isValidDirectory (Path path){
        return !Files.isDirectory(path) || !Files.exists(path);
    }
}
