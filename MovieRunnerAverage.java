import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerAverage {


    public void printAverageRatings() throws IOException {
        SecondRatings secondRatings = new SecondRatings("./data/ratedmovies_custom.csv", "./data/ratings_custom.csv");
        System.out.println("Number of movies in file : " + secondRatings.getMovieSize());
        System.out.println("Number of raters in file : " + secondRatings.getRaterSize());
        ArrayList<Rating> ratings = secondRatings.getAverageRatings(50);
        Collections.sort(ratings, new SortByAverageRatings());
        for (Rating averageRating : ratings) {
            String title = secondRatings.getTitle(averageRating.getItem());
            System.out.println(averageRating.getValue() + " " + title);
        }
        System.out.println("Movies with 50 or more ratings: " + ratings.size());
    }

    public void getAverageRatingOneMovie() throws IOException  {
        SecondRatings secondRatings = new SecondRatings("./data/ratedmovies_custom.csv", "./data/ratings_custom.csv");
        String id = secondRatings.getId("No Country for Old Men");
        double averageRating = secondRatings.getAverageByID(id, 0);
        System.out.println("No Country for Old Men " + averageRating);
    }

    public static void main(String[] args) throws IOException {
        MovieRunnerAverage movieRunner = new MovieRunnerAverage() ;
        movieRunner.printAverageRatings();
        movieRunner.getAverageRatingOneMovie();
    }


}
