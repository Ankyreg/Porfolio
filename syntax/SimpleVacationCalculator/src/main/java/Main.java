import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main {

    private static final Period workMonth = Period.ofMonths(6);
    private static final Period vocationWeeks = Period.ofWeeks(2);
    //Vocation days in Russia
    private static final LocalDate womanDay = LocalDate.of(2000, 3, 8);
    private static final LocalDate newYear = LocalDate.of(1200, 12, 31);
    private static final LocalDate newYear2 = LocalDate.of(2000, 1, 1);
    private static final LocalDate newYear3 = LocalDate.of(2000, 1, 2);
    private static final LocalDate newYear4 = LocalDate.of(2000, 1, 3);
    private static final LocalDate christmas = LocalDate.of(2000, 1, 7);
    private static final LocalDate manDay = LocalDate.of(2000, 2, 23);
    private static final LocalDate mayDay = LocalDate.of(2000, 5, 1);
    private static final LocalDate dayRussia = LocalDate.of(2000, 6, 13);

    public static void main(String... a) {

       //shift workers who need to organize vacations. the date of the last vacation is specified in the parameters
        Worker firstWorker = new Worker("Denis", LocalDate.of(2022, 10, 30));
        Worker secondWorker = new Worker("Paul", LocalDate.of(2022, 6, 30));

        //the first function
        getAllVacationsToDate(firstWorker, LocalDate.of(2025, 12, 30));
        //the second function
        checkVacationsOverlays(firstWorker, secondWorker);

    }

    /***
     * Makes a list of all dates of all vacations until a specified time and print it
     * @param worker
     * @param date
     */
    public static void getAllVacationsToDate(Worker worker, LocalDate date) {
        Worker thisWorker = worker;
        ArrayList<LocalDate> allVacations = new ArrayList<>();
        while (worker.getLastVacation().isBefore(date)) {
            allVacations.addAll(checkingHolidaysOverlays(thisWorker));
            thisWorker.setLastVacation(allVacations.get(allVacations.size() - 1));
        }
        print(allVacations, worker);

    }

    /***
     * Checks the overlap between holidays and vacations.
     * If there are holidays in the vacation period, the number
     * of days added to the vacation is as many as there were overlaps
     * @param worker
     * @return
     */

    public static ArrayList<LocalDate> checkingHolidaysOverlays(Worker worker) {
        ArrayList<LocalDate> holidays = new ArrayList<>();
        ArrayList<LocalDate> daysVacation = formListOfDaysVocation(worker);
        holidays.add(womanDay);
        holidays.add(newYear);
        holidays.add(newYear2);
        holidays.add(newYear3);
        holidays.add(newYear4);
        holidays.add(christmas);
        holidays.add(manDay);
        holidays.add(mayDay);
        holidays.add(dayRussia);
        for (LocalDate holiday : holidays) {
            for (int j = 0; j < daysVacation.size(); j++) {
                if (holiday.getDayOfMonth() == daysVacation.get(j).getDayOfMonth() && (holiday.getMonth() ==
                        daysVacation.get(j).getMonth())) {
                    LocalDate addDays = daysVacation.get(daysVacation.size() - 1).plusDays(1);
                    daysVacation.add(addDays);
                }
            }
        }
        return daysVacation;
    }

    /**
     * determines which of the shifts goes on vacation first
     * @param worker1
     * @param worker2
     */

    public static void checkVacationsOverlays(Worker worker1, Worker worker2) {
        int first;

        if (worker2.getLastVacation().isBefore(worker1.getLastVacation())) {
            first = 2;
        } else {
            int toss = (int) (Math.random() * (1 + 1)) + 1;
            if (toss == 1) {
                worker2.setLastVacation(worker1.getLastVacation());
            } else worker1.setLastVacation(worker2.getLastVacation());
            first = toss;
        }
        if (first == 1) {
            crossingCorrector(worker1,worker2);
        } else {
            crossingCorrector(worker2,worker1);
        }

    }

    /**
     * checks the overlapping of vacation days of two shift workers.
     * If there is overlap, the second employee's vacation days are shifted
     * @param worker1
     * @param worker2
     */

    private static void crossingCorrector(Worker worker1, Worker worker2) {
        ArrayList<LocalDate> workerFirst = checkingHolidaysOverlays(worker1);

        LocalDate end1 = workerFirst.get(workerFirst.size() - 1);
        LocalDate start2 = worker2.getLastVacation().plus(workMonth);
        LocalDate end2 = start2.plus(vocationWeeks);

        if (start2.isBefore(end1) && (end1.isBefore(end2) || end2.isBefore(end1))) {
            int crossingDays = Period.between(start2, end1).getDays();
            worker2.setLastVacation(worker2.getLastVacation().plusDays(crossingDays));
        }

        print(workerFirst, worker1);
        print(checkingHolidaysOverlays(worker2), worker2);
    }

    /**
     * prints all vacation dates
     * @param vocation1
     * @param worker1
     */
    private static void print(ArrayList<LocalDate> vocation1, Worker worker1) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Vacation days " + worker1.getName() + ":");
        for (LocalDate date : vocation1) {
            System.out.println(formatter.format(date));
        }
        System.out.println("The sum of Days " + vocation1.size());
    }

    /**
     * Forms a sheet of vacation dates and returns the sheet
     * @param worker
     * @return
     */
    private static ArrayList<LocalDate> formListOfDaysVocation(Worker worker) {
        ArrayList<LocalDate> daysVacation = new ArrayList<>();
        LocalDate start = worker.getLastVacation().plus(workMonth);
        LocalDate end = start.plus(vocationWeeks);
        for (int i = 0; i < Period.between(start, end).getDays(); i++) {
            LocalDate day = start.plusDays(i);
            daysVacation.add(day);
        }
        return daysVacation;
    }

}
