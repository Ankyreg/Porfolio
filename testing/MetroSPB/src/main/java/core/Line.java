package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Line implements Comparable<Line>{
    private final int number;
    private final String name;
    private final List<Station> stations;
    private Logger logger = LoggerFactory.getLogger(Line.class);

    public Line(int number, String name) {
        this.number = number;
        this.name = name;
        stations = new ArrayList<>();
    }

    public int getNumber()
    {
        return number;
    }

    public String getName()
    {
        return name;
    }

    public void addStation(Station station)
    {
        stations.add(station);
    }

    public List<Station> getStations()
    {
        return stations;
    }

    public int compareTo(Line line)
    {
        if(line == null){
            logger.error("null passed");
            throw new NullPointerException("Argument i null");
        }
        return Integer.compare(number, line.getNumber());
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof Line)) {
            logger.info("an invalid argument was passed: " + obj.toString());
            return false;}
        Line line = (Line) obj;
        return Objects.equals(number,line.getNumber()) && Objects.equals(name, line.getName());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getName());
    }
}
