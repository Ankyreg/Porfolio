package model;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
@Getter
public class Metro {

    private final Map<Line,ArrayList<Station>> stations = new HashMap();
    private final ArrayList <Line> lines = new ArrayList<>();
    private final Logger logger = LoggerFactory.getLogger(Metro.class);

    public void addLines (Line line){
        lines.add(line);
    }

    public void addLines (ArrayList<Line> line){
        lines.addAll(line);
    }

    public void addStations(Line line, ArrayList<Station> st){
        stations.put(line,st);
    }

    public void getInformationAboutConnections(){
        lines.forEach( l -> {logger.info("Line: " + l);
            l.getStations().stream().filter(s -> !s.getConnections().isEmpty()).
                    forEach(s -> {
                        logger.info("\t Station: " + s );
                        logger.info("\t\t Connections: ");
                        s.getConnections().forEach(c ->
                                logger.info(c + " on line" + c.getLine().getNum() + " - " + c.getLine().getName()));});
            logger.info("\n");
        });
    }
}
