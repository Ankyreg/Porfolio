import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test BankAccount")
public class BankAccountTest {

    public static final double DELTA = 0.01;

    private static final String notExpectedSumMessage = "The amount on the account does not match the expected";
    private BankAccount bankAccount;

    @BeforeEach
    public void setUp() {
        bankAccount = new BankAccount();
    }

    @Test
    @DisplayName("Method takeMoney")
    void put() {
        bankAccount.takeMoney(1.0);
        assertEquals(1.0, bankAccount.getAmount(), DELTA, notExpectedSumMessage);
    }

    @Test
    @DisplayName("Method takeMoney, attempt to call a method with a negative amount (balance should not change)")
    void putNegativeAmount() {
        bankAccount.takeMoney(-1.0);
        assertEquals(0.0, bankAccount.getAmount(), DELTA, notExpectedSumMessage);
    }

    @Test
    @DisplayName("Method getMoney")
    void take() {
        bankAccount.takeMoney(2.0);
        bankAccount.getMoney(1.0);
        assertEquals(1.0, bankAccount.getAmount(), DELTA, notExpectedSumMessage);
    }

    @Test
    @DisplayName("Method getMoney,an attempt to withdraw more money from the account than is available in the account")
    void takeTooMuchMoney() {
        bankAccount.takeMoney(2.0);
        bankAccount.getMoney(3.0);
        assertEquals(2.0, bankAccount.getAmount(), DELTA, notExpectedSumMessage);
    }
}
