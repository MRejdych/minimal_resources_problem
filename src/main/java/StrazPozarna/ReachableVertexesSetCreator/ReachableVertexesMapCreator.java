package StrazPozarna.ReachableVertexesSetCreator;

import StrazPozarna.GraphEdge.GraphEdge;
import StrazPozarna.GraphVertex.Vertex;

import java.util.Map;
import java.util.Set;

public interface ReachableVertexesMapCreator<V extends Vertex, T extends GraphEdge> {
    Map<V, Set<V>> getMapOfReachableVertexesFromSpecificVertex();
}
