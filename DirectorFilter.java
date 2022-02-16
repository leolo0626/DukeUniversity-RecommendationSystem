import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DirectorFilter implements Filter  {
        private List<String> myDirectors;

        public DirectorFilter(String directors) {
            myDirectors = Arrays.stream(directors.split(",")).map(String::trim).collect(Collectors.toList());
        }

        @Override
        public boolean satisfies(String id) {
            String directors = MovieDatabase.getDirector(id);
            List<String> directorList = Arrays.stream(directors.split(",")).map(String::trim).collect(Collectors.toList());
            for (String director : myDirectors) {
                if (directorList.contains(director)) {
                    return true;
                }
            }
            return false;
        }
}
