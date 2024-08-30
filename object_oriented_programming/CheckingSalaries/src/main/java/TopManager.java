public class TopManager implements Employee{
    Company company;
    private final double salariesWithoutPercent;
    private final double salariesWithPercent;
    private final double PREMIUM = 100000;

    /**
     * The salary of a top manager consists of a fixed part up to 70,000 and a bonus of 100,000
     * @param company
     */

    public TopManager (Company company){
        this.company = company;
        salariesWithoutPercent = Math.random() * 70000;
        salariesWithPercent = salariesWithoutPercent + PREMIUM;
    }

    /**
     * if the company's income exceeds 10 million, the method returns the salary with a bonus,
     * if less, then only a fixed part
     * @return
     */
    public double getMonthSalary() {
        if (company.getIncome() > 10000000) {
            return salariesWithPercent;
        } else {
            return salariesWithoutPercent;
        }
    }
    public String toString (){
        return "TopManager ";
    }
}
