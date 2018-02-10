package app.ReachableVertexesSetCreator;

import app.GraphEdge.GraphEdge;
import app.GraphVertex.Vertex;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class RecursiveReachableVertexesMapCreator<V extends Vertex, T extends GraphEdge> implements ReachableVertexesMapCreator<V, T> {
    private final Set<V> vertexesSet;
    private final List<T> graphEdges;
    private final Duration timeout;
    private final Long maxWeightBetweenVertexes;
    private Map<V, Set<V>> mapOfReachableVertexesFromSpecificVertex;
    private Map<V, Set<T>> mapOfEdgesDirectlyConnectedToVertex;

    public RecursiveReachableVertexesMapCreator(List<V> vertexesList, List<T> graphEdges, Duration timeout, Long maxWeightBetweenVertexes) {
        this.vertexesSet = new HashSet<>(vertexesList);
        this.graphEdges = graphEdges;
        this.timeout = timeout;
        this.maxWeightBetweenVertexes = maxWeightBetweenVertexes;
        this.mapOfReachableVertexesFromSpecificVertex = new ConcurrentHashMap<>();
    }

    public Map<V, Set<V>> getMapOfReachableVertexesFromSpecificVertex() {
        mapOfEdgesDirectlyConnectedToVertex = createMapOfEdgesDirectlyConnectedToVertex();
        mapOfReachableVertexesFromSpecificVertex = createMapOfReachableVertexesFromSpecificVertex();

        return mapOfReachableVertexesFromSpecificVertex;
    }


    private Map<V, Set<V>> createMapOfReachableVertexesFromSpecificVertex() {
        Map<V, Set<V>> map = new ConcurrentHashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        vertexesSet.forEach(v -> executor.execute(() -> map.put(v, createReachableVertexesSet(v, new HashSet<>(), maxWeightBetweenVertexes))));
        executor.shutdown();
        try {
            executor.awaitTermination(timeout.toMillis(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return map;
    }

    private Set<V> createReachableVertexesSet(V vertex, Set<V> visited, Long maxWeightOfEdgesBetweenVertexes) {
        Set<T> edgesSet = mapOfEdgesDirectlyConnectedToVertex.get(vertex);
        Set<V> reachableVertexesSet = new HashSet<>();
        reachableVertexesSet.add(vertex);

        for (T edge : edgesSet) {
            Set<V> visitedVertexes = new HashSet<V>(visited);
            visitedVertexes.add(vertex);
            Long edgeWeight = edge.getEdgeWeight();
            V nextVertex = (V) edge.getSecondVertex();
            if (visitedVertexes.contains(nextVertex)) {
                continue;
            }
            if (edgeWeight <= maxWeightOfEdgesBetweenVertexes) {
                reachableVertexesSet.addAll(createReachableVertexesSet(nextVertex, visitedVertexes, (maxWeightOfEdgesBetweenVertexes - edgeWeight)));
            }
        }

        return reachableVertexesSet;
    }


    private Map<V, Set<T>> createMapOfEdgesDirectlyConnectedToVertex() {
        Map<V, Set<T>> map = new ConcurrentHashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        vertexesSet.forEach(vertex -> executor.execute(() -> map.put(vertex, addEdges(vertex, graphEdges))));
        executor.shutdown();
        try {
            executor.awaitTermination(timeout.toMillis(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return map;
    }


    private Set<T> addEdges(V vertex, List<T> graphEdges) {
        Set<T> directlyConnectedEdges = new HashSet<>();
        for (T edge : graphEdges) {
            if (vertex.equals(edge.getFirstVertex())) {
                directlyConnectedEdges.add(edge);
            } else if (vertex.equals(edge.getSecondVertex())) {
                directlyConnectedEdges.add(((T) edge.returnEdgeWithSwappedVertexes()));
            }
        }

        return directlyConnectedEdges;
    }
}