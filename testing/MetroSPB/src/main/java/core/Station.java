package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Station implements Comparable <Station>{
    private Line line;
    private String name;
    private Logger logger = LoggerFactory.getLogger(Line.class);

    public Station(String name, Line line)
    {
        this.name = name;
        this.line = line;
    }

    public Line getLine()
    {
        return line;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public int compareTo(Station station)
    {   if (station == null){
        logger.error("null passed");
        throw new NullPointerException("Argument is null");
    }
    int result = line.compareTo(station.getLine());
        if (result == 0){
            result = name.compareTo(station.getName());
        }
        return result;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Station)) return false;
        Station station = (Station) o;
        return Objects.equals(getLine(), station.getLine()) && Objects.equals(getName(), station.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLine(), getName());
    }


    @Override
    public String toString()
    {
        return name;
    }

}
