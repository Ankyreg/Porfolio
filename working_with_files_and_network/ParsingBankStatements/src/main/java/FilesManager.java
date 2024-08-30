import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class FilesManager {
    private final int MAX_RECURSION_DEEP = 10;
    private int directoryInputCount = 0;
    private final Scanner scannerForPath = new Scanner(System.in);
    private final Scanner scannerForAnswer = new Scanner(System.in);
    private final String root = "res";
    private final Logger logger = LoggerFactory.getLogger(FilesManager.class);

    /**
     * Method of writing Map data expense sheet to a txt document.
     * @param expenseSheet
     */

    public void saveExpenseSheetToDocument(Map<String, Double> expenseSheet) {
        logger.debug("Saving the expenseSheet starts");
        Optional<Path> path = pathValidator();
        if (path.isEmpty()) {
            logger.warn("Cancel document generation");
            System.out.println("Sorry! Document creation canceled");
        } else {
            Path absolutePath = path.get().resolve("expenseSheet.txt");
            logger.debug("trying to write \"expenseSheet.txt\"");
            try (BufferedWriter writer = Files.newBufferedWriter(absolutePath, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING)) {
                for (Map.Entry<String, Double> ex : expenseSheet.entrySet()) {
                    writer.write(ex.getKey() + " ----> " + ex.getValue());
                    writer.newLine();
                }
                writer.flush();
            } catch (Exception e) {
                logger.error("Unexpected problem " + e.getLocalizedMessage());
                System.out.println("Unexpected problem " + e.getMessage());
            }

            System.out.println("Done!");
            logger.debug(absolutePath + "  was created");
        }
    }

    /**
     * The method prompts the user to enter the directory where the generated file should be saved.
     * if this method is calls more than {@link  #MAX_RECURSION_DEEP}, it returns empty optional and parsing finishes.
     * If the user input is incorrect, the method {@link #handlingIncorrectUserInput()} is called
     *
     * @return
     */

    private Optional<Path> pathValidator() {
        if (directoryInputCount > MAX_RECURSION_DEEP) {
            System.out.println("Too many failed input attempts");
            logger.error("the number of incorrect inputs has been exceeded " + directoryInputCount);
            return Optional.empty();
        }
        String path;
        System.out.println("Enter the absolute path to a folder for saving a document");
        logger.debug("User input");
        path = scannerForPath.nextLine();
        if (isFolder(path)) {
            logger.debug("directory was specified " + path);
            return Optional.of(Path.of(path));
        }
        logger.debug("a non-existent directory was specified " + path);
        return handlingIncorrectUserInput();
    }

    private boolean isFolder(String path) {
        return Files.exists(Paths.get(path)) && !Files.isRegularFile(Paths.get(path));
    }

    /**
     * The method prompts the user to enter the existing directory again, or create a new one.
     * If the user enters an incorrect option number more than 10 times the method returns empty Option and the
     * document creating finishes.
     * If the user selects the second option (enter the existing directory again) the method calls {@link #pathValidator()}
     * and increments {@link #directoryInputCount}
     * If the user selects the first option (to create new directory) the method calls {@link #createFolder()}
     *
     * @return
     */

    private Optional<Path> handlingIncorrectUserInput() {
        logger.debug("An attempt to enter a path again or create new folder in " + root);
        System.out.println("""
                This directory doesn't exist. Would you like to create a folder?
                \t 1 - yes
                \t 2 - no. I want to enter the ABSOLUTE path one more time""");
        logger.debug("User input");
        String answer = scannerForAnswer.nextLine();
        int inputAnswerCounter = 0;
        do {
            if (answer.equals("2")) {
                directoryInputCount++;
                logger.debug("Trying to enter the directory again. Attempt " + directoryInputCount);
                return pathValidator();
            }
            if (answer.equals("1")) {
                logger.debug("Trying to create a new directory");
                return createFolder();
            } else {
                logger.debug("unexpected input " + answer + ". Attempt #" + inputAnswerCounter);
                inputAnswerCounter = inputAnswerCounter + 1;
                System.out.println("Invalid input");
            }
        } while (inputAnswerCounter != 10);
        return Optional.empty();
    }

    /**
     * This method creates new folder in {@link #root} if the entered name complies with folder naming standards.
     * Otherwise, it returns empty Optional.
     *
     * @return
     */

    private Optional<Path> createFolder() {
        Path file;
        String reg = "[/:*?»<>|.\\\\]^[\\x00-\\x1f]";
        System.out.println("Enter a name");
        logger.debug("user input");
        String newInput = scannerForPath.nextLine();
        if (newInput.matches(reg) || newInput.length() > 200) {
            System.out.println("invalid file name");
            logger.info("invalid file name " + newInput);
            return Optional.empty();
        } else {
            try {
                logger.debug("trying to create the directory: " + newInput);
                file = Files.createDirectory(Paths.get(root, newInput));
                logger.debug("the folder " + root + "/" + newInput + "   was created");
               System.out.println("Your new expenseSheet.txt will be here: " + file.toAbsolutePath());
                return Optional.of(file);
            } catch (IOException e) {
                logger.warn("failed to create folder " + newInput + " " + e.getLocalizedMessage());
                System.out.println("Error creating folder: " + e.getMessage());
                return Optional.empty();
            }
        }
    }

    /**
     * The method searches for CSV files and copies the found files to a created folder by
     * {@link #createDirectory()}.
     * A search is performed in the specified directory and subdirectories.
     * If the specified directory isn't folder, a message about this is displayed.
     */

    public void findAndSaveCsvDocuments() {
        System.out.println("Enter the absolute path to the directory where you want to find csv");
        logger.debug("user input directory to CSV directory");
        String source = scannerForPath.nextLine();
        Path target;
        if (isFolder(source)) {
            Optional<Path> path = createDirectory();
            if (path.isPresent()) {
                target = path.get();
                try {
                    MyFilesVisitor myFilesVisitor = new MyFilesVisitor("*csv", target);
                    logger.debug("start searching for *csv files");
                    Files.walkFileTree(Path.of(source), myFilesVisitor);
                } catch (IOException e) {
                    logger.error("An error occurred "  +  e.getMessage(), e);
                    System.out.println("Failed to copy data");
                }
            }
        } else {
            logger.debug("A non-existent directory was specified");
           System.out.println("This directory doesn't exist");
        }
    }

    /**
     * The method creates a “foundlings_” folder in the {@link #root} to save all found CSV files.
     * For each search, a new folder with the current date is created, if a folder with the same name already exists,
     * then a number is added to the folder name by {@link #increaseCopyName(String)}
     *
     * @return
     */

    private Optional<Path> createDirectory() {
        String dateCreation = LocalDate.now().format(DateTimeFormatter.ofPattern("dd_MM_yy"));
        String preName = "foundlings_";
        Path target = Paths.get(root, preName + dateCreation);
        boolean fileExist = Files.exists(target);
        Path nameOfCopy;
        if (fileExist) {
            try {
                Optional<Integer> numOfCopy = increaseCopyName(preName + dateCreation);
                if (numOfCopy.isEmpty()) {
                    logger.error("Failed to increase copy name. Aborting directory creation.");
                    return Optional.empty();
                }
                nameOfCopy = Paths.get(target + "(" + numOfCopy.get() + ")");
                return Optional.of(Files.createDirectory(nameOfCopy));
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                System.out.println("Something went wrong  " + e.getMessage());
                return Optional.empty();
            }
        } else {
            try {
                Files.createDirectories(target);
                return Optional.of(target);
            } catch (IOException e){
                logger.error("Failed to create directory: " + e.getMessage(), e);
                return Optional.of(target);
            }
        }
    }

    /**
     * If the passed parameter contains a number in brackets like "foundlings_20_01_22(3)" then all matches in
     * {@link #root} are searched and counted and the sum of all matches with brackets counted is returned, increased by 1.
     *
     * @param path
     * @return
     * @throws IOException
     */

    private Optional<Integer> increaseCopyName(String path) throws IOException {
        String pattern = path + "\\([0-9][0-9]?[0-9]?\\)";
        long numOfCopies;
        try {
            numOfCopies = Files.find(Path.of(root), Integer.MAX_VALUE, (p, attr) ->
                            p.getFileName().toString().matches(pattern)).count();
        } catch (IOException e) {
            logger.error("error when working in directory " + root + ". " + e.getMessage(), e);
            return Optional.empty();
        }
        return Optional.of(Math.toIntExact(numOfCopies + 1));
    }

        private class MyFilesVisitor extends SimpleFileVisitor<Path> {

        private final Path target;
        private final PathMatcher matcher;

     private MyFilesVisitor(String pattern, Path target) {
         matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
         this.target = target;
     }

     private void searchAndCopy (Path file){
         Path name = file.getFileName();
         if (name != null && matcher.matches(name)){
             try {
                 Path destination = target.resolve(name.getFileName());
                 Files.copy(file,destination,StandardCopyOption.REPLACE_EXISTING);
                 logger.info("file copied successfully");
                 System.out.println("Done!");
             }catch (IOException e){
                 logger.error("an error occurred during copying " + e.getMessage(), e);
                 System.out.println("An error occurred while working with files " + e.getMessage());
             }
         } else {
             logger.info("no any matches");
             System.out.println("files not found");
         }
     }

     public FileVisitResult visitFile (Path file, BasicFileAttributes attributes){
         searchAndCopy(file);
         return FileVisitResult.CONTINUE;
        }
    }
}
