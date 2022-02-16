import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerWithFilters {

    public void printAverageRatings() throws IOException {
        ThirdRatings thirdRatings = new ThirdRatings("./data/ratings.csv");

        System.out.println("Number of raters in file : " + thirdRatings.getRaterSize());
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Number of Movie in file : " + MovieDatabase.size());


        ArrayList<Rating> ratings = thirdRatings.getAverageRatings(35);
        System.out.println(String.format("found %s movies: " ,ratings.size()));
        Collections.sort(ratings, new SortByAverageRatings());
        for (Rating averageRating : ratings) {
            String title = MovieDatabase.getTitle(averageRating.getItem());
            System.out.println(averageRating.getValue() + " " + title);
        }

    }

    public void printAverageRatingsByYear() throws IOException {
        ThirdRatings thirdRatings = new ThirdRatings("./data/ratings.csv");

        System.out.println("Number of raters in file : " + thirdRatings.getRaterSize());
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Number of Movie in file : " + MovieDatabase.size());

        YearAfterFilter yearAfterFilter = new YearAfterFilter(2000);
        ArrayList<Rating> ratings = thirdRatings.getAverageRatingsByFilter(20, yearAfterFilter);
        System.out.println(String.format("found %s movies: " ,ratings.size()));
        Collections.sort(ratings, new SortByAverageRatings());
        for (Rating averageRating : ratings) {
            String title = MovieDatabase.getTitle(averageRating.getItem());
            System.out.println(averageRating.getValue() + " " + title);
        }
    }

    public void printAverageRatingsByGenre() throws IOException {
        ThirdRatings thirdRatings = new ThirdRatings("./data/ratings.csv");

        System.out.println("Number of raters in file : " + thirdRatings.getRaterSize());
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Number of Movie in file : " + MovieDatabase.size());

        GenreFilter genreFilter = new GenreFilter("Comedy");
        ArrayList<Rating> ratings = thirdRatings.getAverageRatingsByFilter(20, genreFilter);
        System.out.println(String.format("found %s movies: " ,ratings.size()));
        Collections.sort(ratings, new SortByAverageRatings());
        for (Rating averageRating : ratings) {
            String title = MovieDatabase.getTitle(averageRating.getItem());
            System.out.println(averageRating.getValue() + " " + title);
        }
    }

    public void printAverageRatingsByMinutes() throws IOException {
        ThirdRatings thirdRatings = new ThirdRatings("./data/ratings.csv");

        System.out.println("Number of raters in file : " + thirdRatings.getRaterSize());
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Number of Movie in file : " + MovieDatabase.size());

        MinutesFilter minutesFilter = new MinutesFilter(105, 135);
        ArrayList<Rating> ratings = thirdRatings.getAverageRatingsByFilter(5, minutesFilter);
        System.out.println(String.format("found %s movies: " ,ratings.size()));
        Collections.sort(ratings, new SortByAverageRatings());
        for (Rating averageRating : ratings) {
            String title = MovieDatabase.getTitle(averageRating.getItem());
            System.out.println(averageRating.getValue() + " " + title);
        }
    }

    public void printAverageRatingsByDirectors() throws IOException {
        ThirdRatings thirdRatings = new ThirdRatings("./data/ratings.csv");

        System.out.println("Number of raters in file : " + thirdRatings.getRaterSize());
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Number of Movie in file : " + MovieDatabase.size());

        DirectorFilter directorFilter = new DirectorFilter(  "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack");
        ArrayList<Rating> ratings = thirdRatings.getAverageRatingsByFilter(4, directorFilter);
        System.out.println(String.format("found %s movies: " ,ratings.size()));
        Collections.sort(ratings, new SortByAverageRatings());
        for (Rating averageRating : ratings) {
            String title = MovieDatabase.getTitle(averageRating.getItem());
            System.out.println(averageRating.getValue() + " " + title);
        }
    }

    public void printAverageRatingsByYearAfterAndGenre() throws IOException {
        ThirdRatings thirdRatings = new ThirdRatings("./data/ratings.csv");

        System.out.println("Number of raters in file : " + thirdRatings.getRaterSize());
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Number of Movie in file : " + MovieDatabase.size());

        YearAfterFilter yearAfterFilter = new YearAfterFilter(1990);
        GenreFilter genreFilter = new GenreFilter("Drama");
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(yearAfterFilter);
        allFilters.addFilter(genreFilter);
        ArrayList<Rating> ratings = thirdRatings.getAverageRatingsByFilter(8, allFilters);
        System.out.println(String.format("found %s movies: " ,ratings.size()));
        Collections.sort(ratings, new SortByAverageRatings());
        for (Rating averageRating : ratings) {
            String title = MovieDatabase.getTitle(averageRating.getItem());
            System.out.println(averageRating.getValue() + " " + title);
        }
    }

    public void printAverageRatingsByDirectorsAndMinutes() throws IOException {
        ThirdRatings thirdRatings = new ThirdRatings("./data/ratings.csv");

        System.out.println("Number of raters in file : " + thirdRatings.getRaterSize());
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("Number of Movie in file : " + MovieDatabase.size());

        DirectorFilter directorFilter = new DirectorFilter("Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack");
        MinutesFilter minutesFilter = new MinutesFilter(90, 180);
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(directorFilter);
        allFilters.addFilter(minutesFilter);
        ArrayList<Rating> ratings = thirdRatings.getAverageRatingsByFilter(3, allFilters);
        System.out.println(String.format("found %s movies: " ,ratings.size()));
        Collections.sort(ratings, new SortByAverageRatings());
        for (Rating averageRating : ratings) {
            String title = MovieDatabase.getTitle(averageRating.getItem());
            System.out.println(averageRating.getValue() + " " + title);
        }
    }

    public static void main(String[] args) throws IOException {
        MovieRunnerWithFilters movieRunner = new MovieRunnerWithFilters() ;
        movieRunner.printAverageRatingsByDirectorsAndMinutes();

    }
}
