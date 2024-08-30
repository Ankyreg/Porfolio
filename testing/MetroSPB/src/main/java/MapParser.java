import core.Line;
import core.Station;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *  The class parses a Json file and creates a StationIndex object and
 *  fills it with the created objects : Line, Station, Connection
 */
public class MapParser {
    private static StationIndex stationIndex;

    private static final String dataFile = "src/main/resources/map.json";
    private static final Logger logger = LoggerFactory.getLogger(MapParser.class);
    private static final Marker PARSING_DEBUG = MarkerFactory.getMarker("PARSING_DEBUG");


    /**
     * The method fills StationIndex object collections with Lines
     * and Station objects from JSON file
     * @return
     */
    public static StationIndex createStationIndex()
    {
        stationIndex = new StationIndex();
        try
        {
            JSONParser parser = new JSONParser();
            JSONObject jsonData = (JSONObject) parser.parse(getJsonFile());

            JSONArray linesArray = (JSONArray) jsonData.get("lines");
            parseLines(linesArray);

            JSONObject stationsObject = (JSONObject) jsonData.get("stations");
            parseStations(stationsObject);

            JSONArray connectionsArray = (JSONArray) jsonData.get("connections");
            parseConnections(connectionsArray);
        }
        catch(Exception ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        logger.debug(PARSING_DEBUG, "StationIndex was created");
        return stationIndex;
    }

    /**
     *The method reads JSON objects Connection creates Station objects and adds intersecting pairs
     * to the list and passes it to the addConnection method of the StationIndex object
     * @param connectionsArray
     */

    private static void parseConnections(JSONArray connectionsArray)
    {
        connectionsArray.forEach(connectionObject ->
        {
            JSONArray connection = (JSONArray) connectionObject;
            List<Station> connectionStations = new ArrayList<>();
            connection.forEach(item ->
            {
                JSONObject itemObject = (JSONObject) item;
                int lineNumber = ((Long) itemObject.get("line")).intValue();
                String stationName = (String) itemObject.get("station");
                Station station = stationIndex.getStation(stationName, lineNumber);
                if(station == null)
                {
                    logger.error(stationName + " on line " + lineNumber + " doesn't exist");
                    throw new IllegalArgumentException("core.Station " +
                            stationName + " on line " + lineNumber + " not found");
                }
                connectionStations.add(station);
            });
            stationIndex.addConnection(connectionStations);
        });

    }

    /**The method creates a Station object from a JsonObject and adds each object to the
     * TreeSet of the StationIndex object and populates the lists of Line objects with Station
     * objects corresponding to the Line object number
     * @param stationsObject
     */
    private static void parseStations(JSONObject stationsObject)
    {
        stationsObject.keySet().forEach(lineNumberObject ->
        {
            int lineNumber = Integer.parseInt((String) lineNumberObject);
            Line line = stationIndex.getLine(lineNumber);
            JSONArray stationsArray = (JSONArray) stationsObject.get(lineNumberObject);
            stationsArray.forEach(stationObject ->
            {
                Station station = new Station((String) stationObject, line);
                stationIndex.addStation(station);
                logger.debug(PARSING_DEBUG, "Station " + station.getName() + " on " + station.getLine().getName()
                        + " is added into stationIndex" );
                line.addStation(station);
            });
        });

    }

    /**
     * method reads an array of lines and creates Line objects (only name and number fields). All objects
     * are added to the HashMap of the StationIndex object
     * @param linesArray
     */

    private static void parseLines(JSONArray linesArray)
    {
      linesArray.forEach(lineObject -> {
           JSONObject lineJsonObject = (JSONObject) lineObject;
           Line line = new Line (
                  ((Long) lineJsonObject.get("number")).intValue(),
                    (String) lineJsonObject.get("name")
            );
            stationIndex.addLine(line);
            logger.debug(PARSING_DEBUG,"Line " + line.getName() + " " + line.getNumber()
                    + " was added into StationIndex");
       });

    }

    private static String getJsonFile()
    {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(dataFile));
            lines.forEach(builder::append);
        }
        catch (Exception ex) {
            logger.debug(PARSING_DEBUG, "Unable to read file");
            ex.printStackTrace();
      }
        logger.debug(PARSING_DEBUG,"JSON file is read successfully");
      return builder.toString();
  }
}
