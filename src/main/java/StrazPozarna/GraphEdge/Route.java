package StrazPozarna.GraphEdge;

import StrazPozarna.GraphVertex.City;


public class Route implements GraphEdge {
    private final City firstCity;
    private final City secondCity;
    private final Long travelTime;

    public Route(City firstCity, City secondCity, Long travelTime) {
        this.firstCity = firstCity;
        this.secondCity = secondCity;
        this.travelTime = travelTime;
    }

    public City getFirstVertex() {
        return firstCity;
    }

    public City getSecondVertex() {
        return secondCity;
    }

    public Long getEdgeWeight() {
        return travelTime;
    }


    public Route returnEdgeWithSwappedVertexes() {
        return new Route(this.secondCity, this.firstCity, travelTime);
    }

    @Override
    public String toString() {
        return "{ " + firstCity + " ->" + secondCity + " weight: " + travelTime + " }";
    }
}
