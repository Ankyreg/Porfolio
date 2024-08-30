import java.util.Scanner;
/**
 * This class represents a financial analysis tool for a company.
 * It calculates the company's income, taxes, and investment potential.
 */
public class Main {
    private static final int minIncome = 200000;
    private static final int maxIncome = 900000;

    private static final int officeRentCharge = 140000;
    private static final int telephonyCharge = 12000;
    private static final int internetAccessCharge = 7200;

    private static final int assistantSalary = 45000;
    private static final int financeMangerSalary = 90000;

    private static final double mainTaxPercent = 0.24;
    private static final double managerPercent = 0.15;

    private static final double minInvestmentAmount = 100000;
    private static final double canInvest = minInvestmentAmount / (1 - mainTaxPercent) + calculateFixedCharges()
            / (1 - managerPercent); // go on
    private static final double breakEven = calculateFixedCharges() / (1 - managerPercent);

    /**
     * The main method that runs the financial analysis tool.
     */
    public static void main(String... a) {

        System.out.println("You can invest from this amount " + canInvest + " money. " + " Breakeven point is " + breakEven);

        while (true) {
            System.out.println("Please, type in the monthly income amount (from 200 000 to 900 000");
            int income = new Scanner(System.in).nextInt();
            if (!checkIncomeRange(income)) {
                continue;
            }
            double managerSalary = income * managerPercent;
            System.out.println("Manager's monthly salary is " + managerSalary);
            double pureIncome = income - managerSalary - calculateFixedCharges();
            System.out.println("Pure income is " + pureIncome);
            if (pureIncome < 0) {
                System.out.println("The company is at a loss!!! You must earn more money!!! Urgent!!!");
                break;
            }
            double taxAmount = pureIncome * mainTaxPercent;
            System.out.println("Tax is " + (taxAmount > 0 ? taxAmount : 0));
            double pureIncomeAfterTax = pureIncome - taxAmount;
            System.out.println("Pure income after tax deduction is " + pureIncomeAfterTax);
            boolean canMakeInvestments = pureIncomeAfterTax >= minInvestmentAmount;
            System.out.println("The company " + (canMakeInvestments ? "can invest" : "can't invest"));

            double minIncome = minInvestmentAmount + calculateFixedCharges();
            System.out.println("Breakeven point is " + minIncome);

        }

    }

    /**
     * Checks if the given income is within the valid range.
     *
     * @param income The monthly income of the company.
     * @return {@code true} if the income is within the valid range, otherwise {@code false}.
     */

    private static boolean checkIncomeRange(int income) {
        if (income < minIncome) {
            System.out.println("Income is less than the lower limit");
            return false;
        }
        if (income > maxIncome) {
            System.out.println("Income is above the bottom limit");
            return false;
        }
        return true;
    }

    /**
     * Calculates the total fixed charges of the company.
     *
     * @return The total fixed charges.
     */
    private static int calculateFixedCharges() {
        return officeRentCharge +
                telephonyCharge +
                internetAccessCharge +
                assistantSalary +
                financeMangerSalary;
    }


}
