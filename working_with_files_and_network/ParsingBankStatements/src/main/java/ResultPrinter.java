import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ResultPrinter {
    private Logger logger = LoggerFactory.getLogger(ResultPrinter.class);

    public void printSheet(Map<String,Double> expenseSheet){
        if (expenseSheet.isEmpty()) {
            logger.debug("attempt to print unformed expense sheet");
            logger.info("Creating the table was canceled");
        } else {
            logger.debug("print the expense sheet");
            for (Map.Entry<String, Double> entry : expenseSheet.entrySet()) {
               logger.info(entry.getKey() + " ------------> " + entry.getValue());
            }
        }
    }

    public void printGeneralSpending(double sum){
        logger.debug("printing of general spending: " + sum);
        logger.info("General Spending: " + sum);
    }

    public void printGeneralIncome(double sum){
        logger.debug("printing of general income: " + sum);
        logger.info("General Income: " + sum);
    }

    public void setLoggerForTest (Logger testLogger){
        logger = testLogger;
    }

}
