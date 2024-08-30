import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LegalPersonTest {

    private static final double DELTA = 0.001;
    private static final String notExpectedSumMessage = "The amount on the account does not match the expected";

    private LegalPerson legalPerson;

    @BeforeEach
    public void setUp() {
        legalPerson = new LegalPerson();
    }

    @Test
    @DisplayName("put method")
    void put() {
        legalPerson.put(1.0);
        assertEquals(1.0, legalPerson.getAmount(), DELTA, notExpectedSumMessage);
    }

    @Test
    @DisplayName("put method, attempt to call method with negative amount (balance should not change)")
    void putNegativeAmount() {
        legalPerson.put(-1.0);
        assertEquals(0.0, legalPerson.getAmount(), DELTA, notExpectedSumMessage);
    }

    @Test
    @DisplayName("take method")
    void take() {
        legalPerson.put(2.0);
        legalPerson.take(1.0);
        assertEquals(0.99, legalPerson.getAmount(), DELTA, notExpectedSumMessage);
    }

    @Test
    @DisplayName("The take method, an attempt to withdraw more money from the account than there is on the account")
    void takeTooMuchMoney() {
        legalPerson.put(1.0);
        legalPerson.take(3.0);
        assertEquals(1.0, legalPerson.getAmount(), DELTA, notExpectedSumMessage);
    }
}
