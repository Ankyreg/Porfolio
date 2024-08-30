import java.time.LocalDate;
import java.util.Objects;

public class Worker {
    private final String name;
    private LocalDate lastVacation;

    public Worker(String name,LocalDate lastVacation){
        this.name = name;
        this.lastVacation = lastVacation;
    }

    public String getName (){
        return name;
    }

    public LocalDate getLastVacation(){
        return lastVacation;
    }

    public LocalDate setLastVacation(LocalDate date){
        return lastVacation = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Worker worker)) return false;
        return getName().equals(worker.getName()) && getLastVacation().equals(worker.getLastVacation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getLastVacation());
    }
}
