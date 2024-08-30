import core.Line;
import core.Station;

import java.util.*;
import java.util.stream.Collectors;

public class StationIndex {
    private final HashMap<Integer, Line> numberAndLine;
    private final TreeMap<Station, TreeSet<Station>> connections;
    private final TreeSet<Station> stations;

    public StationIndex()
    {
        numberAndLine = new HashMap<>();
        stations = new TreeSet<>();
        connections = new TreeMap<>();

    }

    public void addStation(Station station)
    {
        stations.add(station);
    }

    public void addLine(Line line)
    {
        numberAndLine.put(line.getNumber(), line);
    }

    public Line getLine(int number)
    {
        return numberAndLine.get(number);
    }
    public Station getStation(String name)
    {
        for(Station station : stations)
        {
            if(station.getName().equalsIgnoreCase(name)) {
                return station;
            }
        }
        return null;
    }
    public Station getStation(String name, int lineNumber)
    {
        Station query = new Station(name, getLine(lineNumber));
        Station station = stations.ceiling(query);
        return station.equals(query) ? station : null;
    }

    /**
     * method returns the set of stations that have a connection with the passed argument
     * @param station
     * @return Set
     */

    public Set<Station> getConnectedStations(Station station)
    {
        if(connections.containsKey(station)) {
            return connections.get(station);
        }
        return new TreeSet<>();
    }

    /**
     * for each station in the list the method creates a Set with those stations that have transition with it
     * @param stations
     */
    public void addConnection(List<Station> stations)
    {
        for(Station station : stations)
        {
            if(!connections.containsKey(station)) {
                connections.put(station, new TreeSet<>());
            }
            TreeSet<Station> connectedStations = connections.get(station);
            connectedStations.addAll(stations.stream()
                    .filter(s -> !s.equals(station)).toList());
        }
    }
    public List<Station> getList(String... names){
        List <Station> stations = Arrays.stream(names).map(this::getStation).collect(Collectors.toList());
        return stations;
    }
}
