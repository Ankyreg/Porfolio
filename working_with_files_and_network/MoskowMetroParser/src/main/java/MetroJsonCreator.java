import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import model.Line;
import model.Metro;
import model.Station;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Slf4j
public class MetroJsonCreator {

    private final Path path = Path.of("src/main/resources/metro.html");
    private final Path metroJsonPath = Path.of("src/main/resources/mos.json");
    private Document metroHtml;
    private final Metro moscowMetro = new Metro();
    private final ObjectMapper mapper = new ObjectMapper();
    private final HashMap<String, Station> stations = new HashMap<>();

    public MetroJsonCreator() {
        log.debug("Initialization");
        try {
            metroHtml = Jsoup.parse(path.toFile(), "UTF-8");
            log.debug("Initialization finished");
        } catch (IOException e) {
            log.error("document cannot be read " + e);
            log.info("There's something wrong with the file. Check whether it is in the specified directory and whether" +
                    " it is being used by another program");
        }
    }

    public void createMetro() throws IOException {
        lineParser();
        connectionParse();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(moscowMetro);
        try {
            Files.writeString(metroJsonPath, json);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void lineParser() {
        log.debug("Start line parsing");
        Elements lineName = metroHtml.select("span[data-line]");
        ArrayList<Line> lines = new ArrayList<>();
        for (Element e : lineName) {
            String num = e.attr("data-line");
            String name = e.text();
            log.debug(num + " " + name + " was created");
            Line line = new Line(num, name);
            line.addStation(stationParser(line));
            lines.add(line);
        }
        moscowMetro.addLines(lines);
    }

    private ArrayList<Station> stationParser(Line line) {
        Elements lineStation = metroHtml.select("div[data-line=" + line.getNum() + "]");
        ArrayList<Station> stations = new ArrayList<>();
        for (Element e : lineStation) {
            Elements stationNames = e.select("a.listingMetrost_st");
            for (Element el2 : stationNames) {
                String[] stationName = el2.text().split("\\. ");
                String idLine = el2.attr("data-metrost");
                String[] id = idLine.split(",");
                String num = id[0];
                Station station = new Station(stationName[1], line, "s" + num);
                stations.add(station);
                this.stations.put(station.getId(), station);
            }
            moscowMetro.addStations(line, stations);
        }
        return stations;
    }

    public void connectionParse() {
        log.debug("Start parsing connections");
        Element element = metroHtml.getElementById("Connections");
        Elements elements = element.select("line");
        connectionsElementsParsing(elements);
        Elements elements1 = element.select("path");
        connectionsElementsParsing(elements1);
    }

    private void connectionsElementsParsing(Elements elements) {
        for (Element e : elements) {
            String connect = e.attr("class");
            String[] ids = connect.split(" ");
            int counter = 0;
            if (ids.length <= 1) {
                log.error("string parsing is not possible " + e);
            } else {
                while (counter != ids.length) {
                    for (String id : ids) {
                        stations.get(ids[counter]).adConnection(stations.get(id));
                    }
                    counter += 1;
                }
            }
        }
    }

    public void getInformation() {
        moscowMetro.getInformationAboutConnections();
    }
}