import java.util.ArrayList;
import java.util.List;

public class Terminal {
    private String name;
    private List<Flight> flights;
    private List<Aircraft> parkedAircrafts;

    public Terminal(String name) {
        this.name = name;
        this.flights = new ArrayList();
        this.parkedAircrafts = new ArrayList();
    }

    public String getName() {
        return this.name;
    }

    public List<Flight> getFlights() {
        return new ArrayList<>(flights);
    }

    public List<Aircraft> getParkedAircrafts() {
        return new ArrayList<>(parkedAircrafts);
    }

    public void addFlight(Flight flight) {
        this.flights.add(flight);
    }

    public void addParkingAircraft(Aircraft aircraft) {
        this.parkedAircrafts.add(aircraft);
    }
}
