public class Operator implements Employee{
    Company company;
    private double salary;

    /**
     * Operator salary is fixed up to 30000
     * @param company
     */
    public Operator(Company company){
        this.company = company;
        salary = Math.random() * 30000;
    }

    @Override
    public double getMonthSalary() {
        return salary;
    }

    public String toString (){
        return "Operator ";
    }
}
