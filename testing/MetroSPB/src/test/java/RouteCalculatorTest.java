import core.Line;
import core.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouteCalculatorTest {
    List<Station> route;
    StationIndex stationIndexTest;
    RouteCalculator routeCalculatorTest;
    List<Station> connect;
    List<Station> connect2;

    @BeforeEach
    void setUp (){
        stationIndexTest = new StationIndex();
        routeCalculatorTest = new RouteCalculator(stationIndexTest);
        route = new ArrayList<>();
        Line lineRed = new Line(10, "firstLine");
        Line lineGreen = new Line(12, "secondLine");
        route.add(new Station("primorskaya", lineRed));
        route.add(new Station("proskoskogo",lineRed));
        route.add(new Station("velosy",lineGreen));
        route.add(new Station("red Village",lineGreen));

        // adding the lines and the stations to StationIndex
        int num = 1;
        for(int i = 1; i < route.size(); i++){
            stationIndexTest.addLine(new Line(i,"Line"+i));
            for (int j = 1; j < 4; j++){
                Station temp = new Station("Station"+ num, stationIndexTest.getLine(i));
                stationIndexTest.getLine(i).addStation(temp);
                stationIndexTest.addStation(temp);
                num +=1;
            }
        }
        //adding connections
        connect = new ArrayList<>(stationIndexTest.getList("Station1","Station4"));
        connect2 = new ArrayList<>(stationIndexTest.getList("Station5","Station7"));

        stationIndexTest.addConnection(connect);
        stationIndexTest.addConnection(connect2);
    }
    @Test
     void getShortestRoutTestOnTheSameLine(){
        List<Station> expected = new ArrayList<>(stationIndexTest.getList("Station1", "Station2", "Station3"));
        List<Station> actual = routeCalculatorTest.getShortestRoute(stationIndexTest.getStation("Station1"),
                stationIndexTest.getStation("Station3"));
        assertEquals(expected,actual);
     }

     @Test
    void getShortestRoutTestWithOneChange(){
         List<Station> expected2 = new ArrayList<>(stationIndexTest.getList("Station1", "Station4", "Station5"));
         List<Station> actual2 = routeCalculatorTest.getShortestRoute(stationIndexTest.getStation("Station1"),
                 stationIndexTest.getStation("Station5"));

         assertEquals(expected2,actual2);
     }

     @Test
    void getShortestRoutTestWithTwoChange(){
         List<Station> expected3 = new ArrayList<>(stationIndexTest.getList("Station1", "Station4", "Station5",
                 "Station7"));
         List<Station> actual3 = routeCalculatorTest.getShortestRoute(stationIndexTest.getStation("Station1"),
                 stationIndexTest.getStation("Station7"));

         assertEquals(expected3,actual3);
     }

     @Test
    void calculateDurationTest(){
         double actual = RouteCalculator.calculateDuration(route);
         double expected = 8.5;
         assertEquals(expected,actual);
     }



}
