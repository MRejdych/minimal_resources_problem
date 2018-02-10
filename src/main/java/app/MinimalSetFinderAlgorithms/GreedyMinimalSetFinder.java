package app.MinimalSetFinderAlgorithms;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class GreedyMinimalSetFinder<T> implements MinimalSetFinder<T> {

    public GreedyMinimalSetFinder() {
    }

    public Set<T> findMinimalSet(Map<T, Set<T>> mapOfSets, Set<T> universe) {
        int maxSetsIntersectionSize = 0;
        int intersectionSize;
        T bestElement = null;
        Set<T> elementsToReach = new HashSet<>(universe);
        Set<T> minimalSet = new HashSet<>();
        Set<T> keySet = mapOfSets.keySet();
        Set<T> temporarySet;
        do {
            for (T element : keySet) {
                temporarySet = mapOfSets.get(element);
                intersectionSize = sizeOfIntersectionBetweenSets(temporarySet, elementsToReach);

                if (intersectionSize > maxSetsIntersectionSize) {
                    bestElement = element;
                    maxSetsIntersectionSize = intersectionSize;
                }
            }
            minimalSet.add(bestElement);
            elementsToReach.removeAll(mapOfSets.get(bestElement));
            maxSetsIntersectionSize = 0;
        }
        while (!elementsToReach.isEmpty());
        return minimalSet;
    }


    private int sizeOfIntersectionBetweenSets(Set<T> set, Set<T> elementsToReach) {
        Set<T> intersection = new HashSet<>(elementsToReach);
        intersection.retainAll(set);
        return intersection.size();
    }
}
