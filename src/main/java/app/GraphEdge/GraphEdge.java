package app.GraphEdge;

import app.GraphVertex.Vertex;

public interface GraphEdge {
    Vertex getFirstVertex();

    Vertex getSecondVertex();

    Long getEdgeWeight();

    GraphEdge returnEdgeWithSwappedVertexes();
}
