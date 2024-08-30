import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.Calendar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test of DepositAccount")
public class DepositAccountTest {

    public static final double DELTA = 0.001;

    private static final String notExpectedSumMessage = "The amount on the account does not match the expected";
    private BankAccount depositAccount;

    @BeforeEach
    public void setUp() {
        depositAccount = new DepositAccount();
    }

    @Test
    @DisplayName("Method takeMoney")
    void put() {
        depositAccount.takeMoney(1.0);
        assertEquals(1.0, depositAccount.getAmount(), DELTA, notExpectedSumMessage);
    }

    @Test
    @DisplayName("Method takeMoney,attempt to call a method with a negative amount (balance should not change)")
    void putNegativeAmount() {
        depositAccount.takeMoney(-1.0);
        assertEquals(0.0, depositAccount.getAmount(), DELTA, notExpectedSumMessage);
    }


    @Test
    @DisplayName("Method getMoney, attempt to withdraw money a month after replenishment")
    void takeInMonth() {
        depositAccount.takeMoney(2.0);
        rollBackTime(0, 1, 1);
        depositAccount.getMoney(1.0);
        assertEquals(1.0, depositAccount.getAmount(), DELTA, notExpectedSumMessage);
    }

    @Test
    @DisplayName("Method getMoney, attempt to withdraw money a year after replenishment")
    void takeInYear() {
        depositAccount.takeMoney(2.0);
        rollBackTime(1, 0, 0);
        depositAccount.getMoney(1.0);
        assertEquals(1.0, depositAccount.getAmount(), DELTA, notExpectedSumMessage);
    }

    @Test
    @DisplayName("Method getMoney, an attempt to withdraw more money from the account than is available in the account")
    void takeTooMuchMoney() {
        depositAccount.takeMoney(2.0);
        rollBackTime(0, 1, 1);
        depositAccount.getMoney(3.0);
        assertEquals(2.0, depositAccount.getAmount(), DELTA, notExpectedSumMessage);
    }

    @Test
    @DisplayName("Method getMoney, attempt to withdraw money from the account less than a month after crediting")
    void takeNow() {
        depositAccount.takeMoney(100.0);
        depositAccount.getMoney(3.0);
        assertEquals(100.0, depositAccount.getAmount(), DELTA, notExpectedSumMessage);
    }

    private void rollBackTime(int year, int months, int days) {
        try {
            Field field = depositAccount.getClass().getDeclaredField("lastIncome");

            if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
                throw new IllegalAccessException();
            }

            field.setAccessible(true);

            if (field.getAnnotatedType().getType().getTypeName().equals("java.util.Calendar")) {
                Calendar future = Calendar.getInstance();
                future.add(Calendar.YEAR, -year);
                future.add(Calendar.MONTH, -months);
                future.add(Calendar.DAY_OF_YEAR, -days);
                field.set(depositAccount, future);
            } else if (field.getAnnotatedType().getType().getTypeName().equals("java.time.LocalDate")) {
                field.set(depositAccount, LocalDate.now().minusYears(year).minusMonths(months).minusDays(days));
            } else {
                Assertions.fail("The class is missing a lastIncome field of type LocalDate or Calendar");
            }

        } catch (NoSuchFieldException e) {
            Assertions.fail("LastIncome field not found in DepositAccount class.");
        } catch (IllegalAccessException e) {
            Assertions.fail("lastIncome is private");
        }
    }
}
