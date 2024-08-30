import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String... arg) {

        Airport airport = Airport.getInstance();

        System.out.println("All information");
        printAllInformation(airport);

        System.out.println("All flights in the coming hours");
        List<Flight> nextSomeHoursFlights = getNextAllFlightToTime(airport, 240);
        nextSomeHoursFlights.forEach(System.out::println);

        System.out.println("Arrival flights in the coming hours");
        AirportFlightsPerTime arrivalPerTime = new AirportFlightsPerTime(airport, Flight.Type.ARRIVAL,2);
        List<Flight> nextArrivalFlights = getNextArrivalOrDepartureFlightToTime(arrivalPerTime);
        nextArrivalFlights.forEach(System.out::println);

        System.out.println("Departure flights in the coming hours");
        AirportFlightsPerTime departurePerTime = new AirportFlightsPerTime(airport, Flight.Type.DEPARTURE,2);
        List<Flight> nextDepartureFlights = getNextArrivalOrDepartureFlightToTime(departurePerTime);
            nextDepartureFlights.forEach(System.out::println);

    }

    /**
     *The method returns all types of flights for the specified nearest hours
     * @param airport
     * @param hour
     * @return
     */

    public static List<Flight> getNextAllFlightToTime (Airport airport, int hour){
        if (checkTime(hour)){
            return new ArrayList<>(0);
        }
        ;
        LocalDateTime now = LocalDateTime.now();
        return airport.getTerminals().stream().flatMap(terminal -> terminal.getFlights().stream()
                    .filter(flight -> flight.getDate().isAfter(now) && flight.getDate()
                            .isBefore(now.plusHours(hour)))).toList();
    }

    /**
     * The method returns the specified type of flights for the specified next hours
     */

    public static List<Flight> getNextArrivalOrDepartureFlightToTime(AirportFlightsPerTime airportFlights) {
        if (checkTime(airportFlights.hour())){
            return new ArrayList<>(0);
        }
        LocalDateTime now = LocalDateTime.now();
        return airportFlights.airport().getTerminals().stream().flatMap(terminal -> terminal.getFlights().stream()
                    .filter(f -> f.getType().equals(airportFlights.type()) && f.getDate().isAfter(now) && f.getDate()
                            .isBefore(now.plusHours(airportFlights.hour())))).toList();

    }

    /**
     * Shows all the information about the airport
     * @param airport
     */

    public static void printAllInformation(Airport airport){
        airport.getTerminals().forEach( t -> {
            System.out.println("Terminal " + t.getName());
            System.out.println("Parking: ");
            t.getParkedAircrafts().forEach(System.out::println);
            System.out.println("Flights: ");
            t.getFlights().forEach(System.out::println);
        });
    }

    private static boolean checkTime (int hours){
        boolean isMoreThan24 = hours > 24;
        if (isMoreThan24){
            System.out.println("Schedule is not available at this time");
        }
        return isMoreThan24;

    }
}

