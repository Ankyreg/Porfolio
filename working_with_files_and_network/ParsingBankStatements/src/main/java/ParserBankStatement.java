import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserBankStatement {
    private final List<Double> accrued = new ArrayList<>();
    private final List<Double>consumption = new ArrayList<>();
    private final Map<String,Double> expenseSheet = new HashMap<>();
    private final List <String> account = new ArrayList<>();
    private final Logger logger = LoggerFactory.getLogger(ParserBankStatement.class);
    private final ResultPrinter printer = new ResultPrinter();
    private final Scanner scanner = new Scanner(System.in);

    public boolean parseDocument (List<String> movement){
        logger.debug("Creating parser");
        try {
            parseStatements(movement,scanner);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public void parseStatements(List<String> movement, Scanner myScanner ) throws Exception {

        if (movement.isEmpty()){
            throw new Exception("List is Empty");
        }
        boolean continueParsing = true;
        for (String line : movement){
            String [] splitLine = line.split(",", 8);
            try {
                logger.debug("Start parsing line " + splitLine[5]);
                addAccount(splitLine[5]);
                logger.debug("Start parsing line " + splitLine[6]);
                double coming = doubleCorrecting(splitLine[6]);
                accrued.add(coming);
                logger.debug("Start parsing line " + splitLine[7]);
                double consume =  doubleCorrecting(splitLine[7]);
                consumption.add(consume);
            } catch (IllegalArgumentException exception) {
                if (!handleParsingError(exception.getMessage(), myScanner)) {
                    continueParsing = false;
                    clearDate();
                    logger.warn("termination of the program parsing this document");
                    logger.info("Check whether the document is readable");
                   break;
                }
            }
        } if (continueParsing){
            logger.debug("start creating a document");
            createSheet();
        }
    }

    /**
     * correcting double date to the java format.
     */

    private double doubleCorrecting(String line) throws IllegalArgumentException{
        Pattern pattern = Pattern.compile("\\d+([.,]\\d+)?");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()){
            line = matcher.group().replace(",",".");
            logger.debug(line + " was successfully corrected");
            return Double.parseDouble(line);
        } logger.error(line + " can't be corrected"); throw new IllegalArgumentException(line);
    }

    /**
     * if an element can't be parsed, a user can choose whether to stop parsing or skip the table element
     * "1" - stop parsing (program termination)
     * "2" - continue parsing with unreadable line skipping
     * @return
     */
    private boolean  handleParsingError(String exception, Scanner scanner) {
        logger.info("error parsing element " + exception + "\nDo stop parsing or continue?\n\t"
                + "stop - 1" + "\n\tcontinue - 2");
        logger.debug("waiting for user input to decide whether to continue parsing the document");
        int inputCounter = 0;
        while (inputCounter < 10 ) {
            String answer = scanner.nextLine();
            if (answer.equals("2")) {
                logger.debug("continued parsing with incomplete data ");
                return true;
            }
            if (answer.equals("1")) {
                logger.debug("the parsing cancelled");
                return false;
            } else {
                inputCounter = inputCounter + 1;
                logger.warn("unexpected input " + answer + ". There is " + inputCounter + " atte");
                logger.info("wrong command");
            }
        } return false;
    }

    /**
     * The method checks whether the string matches the specified format. If the string matches the format,
     * adds the element to the collection {@link #account}
     * @param accountLine
     * @throws IllegalArgumentException
     */

    private void addAccount (String accountLine) throws IllegalArgumentException{
        logger.debug("the attempt of parsing line: " + accountLine);
        String lineAccountReg = "(\\d+\\++\\d+)\\s+(.{4,40})\\s+(\\d\\d\\.\\d\\d\\.\\d\\d)\\s+(\\d\\d\\.\\d\\d\\.\\d\\d)\\s+";
        Pattern pattern = Pattern.compile(lineAccountReg);
        Matcher matcher = pattern.matcher(accountLine);
        if (matcher.find()){
            logger.debug("account was received correctly");
            String accountName = matcher.group(2).trim();
            account.add(accountName);
        } else {
            logger.error("received a non-standard string" + accountLine);
            throw  new IllegalArgumentException(accountLine);
        }
    }

    /**
     * Creates an expense sheet based on the accounts, consumption, and previous sheet data.
     * If an account already exists in the expense sheet, the consumption value is added to the existing total.
     * If the account is not present, a new entry is created with the consumption value.
     * The method updates the internal expenseSheet map and logs the creation of the sheet.
     */

    private void createSheet(){
        int i = 0;
        for (String ac : account){
            if (expenseSheet.containsKey(ac)){
                double current = expenseSheet.get(ac);
                expenseSheet.put(ac, consumption.get(i) + current);
            } else {
                expenseSheet.put(ac,consumption.get(i));
            }
            i ++;
        }
        logger.debug("The sheet was created");
    }

    public void printSheet() {
        if (expenseSheet.isEmpty()){
            logger.debug("attempt to request unformed income data.");
            logger.info("failed to read document");
        } else {
            printer.printSheet(getExpenseSheet());
        }

    }

    public void printGeneralSpending() {
        if (getConsumption().isEmpty()){
            logger.debug("attempt to request unformed income data.");
            logger.info("failed to read document");
        } else {
            double sum = getConsumption().stream().mapToDouble(Double::doubleValue).sum();
            printer.printGeneralSpending(sum);
        }
    }

    public void printGeneralIncome(){
        if (getAccrued().isEmpty()){
            logger.debug("attempt to request unformed income data.");
            logger.info("failed to read document");
        } else{
            double sum = getAccrued().stream().mapToDouble(Double::doubleValue).sum();
            printer.printGeneralIncome(sum);
        }
    }

    private void clearDate(){
        accrued.clear();
        consumption.clear();
        account.clear();
        expenseSheet.clear();
    }

    public Map <String,Double> getExpenseSheet (){
        return new HashMap<>(expenseSheet);
    }
    public List <Double> getAccrued(){
        return new ArrayList<>(accrued);
    }
    public List <Double> getConsumption(){
        return new ArrayList<>(consumption);
    }
    public List <String> getAccounts(){
        return new ArrayList<>(account);
    }
}
