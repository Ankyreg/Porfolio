import core.Line;
import core.Station;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.List;
import java.util.Scanner;

public class Main {
   private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Main.class);
   private static final Marker INPUT_HISTORY = MarkerFactory.getMarker("INPUT_HISTORY");
   private static final Marker INVALID_STATIONS = MarkerFactory.getMarker("INVALID_STATIONS");
   private static Scanner scanner;
   private static StationIndex stationIndex;

    public static void main (String ... a){

       logger.info(INPUT_HISTORY, "Program started");
       stationIndex = MapParser.createStationIndex();
       RouteCalculator routeCalculator = new RouteCalculator(stationIndex);

       System.out.println("Program for calculating the routes of the St.Petersburg metro\n");
       scanner = new Scanner(System.in);
       for (;;){
          try {
             Station from = takeStation("Enter the station of departure:");
             Station to = takeStation("Enter the station of departure:");
             List<Station> route = routeCalculator.getShortestRoute(from, to);
             System.out.println("Route:");
             printRoute(route);
             System.out.println("Duration:" + RouteCalculator.calculateDuration(route) + " minutes");

          }catch (Exception ex){
             logger.error(ex.getMessage());
             ex.printStackTrace();
          }
       }
    }

   private static Station takeStation(String message ) {
      for(;;)
      {
         System.out.println(message);
         String line = scanner.nextLine().trim();
         Station station = stationIndex.getStation(line);
         if(station != null) {
            logger.info(INPUT_HISTORY,"Found station:" + station );
            System.out.println("Found station: " + station);
            return station;
         }
         logger.info(INVALID_STATIONS, "Station not found " + line);
         System.out.println("Station not found " + line);
      }
   }

   private static void printRoute(List<Station> route)
   {
      Station previousStation = null;
      for(Station station : route)
      {
         if(previousStation != null)
         {
            Line prevLine = previousStation.getLine();
            Line nextLine = station.getLine();
            if(!prevLine.equals(nextLine))
            {
               System.out.println("\tTransfer to the station " +
                       station.getName() + " (" + nextLine.getName() + " line)");
            }
         }
         System.out.println("\t" + station.getName());
         previousStation = station;
      }
   }
}
