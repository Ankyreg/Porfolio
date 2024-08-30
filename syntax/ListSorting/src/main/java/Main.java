import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static final String STAFF_TXT = "data/staff.txt";

    public static void main(String[] args) {
        List<Employee> staff = Employee.loadStaffFromFile(STAFF_TXT);
        sortBySalaryAndAlphabet(staff);
        //after sorting
        staff.forEach(System.out::println);
        System.out.println("max salary for a specified period is");
        findMaxSalaryForPeriod(LocalDate.parse("01.01.2016",Employee.dateFormatter),
                LocalDate.parse("01.01.2018",Employee.dateFormatter),staff);

    }

    public static void  sortBySalaryAndAlphabet(List<Employee> staff) {
        staff.sort(Comparator.comparing(Employee::getSalary).thenComparing(Employee::getName));
    }
    /**
     * find the maximum salary for a specified period
     * @param dateFrom
     * @param dateTill
     * @param staff
     */
    public static void findMaxSalaryForPeriod (LocalDate dateFrom, LocalDate dateTill, List<Employee> staff){
        staff.stream().filter(employee -> employee.getWorkStart().isAfter(dateFrom) &&
                        employee.getWorkStart().isBefore(dateTill)).
                max(Comparator.comparing(Employee::getSalary)).ifPresent(System.out::println);
    }

}
