import java.io.IOException;
import java.util.ArrayList;

public class MovieRunnerSimilarRatings {

    public void printSimilarRatings() {
        ForthRatings forthRating = new ForthRatings();
        RaterDatabase.initialize("ratings.csv");
        System.out.println("Number of raters in file : " + RaterDatabase.size());
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Number of Movie in file : " + MovieDatabase.size());
        ArrayList<Rating> ratings = forthRating.getSimilarRatings("71", 20, 5);
        for (Rating averageRating : ratings) {
            String title = MovieDatabase.getTitle(averageRating.getItem());
            System.out.println(averageRating.getValue() + " " + title);
        }
    }

    public void printSimilarRatingsByGenre() {
        ForthRatings forthRating = new ForthRatings();
        RaterDatabase.initialize("ratings.csv");
        System.out.println("Number of raters in file : " + RaterDatabase.size());
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Number of Movie in file : " + MovieDatabase.size());
        GenreFilter genreFilter = new GenreFilter("Mystery");
        ArrayList<Rating> ratings = forthRating.getSimilarRatingsByFilter("964", 20, 5, genreFilter);
        for (Rating averageRating : ratings) {
            String title = MovieDatabase.getTitle(averageRating.getItem());
            System.out.println(averageRating.getValue() + " " + title);
        }
    }

    public void printSimilarRatingsByDirector() {
        ForthRatings forthRating = new ForthRatings();
        RaterDatabase.initialize("ratings.csv");
        System.out.println("Number of raters in file : " + RaterDatabase.size());
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Number of Movie in file : " + MovieDatabase.size());
        DirectorFilter filter = new DirectorFilter("Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh");
        ArrayList<Rating> ratings = forthRating.getSimilarRatingsByFilter("120", 10, 2, filter);
        for (Rating averageRating : ratings) {
            String title = MovieDatabase.getTitle(averageRating.getItem());
            System.out.println(averageRating.getValue() + " " + title);
        }
    }

    public void printSimilarRatingsByGenreAndMinutes() {
        ForthRatings forthRating = new ForthRatings();
        RaterDatabase.initialize("ratings.csv");
        System.out.println("Number of raters in file : " + RaterDatabase.size());
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Number of Movie in file : " + MovieDatabase.size());
        GenreFilter genreFilter = new GenreFilter("Drama");
        MinutesFilter minutesFilter = new MinutesFilter(80, 160);
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(genreFilter);
        allFilters.addFilter(minutesFilter);
        ArrayList<Rating> ratings = forthRating.getSimilarRatingsByFilter("168", 10, 3, allFilters);
        for (Rating averageRating : ratings) {
            String title = MovieDatabase.getTitle(averageRating.getItem());
            System.out.println(averageRating.getValue() + " " + title);
        }
    }

    public void printSimilarRatingsByYearAfterAndMinutes() {
        ForthRatings forthRating = new ForthRatings();
        RaterDatabase.initialize("ratings.csv");
        System.out.println("Number of raters in file : " + RaterDatabase.size());
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Number of Movie in file : " + MovieDatabase.size());
        YearAfterFilter yearAfterFilter = new YearAfterFilter(1975);
        MinutesFilter minutesFilter = new MinutesFilter(70, 200);
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(yearAfterFilter);
        allFilters.addFilter(minutesFilter);
        ArrayList<Rating> ratings = forthRating.getSimilarRatingsByFilter("314", 10, 5, allFilters);
        for (Rating averageRating : ratings) {
            String title = MovieDatabase.getTitle(averageRating.getItem());
            System.out.println(averageRating.getValue() + " " + title);
        }
    }

    public static void main(String[] args) throws IOException {
        MovieRunnerSimilarRatings movieRunner = new MovieRunnerSimilarRatings();
        movieRunner.printSimilarRatingsByYearAfterAndMinutes();
    }

}
