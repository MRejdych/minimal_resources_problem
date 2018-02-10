package app;

import StrazPozarna.GraphEdge.Route;
import StrazPozarna.GraphVertex.City;
import StrazPozarna.MinimalCitiesSetFinder;
import StrazPozarna.MinimalSetFinderAlgorithms.MinimalSetFinder;
import StrazPozarna.ReachableVertexesSetCreator.ReachableVertexesMapCreator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;


public class MinimalCitySetFinderTest {

    private static List<City> citiesList;
    private static MinimalSetFinder<City> finder;
    private static ReachableVertexesMapCreator<City, Route> mapCreator;

    @BeforeClass
    public static void setUpBeforeClass() {

        finder = (mapOfSets, universe) -> {
            Set<City> expectedSet = new HashSet<>();
            expectedSet.add(new City("A"));
            expectedSet.add(new City("B"));

            return expectedSet;
        };

        mapCreator = HashMap::new;

        citiesList = new ArrayList<>();
    }


    @Test
    public void testMinimalCitiesSetFinderReturnsNotNull() {
        MinimalCitiesSetFinder minimalCitiesSetFinder = new MinimalCitiesSetFinder(citiesList, finder, mapCreator);

        Set<City> computedSet = minimalCitiesSetFinder.call();
        Assert.assertNotNull(computedSet);
    }

    @Test
    public void testMinimalCitiesSetFinder() {
        MinimalCitiesSetFinder minimalCitiesSetFinder = new MinimalCitiesSetFinder(citiesList, finder, mapCreator);

        Set<City> computedSet = minimalCitiesSetFinder.call();
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
