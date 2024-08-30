import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class TestSortingByDateAndAlphabet {
    @DisplayName("input from path staff.txt")
    @Test
    void sort() {
        List<Employee> actualStaff = Employee.loadStaffFromFile("data/staff.txt");
        List<Employee> expectedStaff = Employee.loadStaffFromFile("data/sortedStaff.txt");
        Main.sortBySalaryAndAlphabet(actualStaff);
        assertIterableEquals(expectedStaff, actualStaff, "sorting is not performed according to the condition");
    }
}
