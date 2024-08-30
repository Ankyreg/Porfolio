import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

public class Airport {
    private List<Terminal> terminals;
    private final Random random = new Random(1L);

    public static Airport getInstance(){
        Airport airport = new Airport();
        airport.createAirport();
        return airport;
    }

    /**
     * The method creates terminals A, B, C, D.
     * Each terminal creates a random number of aircraft of different models from 100 to 125.
     * Using the {@link Math#random()}, the purpose of the aircraft is set: a flight is created and
     * the type of flight (departure/arrival) or the aircraft is sent to the parking lot.
     * Each generated terminal is added to the list terminals.
     */

    private void createAirport (){
        terminals = new ArrayList<>();
        String [] terminals = {"A","B","C","D"};
        int aircraftCount = 100 + (int)(Math.random() * (125 - 100 + 1));
        int var2 = terminals.length;
        for (int i = 0; i < var2; ++i){
            String terminalName = terminals[i];
            Terminal terminal = new Terminal(terminalName);
            for (int j = 0; j < aircraftCount; ++j){
                double type = Math.random();
                if (type <= 0.33D){
                    terminal.addFlight(generateFlight(Flight.Type.DEPARTURE));
                } else if (type <= 0.8D){
                    terminal.addFlight(generateFlight(Flight.Type.ARRIVAL));
                } else {
                    terminal.addParkingAircraft(generateAircraft());
                }
            }
            this.terminals.add(terminal);
        }
    }

    public List<Aircraft> getAllAircraft(){
        List<Aircraft> aircraft = new ArrayList<>();
        terminals.forEach(t -> {
            t.getFlights().stream().map(Flight::getAircraft).forEach(aircraft::add); //Почему не FlatMAP??
            aircraft.addAll(t.getParkedAircrafts());
        });
        return aircraft;
    }

    private Aircraft generateAircraft(){
        String[] models = new String[]{"Boeing 737-200", "Boeing 737-800", "Boeing 777-200", "Airbus A-320",
                "Airbus A-319", "Airbus A-321"};
        String randomModel = models[random.nextInt(models.length)];
        return new Aircraft(randomModel);
    }

    private String generateFlightName(){
        String[] companyCodes = new String[]{"SU", "AA", "AR", "AF", "B2", "FV"};
        String randomCode = companyCodes[random.nextInt(companyCodes.length)];
        int randomNumber = random.nextInt(9999)+1;
        return randomCode.concat(" ").concat(String.valueOf(randomNumber));
    }

    /**
     * The method generates a random time for departure/arrival starting from the current time until the next 24 hours
     * @return
     */

    private LocalDateTime generateDate (){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime finish = now.plusHours(24);

        long startSeconds = now.toEpochSecond(ZoneOffset.UTC);
        long endSeconds = finish.toEpochSecond(ZoneOffset.UTC);

        long randomSeconds = startSeconds +  random.nextLong() % (endSeconds - startSeconds);

        return LocalDateTime.ofEpochSecond(randomSeconds,0,ZoneOffset.UTC);
    }

    private Flight generateFlight (Flight.Type type){
        return new Flight(generateFlightName(), type, generateDate(), generateAircraft());
    }


    public List<Terminal> getTerminals() {
        return new ArrayList<Terminal>(terminals);
    }

}
