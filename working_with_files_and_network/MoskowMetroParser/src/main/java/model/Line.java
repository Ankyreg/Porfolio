package model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import java.util.ArrayList;
@Getter
public class Line implements Comparable<Line> {
    private final String num;
    private final String name;
    @JsonManagedReference
    private final ArrayList<Station> stations;

    public Line(String num, String name) {
        this.num = num;
        this.name = name;
        stations = new ArrayList<>();
    }

    public void addStation(Station station) {
        stations.add(station);
    }

    public void addStation(ArrayList<Station> station) {
        stations.addAll(station);
    }

    @Override
    public int compareTo(Line line) {
        return line.num.compareTo(line.getNum());
    }

    public String toString() {
        return getNum() + " - " + getName();
    }

}
