package StrazPozarna.InputOutput;

import StrazPozarna.GraphEdge.Route;
import StrazPozarna.GraphVertex.City;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class JsonIOHandler {
    private List<City> citiesList;
    private List<Route> routesList;
    private Long timeout;
    private Long maxDrivingTime;

    public JsonIOHandler(String source) throws FileNotFoundException {
        try {
            loadDataFromFile(source);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Incorrect data in input file.");
        } catch (IOException e) {
            throw new FileNotFoundException("File was not found under given path.");
        }
    }

    private void loadDataFromFile(String source) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        citiesList = new ArrayList<>();
        routesList = new ArrayList<>();
        Object obj = parser.parse(new FileReader(source));

        JSONObject jsonObject = (JSONObject) obj;

        timeout = (Long) jsonObject.get("timeout");
        maxDrivingTime = (Long) jsonObject.get("max_driving_time");

        JSONArray cities = (JSONArray) jsonObject.get("cities");

        cities.forEach(it -> citiesList.add(new City((String) it)));

        JSONArray routes = (JSONArray) jsonObject.get("routes");
        for (JSONObject jsonRoute : (Iterable<JSONObject>) routes) {
            JSONArray fromTo = (JSONArray) jsonRoute.get("cities");
            City from = new City((String) fromTo.get(0));
            City to = new City((String) fromTo.get(1));
            Long travelTime = (Long) jsonRoute.get("driving_time");
            Route route = new Route(from, to, travelTime);
            routesList.add(route);
        }
    }

    public void writeDataToFile(String destination, Set<City> minimalCitiesSet) {
        JSONObject obj = new JSONObject();

        JSONArray list = new JSONArray();
        for (City city : minimalCitiesSet) {
            list.add(city.getName());
        }

        obj.put("minimal_cities_set", list);

        try (FileWriter file = new FileWriter(destination)) {
            file.write(obj.toJSONString());
            file.close();
        } catch (IOException e) {
            System.out.println("Incorrect destination file path.");
        }
    }


    public List<City> getCitiesList() {
        return citiesList;
    }

    public List<Route> getRoutesList() {
        return routesList;
    }

    public Long getTimeoutValue() {
        return timeout;
    }

    public Long getMaxTravelTime() {
        return maxDrivingTime;
    }

}


