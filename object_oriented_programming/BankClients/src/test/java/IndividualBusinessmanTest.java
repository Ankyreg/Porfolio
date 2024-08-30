import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IndividualBusinessmanTest {

    private static final double DELTA = 0.01;
    private static final String notExpectedSumMessage = "The amount on the account does not match the expected";

    private IndividualBusinessman individualBusinessman;

    @BeforeEach
    public void setUp() {
        individualBusinessman = new IndividualBusinessman();
    }

    @Test
    @DisplayName("put method, attempt to put an amount less than 1000")
    void putLess1000() {
        individualBusinessman.put(500.0);
        assertEquals(495.0, individualBusinessman.getAmount(), DELTA, notExpectedSumMessage);
    }

    @Test
    @DisplayName("put method, attempt to put 1000")
    void put1000() {
        individualBusinessman.put(1000.0);
        assertEquals(995.0, individualBusinessman.getAmount(), DELTA, notExpectedSumMessage);
    }

    @Test
    @DisplayName("put method, attempt to put an amount over 1000")
    void putMore1000() {
        individualBusinessman.put(2000.0);
        assertEquals(1990.0, individualBusinessman.getAmount(), DELTA, notExpectedSumMessage);
    }

    @Test
    @DisplayName("put method, attempt to call method with negative amount (balance should not change)")
    void putNegativeAmount() {
        individualBusinessman.put(-1.0);
        assertEquals(0.0, individualBusinessman.getAmount(), DELTA, notExpectedSumMessage);
    }

    @Test
    @DisplayName("take method")
    void take() {
        individualBusinessman.put(500.0); //expected 495
        individualBusinessman.take(1.0);
        assertEquals(494.0, individualBusinessman.getAmount(), DELTA, notExpectedSumMessage);
    }

    @Test
    @DisplayName("The take method, an attempt to withdraw more money from the account than there is on the account")
    void takeTooMuchMoney() {
        individualBusinessman.put(500.0); //expected 495
        individualBusinessman.take(496.0);
        assertEquals(495.0, individualBusinessman.getAmount(), DELTA, notExpectedSumMessage);
    }
}
