import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@ToString(exclude = {"workStart"})
public class Employee {

    private String name;
    private Integer salary;
    private LocalDate workStart;

    protected static DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");


    public static List <Employee> loadStaffFromFile(String path) {
        List<Employee> staff = new ArrayList<>();
        try {
            Files.lines(Paths.get(path)).forEach(l -> {
                String[] fragment = l.split("_");
                if (fragment.length != 3) {
                    System.err.println("Wrong line : + l");
                } else {
                    staff.add(new Employee(fragment[0],
                            Integer.parseInt(fragment[1]),
                            LocalDate.parse(fragment[2], dateFormatter)));
            }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return staff;
    }
}
