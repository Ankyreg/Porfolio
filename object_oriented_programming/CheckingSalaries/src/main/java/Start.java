import java.util.ArrayList;
import java.util.List;

public class Start {
    public static void main (String ... args) throws Exception {
        Company durex = new Company();
        System.out.println("after hiring operators");
        durex.hireAll(createListEmployees(5, new Operator(durex)));
        checkIncome(durex);

        System.out.println("after hiring managers");
        durex.hireAll(createListEmployees(10, new Manager(durex)));
        checkIncome(durex);

        System.out.println("after hiring top managers");
        durex.hireAll(createListEmployees(4, new TopManager(durex)));
        checkIncome(durex);

        System.out.println("all employees");
        employeeCount(durex.getStaff());

        System.out.println("after hiring operators");
        durex.hireAll(createListEmployees(6,new Operator(durex)));
        checkIncome(durex);

        System.out.println("Top salaries");
        ArrayList <Employee> topSalary = durex.getTopSalaryStaff(3);
        for (Employee e : topSalary){
            System.out.println("Employee " + e + "Salary " + String.format("%,.1f",e.getMonthSalary()));
        }

        System.out.println("Lowest salaries");
        ArrayList <Employee> lowSalary = durex.getLowestSalaryStaff(3);
        for (Employee e : lowSalary){
            System.out.println("Employee " + e + "Salary " + String.format("%,.1f", e.getMonthSalary()));
        }

        durex.fire(durex.getStaff().size()/2);
        System.out.println("Income of the company after firing 50% employees");
        checkIncome(durex);

        System.out.println("remaining employees");
        employeeCount(durex.getStaff());

    }

    /**
     * The method counts and prints how many employees of a particular type are in the company and their total salaries
     * @param employees
     */
    private static void employeeCount (List<Employee> employees){
        int operators = 0;
        double sumSalaryOperator = 0.0;
        int managers = 0;
        double sumSalaryManager = 0.0;
        int topManagers = 0;
        double sumSalaryTopManager = 0.0;
        for (Employee e : employees){
            if (e instanceof Operator){
                operators += 1;
                sumSalaryOperator += e.getMonthSalary();
            }
            if (e instanceof Manager){
                managers += 1;
                sumSalaryManager += e.getMonthSalary();
            }
            if (e instanceof TopManager){
                topManagers += 1;
                sumSalaryTopManager += e.getMonthSalary();
            }
        }
        if (operators > 0){
            System.out.println("The company has " + operators + " operators. Total salary: " + String.format("%,.2f",
                    sumSalaryOperator));
        }
        if (managers > 0){
            System.out.println("The company has " + managers + " managers. Total salary: "
                    + String.format("%,.2f",sumSalaryManager));
        }
        if (topManagers > 0){
            System.out.println("The company has " + topManagers + " top managers. Total salary: "
                    + String.format("%,.2f",sumSalaryTopManager));
        }

    }

    /**
     * the method returns a list with the specified employee value
     * @param count
     * @param employee
     * @return
     */
    private static ArrayList<Employee> createListEmployees (int count, Employee employee){
        ArrayList <Employee> employees = new ArrayList<>();
        for (int i = 0; i < count; i++){
            employees.add(employee);
        }
        return employees;
    }

    private static void checkIncome (Company company){
        System.out.println("Income of the company " + String.format("%,.2f",company.getIncome()));
    }
}
