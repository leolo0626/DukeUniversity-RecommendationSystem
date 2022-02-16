import java.util.Comparator;

public class SortByAverageRatings implements Comparator<Rating> {
    public int compare(Rating a, Rating b) {
        return Double.compare(a.getValue(), b.getValue());
    }
}
