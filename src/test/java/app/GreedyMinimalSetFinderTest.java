package app;

import StrazPozarna.GraphVertex.City;
import StrazPozarna.MinimalSetFinderAlgorithms.GreedyMinimalSetFinder;
import StrazPozarna.MinimalSetFinderAlgorithms.MinimalSetFinder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

public class GreedyMinimalSetFinderTest {

    private static Set<City> citiesSet;
    private static Map<City, Set<City>> mapOfReachableCities;
    private static Set<Integer> integersSet;
    private static Map<Integer, Set<Integer>> mapOfIntegersSubsets;

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

        // Reachable cities sets
         /*
         "roads"
        {  "cities" : ["A", "B"], "driving_time": 2  },
        {  "cities" : ["A", "C"], "driving_time": 6  },
        {  "cities" : ["A", "D"], "driving_time": 4  },
        {  "cities" : ["A", "E"], "driving_time": 1  },
        {  "cities" : ["B", "C"], "driving_time": 4  },
        {  "cities" : ["E", "C"], "driving_time": 12 },
        {  "cities" : ["E", "B"], "driving_time": 5 }
        */

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

        // max drive time: 5
        citiesSet = new HashSet<>(Arrays.asList(cities));
        mapOfReachableCities = new HashMap<>();
        mapOfReachableCities.put(cities[0], A);
        mapOfReachableCities.put(cities[1], B);
        mapOfReachableCities.put(cities[2], C);
        mapOfReachableCities.put(cities[3], D);
        mapOfReachableCities.put(cities[4], E);

        //Minimal integers set finder test data
        Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        integersSet = new HashSet<>(Arrays.asList(integers));
        Integer[] integers1 = {1, 2, 3, 4, 10};
        Set<Integer> integersSubset1 = new HashSet<>(Arrays.asList(integers1));
        Integer[] integers2 = {1, 5, 6, 7, 8, 9};
        Set<Integer> integersSubset2 = new HashSet<>(Arrays.asList(integers2));
        Integer[] integers3 = {3, 4};
        Set<Integer> integersSubset3 = new HashSet<>(Arrays.asList(integers3));
        mapOfIntegersSubsets = new HashMap<>();
        mapOfIntegersSubsets.put(1, integersSubset1);
        mapOfIntegersSubsets.put(2, integersSubset2);
        mapOfIntegersSubsets.put(3, integersSubset3);
    }


    @Test
    public void testMinimalCitiesSetFinderReturnsNotNull() {
        MinimalSetFinder<City> finder = new GreedyMinimalSetFinder<>();

        Set<City> computedSet = finder.findMinimalSet(mapOfReachableCities, citiesSet);
        Assert.assertNotNull(computedSet);
    }

    @Test
    public void testMinimalIntegersSetFinderCorrectResult() {
        MinimalSetFinder<Integer> finder2 = new GreedyMinimalSetFinder<>();
        Set<Integer> integersComputedSet = finder2.findMinimalSet(mapOfIntegersSubsets, integersSet);
        Set<Integer> integersTestSet = new HashSet<>();
        Assert.assertNotEquals(integersTestSet, integersComputedSet);

        integersTestSet.add(1);
        integersTestSet.add(2);
        Assert.assertEquals(integersTestSet, integersComputedSet);

        integersTestSet.add(3);
        Assert.assertNotEquals(integersTestSet, integersComputedSet);
    }

    @Test
    public void testMinimalCitiesSetFinderCorrectResult() {
        MinimalSetFinder<City> finder = new GreedyMinimalSetFinder<>();

        Set<City> computedSet = finder.findMinimalSet(mapOfReachableCities, citiesSet);
        Set<City> testSet = new HashSet<>();
        Assert.assertNotEquals(testSet, computedSet);

        testSet.add(new City("A"));
        testSet.add(new City("B"));

        Assert.assertEquals(testSet, computedSet);

        testSet.add(new City("C"));
        Assert.assertNotEquals(testSet, computedSet);

        testSet.add(new City("E"));
        testSet.add(new City("D"));
        Assert.assertNotEquals(testSet, computedSet);
    }
}
