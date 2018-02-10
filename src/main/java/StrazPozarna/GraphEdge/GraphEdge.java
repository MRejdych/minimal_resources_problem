package StrazPozarna.GraphEdge;

import StrazPozarna.GraphVertex.Vertex;

public interface GraphEdge {
    Vertex getFirstVertex();

    Vertex getSecondVertex();

    Long getEdgeWeight();

    GraphEdge returnEdgeWithSwappedVertexes();
}
