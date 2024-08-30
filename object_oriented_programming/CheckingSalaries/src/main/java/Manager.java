public class Manager implements Employee{
    Company company;
    private final double salary;
    private final double sales;

    /**
     *The manager's salary consists of a fixed part up to 50,000 and a percentage of sales.
     *  Sales are calculated randomly up to 500,000
     * @param company
     */

    public Manager(Company company){
        this.company = company;
        sales = Math.random() * 500000;
        double percent = sales * 0.05;
        salary = 50000 + percent;
    }

    public double getSales(){
        return sales;
    }

    @Override
    public double getMonthSalary() {
        return salary;
    }
    public String toString (){
        return "Manager ";
    }
}
