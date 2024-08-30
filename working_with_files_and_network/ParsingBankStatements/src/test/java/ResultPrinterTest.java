import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import java.util.HashMap;
import java.util.Map;

public class ResultPrinterTest {
    ResultPrinter resultPrinter;
    Logger logger;

    @BeforeEach
    void setUP(){
        resultPrinter = new ResultPrinter();
        logger = Mockito.mock(Logger.class);
        resultPrinter.setLoggerForTest(logger);

    }

    @Test
    void tesPrintSheetWithDate(){
        HashMap<String,Double> expenseSheet = createExpenseSheet();
        resultPrinter.printSheet(expenseSheet);
        for (Map.Entry<String,Double> entry : expenseSheet.entrySet()){
            Mockito.verify(logger).info(entry.getKey() + " ------------> " + entry.getValue());
        }
    }
    @Test
    void testPrintSheetWithoutDate(){
        resultPrinter.printSheet(new HashMap<>());
        Mockito.verify(logger).info("Creating the table was canceled");
    }

    @Test
    void testPrintGeneralIncomeWithCorrectDouble(){
        double correctDouble = 800.00;
        resultPrinter.printGeneralIncome(correctDouble);
        Mockito.verify(logger).info("General Income: 800.0" );
    }

    @Test
    void testPrintGeneralIncomeWithNull(){
        double correctDouble = 0;
        resultPrinter.printGeneralIncome(correctDouble);
        Mockito.verify(logger).info("General Income: 0.0" );
    }

    @Test
    void testPrintGeneralSpendingWithCorrectDouble(){
        double correctDouble = 800.00;
        resultPrinter.printGeneralSpending(correctDouble);
        Mockito.verify(logger).info("General Spending: 800.0" );
    }

    @Test
    void testPrintGeneralSpendingWithNull(){
        double correctDouble = 0;
        resultPrinter.printGeneralSpending(correctDouble);
        Mockito.verify(logger).info("General Spending: 0.0" );
    }


    private HashMap<String,Double>createExpenseSheet(){
        HashMap <String,Double> expenseSheet =  new HashMap<>();
        expenseSheet.put("name1", 800.00);
        expenseSheet.put("name2",980.0);
        return expenseSheet;
    }



}
