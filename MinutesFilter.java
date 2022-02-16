import java.util.Arrays;
import java.util.stream.Collectors;

public class MinutesFilter implements Filter {
    private int myMinMinutes;
    private int myMaxMinutes;

    public MinutesFilter(int minMinutes, int maxMinutes) {
        myMaxMinutes = maxMinutes;
        myMinMinutes = minMinutes;
    }

    @Override
    public boolean satisfies(String id) {
        int minutes = MovieDatabase.getMinutes(id);
        return (minutes >= myMinMinutes & minutes <= myMaxMinutes);
    }
}
