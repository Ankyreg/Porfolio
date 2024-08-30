import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ParserBankStatementTest {
    ParserBankStatement parser;

    @BeforeEach
    void setUp(){
        parser = new ParserBankStatement();
    }

    @Test
    void TestParseStatementsWithCorrectDate(){
        List<String> movement = Arrays.asList(
                "account type,40817813206170024534,RUR,31.05.17,CRD_1U34U7,548673++++++1028    809216  /RU/CARD2CARD ALFA_MOBILE>MOSCOW          31.05.17 31.05.17 1500.00       RUR MCC6536,1500,200",
                "account type,40817813206170024534,RUR,31.05.17,CRD_1U34U7,548673++++++1028    809216  /RU/CARD2CARD ALFA_MOBILE>MOSCOW          31.05.17 31.05.17 1500.00       RUR MCC6536,1500,200",
                "account type,40817813206170024534,RUR,31.05.17,CRD_5XK5TM,548673++++++1028    21708201\\RUS\\MOSCOW\\Ryabin\\KUSCHAVEL              31.05.17 29.05.17       300.00  RUR (Apple Pay-7666) MCC5814,400,300",
                "account type,40817813206170024534,RUR,31.05.17,CRD_5XK5TM,548673++++++1028    21708201\\RUS\\MOSCOW\\Ryabin\\KUSCHAVEL              31.05.17 29.05.17       300.00  RUR (Apple Pay-7666) MCC5814,400,300"
        );
        try {
            parser.parseDocument(movement);
            List<String> expectedAccount = Arrays.asList("809216  /RU/CARD2CARD ALFA_MOBILE>MOSCOW","21708201\\RUS\\MOSCOW\\Ryabin\\KUSCHAVEL","809216  /RU/CARD2CARD ALFA_MOBILE>MOSCOW","21708201\\RUS\\MOSCOW\\Ryabin\\KUSCHAVEL");
            List <Double> expectedAccrued = Arrays.asList(1500.00, 400.00,1500.00, 400.00);
            List <Double> expectedConsumption = Arrays.asList(200.00, 300.00,200.00, 300.00);
            Map<String,Double> expectedExpenseSheet = Map.of("809216  /RU/CARD2CARD ALFA_MOBILE>MOSCOW", 400.00, "21708201\\RUS\\MOSCOW\\Ryabin\\KUSCHAVEL",600.00 );

            //testing correct account parsing
            List<String> actualAccount = parser.getAccounts();
            expectedAccount.sort(Comparator.naturalOrder());
            actualAccount.sort(Comparator.naturalOrder());
            Assertions.assertEquals(expectedAccount,actualAccount);

            //testing correct accrued parsing
            List<Double> actualAccrued = parser.getAccrued();
            expectedAccrued.sort(Comparator.naturalOrder());
            actualAccrued.sort(Comparator.naturalOrder());
            Assertions.assertEquals(expectedAccrued,actualAccrued);

            //testing correct consumption
            List<Double> actualConsumption = parser.getConsumption();
            expectedConsumption.sort(Comparator.naturalOrder());
            actualConsumption.sort(Comparator.naturalOrder());
            Assertions.assertEquals(expectedConsumption,actualConsumption);

            //testing correct expense sheet
            Assertions.assertEquals(expectedExpenseSheet,parser.getExpenseSheet());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void TestParseStatementsWithFailedAccountLineAndParsingTermination(){
        List<String> movement = Arrays.asList(
                "account type,       40817813206170024534,RUR,31.05.17,CRD_1U34U7,ikl.    809216  /RU/CARD2CARD ALFA_MOBILE>MOSCOW          31.05.17 31.05.17 1500.00       RUR MCC6536,1500,200",
                "account type,40817813206170024534,RUR,31.05.17,CRD_1U34U7,548673++++++1028     1500.00       RUR MCC6536,        1500,200",
                "account type,      40817813206170024534,RUR,31.05.17,CRD_5XK5TM,yuyiuiggjhn bhgggggggggggggggggggggggggggmnjhgjfjfjvjvvj 31.05.17 29.05.17       300.00  RUR (Apple Pay-7666) MCC5814,400,300",
                "account type,      40817813206170024534,           RUR,31.05.17,CRD_5XK5TM,548673++++++1028    21708201\\RUS\\MOSCOW\\Ryabin\\KUSCHAVEL              31.05.17 29.05.17       300.00  RUR (Apple Pay-7666) MCC5814,400,300"
        );
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream("1\n".getBytes(StandardCharsets.UTF_8));
            Scanner scanner = new Scanner(inputStream);
            parser.parseStatements(movement, scanner);
            //testing correct account parsing
            Assertions.assertTrue(parser.getAccounts().isEmpty());
            //testing correct accrued parsing
            Assertions.assertTrue(parser.getAccrued().isEmpty());
            //testing correct consumption
            Assertions.assertTrue(parser.getConsumption().isEmpty());
            //testing correct expense sheet
            Assertions.assertTrue(parser.getExpenseSheet().isEmpty());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void TestParseStatementsWithFailedAccountLineAndParsingContinuation(){
        List<String> movement = Arrays.asList(
                "account type,40817813206170024534,RUR,31.05.17,CRD_1U34U7,548673++++++1028    809216  /RU/CARD2CARD ALFA_MOBILE>MOSCOW          31.05.17 31.05.17 1500.00       RUR MCC6536,1500,200",
                "account type,40817813206170024534,RUR,31.05.17,CRD_1U34U7,548673++++++1028 ,   809216  /RU/CARD2CARD ALFA_MOBILE>MOSCOW          31.05.17 31.05.17 1500.00       RUR MCC6536,1500,200",
                "account type,40817813206170024534,RUR,31.05.17,CRD_5XK5TM,548673++++++1028    21708201\\RUS\\MOSCOW\\Ryabin\\KUSCHAVEL              31.05.17 29.05.17       300.00  RUR (Apple Pay-7666) MCC5814,400,300",
                "account type,40817813206170024534,RUR,31.05.17,CRD_5XK5TM,548673++++++1028    21708201\\RUS\\MOSCOW\\Ryabin\\KUSCHAVEL              31.05.17 29.05.17       300.00  RUR (Apple Pay-7666) MCC5814,400,300"
        );

        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream("2\n".getBytes(StandardCharsets.UTF_8));

            Scanner scanner = new Scanner(inputStream);
            parser.parseStatements(movement, scanner);

            List<String> expectedAccount = Arrays.asList("809216  /RU/CARD2CARD ALFA_MOBILE>MOSCOW","21708201\\RUS\\MOSCOW\\Ryabin\\KUSCHAVEL", "21708201\\RUS\\MOSCOW\\Ryabin\\KUSCHAVEL");
            List <Double> expectedAccrued = Arrays.asList(1500.00, 400.00, 400.0);
            List <Double> expectedConsumption = Arrays.asList(200.00, 300.00,300.00);
            Map<String,Double> expectedExpenseSheet = Map.of("809216  /RU/CARD2CARD ALFA_MOBILE>MOSCOW", 200.00, "21708201\\RUS\\MOSCOW\\Ryabin\\KUSCHAVEL",600.00 );

            //testing correct account parsing
            List<String> actualAccount = parser.getAccounts();
            expectedAccount.sort(Comparator.naturalOrder());
            actualAccount.sort(Comparator.naturalOrder());
            Assertions.assertEquals(expectedAccount,actualAccount);

            //testing correct accrued parsing
            List<Double> actualAccrued = parser.getAccrued();
            expectedAccrued.sort(Comparator.naturalOrder());
            actualAccrued.sort(Comparator.naturalOrder());
            Assertions.assertEquals(expectedAccrued,actualAccrued);

            //testing correct consumption
            List<Double> actualConsumption = parser.getConsumption();
            expectedConsumption.sort(Comparator.naturalOrder());
            actualConsumption.sort(Comparator.naturalOrder());
            Assertions.assertEquals(expectedConsumption,actualConsumption);

            //testing correct expense sheet
            Assertions.assertEquals(expectedExpenseSheet,parser.getExpenseSheet());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void TestParseStatementWithIncorrectIncomeWithoutParsingTermination() {
                List<String> movement = Arrays.asList(
                "account type,40817813206170024534,RUR,31.05.17,CRD_1U34U7,548673++++++1028    809216  /RU/CARD2CARD ALFA_MOBILE>MOSCOW          31.05.17 31.05.17 1500.00       RUR MCC6536,3000,200",
                "account type,40817813206170024534,RUR,31.05.17,CRD_1U34U7,548673++++++1028    809216  /RU/CARD2CARD ALFA_MOBILE>MOSCOW          31.05.17 31.05.17 1500.00       RUR MCC6536,900,200",
                "account type,40817813206170024534,RUR,31.05.17,CRD_5XK5TM,548673++++++1028    21708201\\RUS\\MOSCOW\\Ryabin\\KUSCHAVEL              31.05.17 29.05.17       300.00  RUR (Apple Pay-7666) MCC5814,mm,300",
                "account type,40817813206170024534,RUR,31.05.17,CRD_5XK5TM,548673++++++1028    21708201\\RUS\\MOSCOW\\Ryabin\\KUSCHAVEL              31.05.17 29.05.17       300.00  RUR (Apple Pay-7666) MCC5814,0,300"
        );
                try {
                    ByteArrayInputStream inputStream = new ByteArrayInputStream("2\n".getBytes(StandardCharsets.UTF_8));

                    Scanner scanner = new Scanner(inputStream);
                    parser.parseStatements(movement,scanner);

                    List<String> expectedAccount = Arrays.asList("809216  /RU/CARD2CARD ALFA_MOBILE>MOSCOW","809216  /RU/CARD2CARD ALFA_MOBILE>MOSCOW","21708201\\RUS\\MOSCOW\\Ryabin\\KUSCHAVEL");
                    List <Double> expectedAccrued = Arrays.asList(3000.00, 900.00, 0.0);
                    List <Double> expectedConsumption = Arrays.asList(200.00, 200.00,300.00);
                    Map<String,Double> expectedExpenseSheet = Map.of("809216  /RU/CARD2CARD ALFA_MOBILE>MOSCOW", 400.00, "21708201\\RUS\\MOSCOW\\Ryabin\\KUSCHAVEL",300.00 );

                    //testing correct account parsing
                    List<String> actualAccount = parser.getAccounts();
                    expectedAccount.sort(Comparator.naturalOrder());
                    actualAccount.sort(Comparator.naturalOrder());
                    Assertions.assertEquals(expectedAccount,actualAccount);

                    //testing correct accrued parsing
                    List<Double> actualAccrued = parser.getAccrued();
                    expectedAccrued.sort(Comparator.naturalOrder());
                    actualAccrued.sort(Comparator.naturalOrder());
                    Assertions.assertEquals(expectedAccrued,actualAccrued);

                    //testing correct consumption
                    List<Double> actualConsumption = parser.getConsumption();
                    expectedConsumption.sort(Comparator.naturalOrder());
                    actualConsumption.sort(Comparator.naturalOrder());
                    Assertions.assertEquals(expectedConsumption,actualConsumption);

                    //testing correct expense sheet
                    Assertions.assertEquals(expectedExpenseSheet,parser.getExpenseSheet());

                } catch (Exception e){
                    e.printStackTrace();
                }
    }
    @Test
    void TestParseStatementWithIncorrectIncomeAndParsingTermination() {
        List<String> movement = Arrays.asList(
                "account type,40817813206170024534,RUR,31.05.17,CRD_1U34U7,548673++++++1028    809216  /RU/CARD2CARD ALFA_MOBILE>MOSCOW          31.05.17 31.05.17 1500.00       RUR MCC6536,3000,200",
                "account type,40817813206170024534,RUR,31.05.17,CRD_1U34U7,548673++++++1028    809216  /RU/CARD2CARD ALFA_MOBILE>MOSCOW          31.05.17 31.05.17 1500.00       RUR MCC6536,900,200",
                "account type,40817813206170024534,RUR,31.05.17,CRD_5XK5TM,548673++++++1028    21708201\\RUS\\MOSCOW\\Ryabin\\KUSCHAVEL              31.05.17 29.05.17       300.00  RUR (Apple Pay-7666) MCC5814,mm,300",
                "account type,40817813206170024534,RUR,31.05.17,CRD_5XK5TM,548673++++++1028    21708201\\RUS\\MOSCOW\\Ryabin\\KUSCHAVEL              31.05.17 29.05.17       300.00  RUR (Apple Pay-7666) MCC5814,0,300"
        );
         try {
             ByteArrayInputStream inputStream = new ByteArrayInputStream("1\n".getBytes(StandardCharsets.UTF_8));

             Scanner scanner = new Scanner(inputStream);
             parser.parseStatements(movement,scanner);

             //testing correct account parsing
             Assertions.assertTrue(parser.getAccounts().isEmpty());
             //testing correct accrued parsing
             Assertions.assertTrue(parser.getAccrued().isEmpty());
             //testing correct consumption
             Assertions.assertTrue(parser.getConsumption().isEmpty());
             //testing correct expense sheet
             Assertions.assertTrue(parser.getExpenseSheet().isEmpty());
         } catch (Exception e){
             e.printStackTrace();
         }

    }

    @Test
    void TestParseStatementWithMoreThenTenIncorrectInputs (){
        List<String> movement = Arrays.asList(
                "account type,40817813206170024534,RUR,31.05.17,CRD_1U34U7,548673++++++1028    809216  /RU/CARD2CARD ALFA_MOBILE>MOSCOW          31.05.17 31.05.17 1500.00       RUR MCC6536,3000,200",
                "account type,40817813206170024534,RUR,31.05.17,CRD_1U34U7,548673++++++1028    809216  /RU/CARD2CARD ALFA_MOBILE>MOSCOW          31.05.17 31.05.17 1500.00       RUR MCC6536,900,200",
                "account type,40817813206170024534,RUR,31.05.17,CRD_5XK5TM,548673++++++1028    21708201\\RUS\\MOSCOW\\Ryabin\\KUSCHAVEL              31.05.17 29.05.17       300.00  RUR (Apple Pay-7666) MCC5814,mm,300",
                "account type,40817813206170024534,RUR,31.05.17,CRD_5XK5TM,548673++++++1028    21708201\\RUS\\MOSCOW\\Ryabin\\KUSCHAVEL              31.05.17 29.05.17       300.00  RUR (Apple Pay-7666) MCC5814,0,300"
        );
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream("f\nf\nf\nf\nf\nf\nf\nf\nf\nf\nf\n".getBytes(StandardCharsets.UTF_8));

            Scanner scanner = new Scanner(inputStream);
            parser.parseStatements(movement, scanner);

            //testing correct account parsing
            Assertions.assertTrue(parser.getAccounts().isEmpty());
            //testing correct accrued parsing
            Assertions.assertTrue(parser.getAccrued().isEmpty());
            //testing correct consumption
            Assertions.assertTrue(parser.getConsumption().isEmpty());
            //testing correct expense sheet
            Assertions.assertTrue(parser.getExpenseSheet().isEmpty());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

