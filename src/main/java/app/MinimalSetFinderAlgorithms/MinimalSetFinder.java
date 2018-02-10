package app.MinimalSetFinderAlgorithms;

import java.util.Map;
import java.util.Set;

public interface MinimalSetFinder<T> {
    Set<T> findMinimalSet(Map<T, Set<T>> mapOfSets, Set<T> universe);
}
