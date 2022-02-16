import java.util.Arrays;
import java.util.stream.Collectors;

public class GenreFilter implements Filter{
    private String myGenre;

    public GenreFilter(String genre) {
        myGenre = genre;
    }

    @Override
    public boolean satisfies(String id) {
        String genres = MovieDatabase.getGenres(id);

        boolean isGenre = Arrays.stream(genres.split(",")).map(String::trim).collect(Collectors.toList()).contains(myGenre);
        return isGenre;
    }
}
