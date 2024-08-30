import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final Path path = Path.of("res/movementList.csv");
    private static ParserBankStatement parser;
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main (String ... a){

        List<String> movements;

        try {
            movements = Files.readAllLines(path);
            movements = movements.stream().skip(1).collect(Collectors.toList());
            parser = new ParserBankStatement();
            parser.parseDocument(movements);
        } catch (IOException exception){
            exception.printStackTrace();
        } catch (Exception e){
            logger.error("Reading document error" + e.getMessage());
            logger.info("The document couldn't be read");
        }
            parser.printSheet();
            parser.printGeneralIncome();
            parser.printGeneralSpending();

        FilesManager filesManager = new FilesManager();
        filesManager.saveExpenseSheetToDocument(parser.getExpenseSheet());
        filesManager.findAndSaveCsvDocuments();
    }
}
