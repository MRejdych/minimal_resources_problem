package app;

import StrazPozarna.GraphVertex.City;
import org.junit.Assert;
import org.junit.Test;

public class CityTest {

    @Test
    public void testCityEquals() {
        City city1 = new City("Krakow");
        City city2 = new City("Krakow");
        City city3 = new City("Katowice");

        Assert.assertEquals(city2, city1);
        Assert.assertNotEquals(city3, city1);
        Assert.assertNotEquals("Krakow", city1);
    }
}
