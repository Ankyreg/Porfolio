import core.Station;

import java.util.*;

public class RouteCalculator {
    private StationIndex stationIndex;

    private static double interStationDuration = 2.5;
    private static double interConnectionDuration = 3.5;

    public RouteCalculator(StationIndex stationIndex)
    {
        this.stationIndex = stationIndex;
    }

    public List<Station> getShortestRoute(Station from, Station to)
    {
        List<Station> route = getRouteOnTheLine(from, to);
        if(route != null) {
            return route;
        }

        route = getRouteWithOneConnection(from, to);
        if(route != null) {
            return route;
        }

        route = getRouteWithTwoConnections(from, to);
        return route;
    }

    /**
     * The method counts the travel time between each station depending on whether there
     * is a transition between them or not, sums it all up and returns the total time
     * @param route list
     * @return double
     */
    public static double calculateDuration(List<Station> route)
    {
        double duration = 0;
        Station previousStation = null;
        for(int i = 0; i < route.size(); i++)
        {
            Station station = route.get(i);
            if(i > 0)
            {
                duration += previousStation.getLine().equals(station.getLine()) ?
                        interStationDuration : interConnectionDuration;
            }
            previousStation = station;
        }
        return duration;
    }

    /**
     * The method creates a list of stations from one argument to the second argument,
     * provided they are on the same line. Otherwise the method returns null
     * @param from Station
     * @param to Station
     * @return list
     */
    private List<Station> getRouteOnTheLine(Station from, Station to)
    {
        if(!from.getLine().equals(to.getLine())) {
            return null;
        }
        ArrayList<Station> route = new ArrayList<>();
        List<Station> stations = from.getLine().getStations();
        int direction = 0;
        for(Station station : stations)
        {
            if(direction == 0)
            {
                if(station.equals(from)) {
                    direction = 1;
                } else if(station.equals(to)) {
                    direction = -1; // in the case of reverse direction, the direction variable changes.
                                    // And at completion the list will be reversed
                }
            }

            if(direction != 0) {
                route.add(station);
            }

            if((direction == 1 && station.equals(to)) ||
                    (direction == -1 && station.equals(from))) {
                break;
            }
        }
        if(direction == -1) {
            Collections.reverse(route);
        }
        return route;
    }

    /**
     * The method creates a list of all stations on the lines of the first argument
     * and the second argument, starting from the station "from" to the station that
     * has a connection with the line of the station "to" and all stations from "to"
     * to this connection. If the stations do not have a common connection or are on
     * the same line, the method returns null
     * @param from Station
     * @param to Station
     * @return list
     */

    private List<Station> getRouteWithOneConnection(Station from, Station to)
    {
        if(from.getLine().equals(to.getLine())) {
            return null;
        }
        ArrayList<Station> route = new ArrayList<>();

        List<Station> fromLineStations = from.getLine().getStations();
        List<Station> toLineStations = to.getLine().getStations();
        for(Station srcStation : fromLineStations)
        {
            for(Station dstStation : toLineStations)
            {
                if(isConnected(srcStation, dstStation))
                {
                    ArrayList<Station> way = new ArrayList<>();
                    way.addAll(getRouteOnTheLine(from, srcStation));
                    way.addAll(getRouteOnTheLine(dstStation, to));
                    if(route.isEmpty() || route.size() > way.size()) //если список пустой или больше чем новый ?
                    {
                        route.clear();
                        route.addAll(way);
                    }
                }
            }
        }
        if (route.isEmpty()){
            return null;
        }
        return route;
    }

    private boolean isConnected(Station station1, Station station2)
    {
        Set<Station> connected = stationIndex.getConnectedStations(station1);
        return connected.contains(station2);
    }

    /**
     *  The method checks sets connected stations of the arguments to see if they share a common line.
     *  If one and the other argument have connected stations that are on the same line, the method
     *  returns a list including these stations and the stations between them. If connected stations
     *  don't have a common line, the method returns null
     * @param from Station
     * @param to Station
     * @return List<Station>
     */

    private List<Station> getRouteViaConnectedLine(Station from, Station to)
    {
        Set<Station> fromConnected = stationIndex.getConnectedStations(from);
        Set<Station> toConnected = stationIndex.getConnectedStations(to);
        for(Station srcStation : fromConnected)
        {
            for(Station dstStation : toConnected)
            {
                if(srcStation.getLine().equals(dstStation.getLine())) {
                    return getRouteOnTheLine(srcStation, dstStation);
                }
            }
        }
        return null;
    }

    /**
     * The method returns a list of all stations, which is the route from one station
     * passed to the method to another. Stations must not be on the same line and must
     * have more than one connection otherwise the method returns null
     * @param from Station
     * @param to Station
     * @return list, null
     */
    private List<Station> getRouteWithTwoConnections(Station from, Station to)
    {
        if (from.getLine().equals(to.getLine())) {
            return null;
        }
        ArrayList<Station> route = new ArrayList<>();

        List<Station> fromLineStations = from.getLine().getStations();
        List<Station> toLineStations = to.getLine().getStations();
        for(Station srcStation : fromLineStations)
        {
            if (hasConnections(srcStation)){       // if a station doesn't have a connection, it is skipped
                continue;
            }
            for (Station dstStation : toLineStations)
            {
                if (hasConnections(dstStation)){   //only looking for stations with a connections
                    continue;
                }
                List<Station> connectedLineRoute =
                        getRouteViaConnectedLine(srcStation, dstStation);
                if(connectedLineRoute == null) {
                    continue;
                }
                ArrayList<Station> way = new ArrayList<>();
                way.addAll(getRouteOnTheLine(from, srcStation));
                way.addAll(connectedLineRoute);
                way.addAll(getRouteOnTheLine(dstStation, to));
                if(route.isEmpty() || route.size() > way.size())
                {
                    route.clear();
                    route.addAll(way);
                }
            }
        }
        return route;
    }

    private boolean hasConnections(Station station) {
        Set<Station> temp = stationIndex.getConnectedStations(station);
        return temp.isEmpty();
    }
}

