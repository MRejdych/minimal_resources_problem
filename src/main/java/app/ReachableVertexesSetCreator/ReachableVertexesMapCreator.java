package app.ReachableVertexesSetCreator;

import app.GraphEdge.GraphEdge;
import app.GraphVertex.Vertex;

import java.util.Map;
import java.util.Set;

public interface ReachableVertexesMapCreator<V extends Vertex, T extends GraphEdge> {
    Map<V, Set<V>> getMapOfReachableVertexesFromSpecificVertex();
}
