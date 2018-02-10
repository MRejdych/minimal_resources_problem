package app;

import app.GraphEdge.Route;
import app.GraphVertex.City;
import app.InputOutput.JsonIOHandler;
import app.MinimalSetFinderAlgorithms.GreedyMinimalSetFinder;
import app.MinimalSetFinderAlgorithms.MinimalSetFinder;
import app.ReachableVertexesSetCreator.ReachableVertexesMapCreator;
import app.ReachableVertexesSetCreator.RecursiveReachableVertexesMapCreator;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;


public class MinimalCitiesSetFinder implements Callable<Set<City>> {

    private Set<City> citiesSet;
    private MinimalSetFinder<City> finder;
    private ReachableVertexesMapCreator<City, Route> reachableCitiesMapCreator;

    public MinimalCitiesSetFinder(List<City> citiesList, MinimalSetFinder<City> finder, ReachableVertexesMapCreator<City, Route> mapCreator) {
        this.citiesSet = new HashSet<>(citiesList);
        this.finder = finder;
        this.reachableCitiesMapCreator = mapCreator;
    }

    public Set<City> call() {
        long startTime = System.nanoTime();

        Map<City, Set<City>> mapOfReachableCitiesSets = reachableCitiesMapCreator.getMapOfReachableVertexesFromSpecificVertex();
        Set<City> minimalCitiesSet = finder.findMinimalSet(mapOfReachableCitiesSets, citiesSet);

        long stopTime = System.nanoTime();
        long elapsedTime = stopTime - startTime;
        System.out.println("Found minimal set in  " + elapsedTime + " nanoseconds.");
        return minimalCitiesSet;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String source = "in.json"; //default source of in.json file
        String destination = "out.json"; //default source of out.json file

        if (args.length >= 2) {
            source = args[0];
            destination = args[1];
        }

        final JsonIOHandler jsonIOHandler = new JsonIOHandler(source);
        final Long maxTravelTime = jsonIOHandler.getMaxTravelTime();
        final List<City> citiesList = jsonIOHandler.getCitiesList();
        final List<Route> routesList = jsonIOHandler.getRoutesList();
        final Duration timeout = Duration.ofSeconds(jsonIOHandler.getTimeoutValue());
        Set<City> minimalCitiesSet = null;

        MinimalSetFinder<City> finder = new GreedyMinimalSetFinder<>();
        ReachableVertexesMapCreator<City, Route> reachableCitiesMapCreator = new RecursiveReachableVertexesMapCreator<>(citiesList, routesList, timeout, maxTravelTime);
        MinimalCitiesSetFinder finderThread = new MinimalCitiesSetFinder(citiesList, finder, reachableCitiesMapCreator);


        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Future<Set<City>> handler = executor.submit(finderThread);

        try {
            minimalCitiesSet = handler.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            handler.cancel(true);
            minimalCitiesSet = new HashSet<>(citiesList);
            System.out.println("Timeout exceeded");
        } catch (Exception e) {
            e.printStackTrace();
        }

        executor.shutdownNow();

        jsonIOHandler.writeDataToFile(destination, minimalCitiesSet);
        System.out.println("Minimal cities set is: " + minimalCitiesSet);
    }
}
