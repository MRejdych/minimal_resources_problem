package app;

import StrazPozarna.GraphEdge.Route;
import StrazPozarna.GraphVertex.City;
import StrazPozarna.ReachableVertexesSetCreator.ReachableVertexesMapCreator;
import StrazPozarna.ReachableVertexesSetCreator.RecursiveReachableVertexesMapCreator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Duration;
import java.util.*;


public class RecursiveReachableVertexesMapCreatorTest {
    private static List<City> citiesList;
    private static Map<City, Set<City>> mapOfReachableCities;
    private static List<Route> routesList;
    private static long maxTravelTime;
    private static Duration timeout;


    @BeforeClass
    public static void setUpBeforeClass() {
        /* cities: {"A", "B", "C". "D", "E"};*/
        City[] cities = {
                new City("A"),
                new City("B"),
                new City("C"),
                new City("D"),
                new City("E")
        };
        citiesList = Arrays.asList(cities);

        // Zbiory osiagalnych miast
         /*"roads"
        {  "cities" : ["A", "B"], "driving_time": 2  },
        {  "cities" : ["A", "C"], "driving_time": 6  },
        {  "cities" : ["A", "D"], "driving_time": 4  },
        {  "cities" : ["A", "E"], "driving_time": 1  },
        {  "cities" : ["B", "C"], "driving_time": 4  },
        {  "cities" : ["E", "C"], "driving_time": 12 },
        {  "cities" : ["E", "B"], "driving_time": 5 }*/
        Route[] routes = {
                new Route(cities[0], cities[1], 2L),
                new Route(cities[0], cities[2], 6L),
                new Route(cities[0], cities[3], 4L),
                new Route(cities[0], cities[4], 1L),
                new Route(cities[1], cities[2], 4L),
                new Route(cities[4], cities[2], 12L),
                new Route(cities[4], cities[1], 5L)
        };
        routesList = Arrays.asList(routes);


        Set<City> A = new HashSet<>();
        A.add(cities[0]);
        A.add(cities[1]);
        A.add(cities[3]);
        A.add(cities[4]);

        Set<City> B = new HashSet<>();
        B.add(cities[0]);
        B.add(cities[1]);
        B.add(cities[2]);
        B.add(cities[4]);

        Set<City> C = new HashSet<>();
        C.add(cities[2]);
        C.add(cities[1]);

        Set<City> D = new HashSet<>();
        D.add(cities[0]);
        D.add(cities[3]);
        D.add(cities[4]);


        Set<City> E = new HashSet<>();
        E.add(cities[0]);
        E.add(cities[1]);
        E.add(cities[3]);
        E.add(cities[4]);


        mapOfReachableCities = new HashMap<>();
        mapOfReachableCities.put(cities[0], A);
        mapOfReachableCities.put(cities[1], B);
        mapOfReachableCities.put(cities[2], C);
        mapOfReachableCities.put(cities[3], D);
        mapOfReachableCities.put(cities[4], E);

        maxTravelTime = 5;
        timeout = Duration.ofSeconds(5);
    }


    @Test
    public void testRecursiveReachableCitiesMapFinderReturnsNotNull() {
        ReachableVertexesMapCreator<City, Route> mapCreator = new RecursiveReachableVertexesMapCreator<>(citiesList, routesList, timeout, maxTravelTime);
        Assert.assertNotNull(mapCreator.getMapOfReachableVertexesFromSpecificVertex());
    }

    @Test
    public void testRecursiveReachableCitiesMapFinder() {
        ReachableVertexesMapCreator<City, Route> mapCreator = new RecursiveReachableVertexesMapCreator<>(citiesList, routesList, timeout, maxTravelTime);
        Map<City, Set<City>> computedMap = mapCreator.getMapOfReachableVertexesFromSpecificVertex();
        Map<City, Set<City>> testMap = new HashMap<>();
        Assert.assertNotEquals(testMap, computedMap);

        testMap = new HashMap<>(mapOfReachableCities);
        Assert.assertEquals(testMap, computedMap);

        testMap.get(new City("A")).add(new City("C"));
        Assert.assertNotEquals(testMap, computedMap);
    }
}
