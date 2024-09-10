package model;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Objects;

@Getter
public class Station implements Comparable<Station>{
    @JsonBackReference
    private final Line line;
    private final String name;
    private final String id;
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private final HashSet<Station> connections = new HashSet<>();


    public Station (String name, Line line, String id){
        this.line = line;
        this.name = name;
        this.id = id;
    }

    public void adConnection(Station station){
        connections.add(station);
    }

    @Override
    public int compareTo(Station o) {
        int lineComparison = line.compareTo(o.getLine());
        if(lineComparison != 0) {
            return lineComparison;
        }
        return name.compareToIgnoreCase(o.getName());
    }
    public String toString(){
        return name;
    }

    public boolean equals (Object o){
        if (o == null){
            return false;
        }
        if (o == this){
            return true;
        }
        if (!(o instanceof Station station1)){
            return false;
        }
        return  station1.getId().equals(id)&&station1.getName().equals(name)&&station1.getLine().toString().equals(line.toString());
    }
    public int hashCode (){
        return Objects.hash(id,name,line.toString());
    }
}