import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
@DisplayName("Test of CardAccount")
public class CardAccountTest {

    public static final double DELTA = 0.01;

    private static final String notExpectedSumMessage = "The amount on the account does not match the expected";
    private BankAccount cardAccount;

    @BeforeEach
    public void setUp() {
        cardAccount = new CardAccount();
    }

    @Test
    @DisplayName("Method takeMoney")
    void put() {
        cardAccount.takeMoney(10.0);
        assertEquals(10.0, cardAccount.getAmount(), DELTA, notExpectedSumMessage);
    }

    @Test
    @DisplayName("Method takeMoney,  attempt to call a method with a negative amount (balance should not change)")
    void putNegativeAmount() {
        cardAccount.takeMoney(-1.0);
        assertEquals(0.0, cardAccount.getAmount(), DELTA, notExpectedSumMessage);
    }

    @Test
    @DisplayName("Method getMoney, take=102.0, get=100.0")
    void put102take100() {
        cardAccount.takeMoney(102.0);
        cardAccount.getMoney(100.0);
        assertEquals(1.0, cardAccount.getAmount(), DELTA, notExpectedSumMessage);
    }

    @Test
    @DisplayName("Method getMoney, put=2.0, take=1.0")
    void put2take1() {
        cardAccount.takeMoney(2.0);
        cardAccount.getMoney(1.0);
        assertEquals(0.99, cardAccount.getAmount(), DELTA, notExpectedSumMessage);
    }

    @Test
    @DisplayName("Method getMoney, put=100.0, take=20.0")
    void put100take20() {
        cardAccount.takeMoney(100.0);
        cardAccount.getMoney(20.0);
        assertEquals(79.8, cardAccount.getAmount(), DELTA, notExpectedSumMessage);
    }

    @Test
    @DisplayName("Method getMoney, an attempt to withdraw more money from the account than is available in the account")
    void takeTooMuchMoney() {
        cardAccount.takeMoney(1.0);
        cardAccount.getMoney(3.0);
        assertEquals(1.0, cardAccount.getAmount(), DELTA, notExpectedSumMessage);
    }
}
