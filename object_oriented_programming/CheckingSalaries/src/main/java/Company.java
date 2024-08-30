import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Company {

    private double income;
    private List<Employee> staff = new ArrayList<>();

    public List<Employee> getStaff() {
        return new ArrayList<>(staff);
    }

    public double getIncome() {
        return income;
    }

    /**
     * After an employee leaves, sales are deducted from income
     * @param salary
     */
    private void deductionIncome(double salary) {
        income = income - salary;
    }

    /**
     * After hiring an employee, the amount of his salary is taken from the income and the amount of sales is added
     * @param employee
     */
    public void hire (Employee employee){
        income += employee.getSales();
        staff.add(employee);
        deductionIncome(employee.getMonthSalary());
    }

    public void hireAll(Collection<Employee> employees){
        for (Employee e : employees){
            hire(e);
        }
    }

    /**
     * method of deleting from the list of employees. Removal from the list occurs randomly. If the number passed to
     * the method exceeds the total number of employees, an error message occurs.
     * @param employee
     */
    public void fire(int employee){
        if (staff.size() < employee){
            System.out.println("There are not so many people in the company");
        } else {
            for (int i = 0; i < employee; i++){
               Employee empl = staff.get((int) (Math.random() * staff.size()));
               deductionIncome(empl.getSales());
               income += empl.getMonthSalary();
               staff.remove(empl);
            }
        }
    }

    /**
     * method returns the list with the highest salaries. If a number greater than the total number of employees
     * is entered in the parameter, the method throws an exception
     * @param count
     * @return
     * @throws Exception
     */
   public ArrayList<Employee> getTopSalaryStaff(int count) throws Exception{
        if (count > staff.size() || staff.isEmpty() || count == 0){
         throw new Exception("No employee");
        } else {
            ArrayList <Employee> top = new ArrayList<>();
            staff.sort(new SalariesComparator().reversed());
            for (int i = 0; i < count; i++){
                top.add(staff.get(i));
            }
            return top;
        }
   }

    /**
     * the method returns the sheet with the lowest salaries. If a number greater than the total number of employees
     * is entered in the parameter, the method throws an exception
     * @param count
     * @return
     * @throws RuntimeException
     */
    public ArrayList<Employee> getLowestSalaryStaff(int count) throws RuntimeException{
        if (count > staff.size() || staff.isEmpty() || count == 0) {
            throw new RuntimeException("No employee");
        } else {
            ArrayList<Employee> low = new ArrayList<>();
            staff.sort(new SalariesComparator());
            for (int i = 0; i < count; i++) {
                low.add(staff.get(i));
            }
            return low;
        }
    }
}
