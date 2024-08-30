import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhysicalPersonTest {

    private static final double DELTA = 0.01;
    private static final String notExpectedSumMessage = "The amount on the account does not match the expected";

    private PhysicalPerson physicalPerson;

    @BeforeEach
    public void setUp() {
        physicalPerson = new PhysicalPerson();
    }

    @Test
    @DisplayName("put method")
    void put() {
        physicalPerson.put(1.0);
        assertEquals(1.0, physicalPerson.getAmount(), DELTA, notExpectedSumMessage);
    }

    @Test
    @DisplayName("put method, attempt to call method with negative amount (balance should not change)")
    void putNegativeAmount() {
        physicalPerson.put(-1.0);
        assertEquals(0.0, physicalPerson.getAmount(), DELTA, notExpectedSumMessage);
    }


    @Test
    @DisplayName("take method")
    void take() {
        physicalPerson.put(2.0);
        physicalPerson.take(1.0);
        assertEquals(1.0, physicalPerson.getAmount(), DELTA, notExpectedSumMessage);
    }

    @Test
    @DisplayName("The take method, an attempt to withdraw more money from the account than there is on the account")
    void takeTooMuchMoney() {
        physicalPerson.put(2.0);
        physicalPerson.take(3.0);
        assertEquals(2.0, physicalPerson.getAmount(), DELTA, notExpectedSumMessage);
    }
}
