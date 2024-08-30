public interface Employee {
    double getMonthSalary();
    default double getSales(){
        return 0;
    }
}
