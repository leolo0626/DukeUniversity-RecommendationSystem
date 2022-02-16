import java.util.Comparator;

public class SortByRatings implements Comparator<Rater> {
    public int compare(Rater a, Rater b) {
        return a.numRatings() - b.numRatings();
    }
}
