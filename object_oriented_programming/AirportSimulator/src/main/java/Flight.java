import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Flight {
    private String code;
    private Flight.Type type;
    private LocalDateTime date;
    private Aircraft aircraft;

    public Flight(String code, Flight.Type type, LocalDateTime date, Aircraft aircraft) {
        this.code = code;
        this.type = type;
        this.date = date;
        this.aircraft = aircraft;
    }

    public String getCode() {
        return this.code;
    }

    public Flight.Type getType() {
        return this.type;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public Aircraft getAircraft() {
        return this.aircraft;
    }

    public String toString() {
        String var10000 = date.format(DateTimeFormatter.ofPattern("dd.MM hh:mm"));
        return var10000 + " / " + this.code + " / " + this.type;
    }

    public static enum Type {
        ARRIVAL,
        DEPARTURE;
    }
}
