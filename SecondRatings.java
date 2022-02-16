/**
 * Write a description of SecondRatings here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.io.IOException;
import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private HashMap<String, Rater> myRaters;

    public SecondRatings() throws IOException{
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }

    public SecondRatings(String moviefile, String ratingsfile) throws IOException {
        FirstRatings firstRatings = new FirstRatings();
        myMovies = firstRatings.loadMovies(moviefile);
        myRaters = firstRatings.loadRaters(ratingsfile);
    }

    public int getMovieSize() {
        return myMovies.size();
    }
    public int getRaterSize() {
        return myRaters.keySet().size();
    }

    public double getAverageByID (String id , int minimalRaters) {
        double totalRating = 0.0 ;
        int totalCount = 0;
        for (Rater rater : myRaters.values()) {
            if (rater.hasRating(id)) {
                totalRating += rater.getRating(id);
                totalCount += 1;
            }
        }
        if (totalCount < minimalRaters) {
            return 0.0 ;
        } else {
            return totalRating / totalCount;
        }
    }

    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        ArrayList<Rating> ratings = new ArrayList<>();
        for (Movie movie : myMovies) {
            String id = movie.getID();
            double averageRating = getAverageByID(id, minimalRaters);
            if (averageRating != 0.0) {
                ratings.add(new Rating(id, averageRating));
            }
        }
        return ratings;
    }

    public String getTitle (String id) {
        for (Movie movie : myMovies) {
            if (movie.getID().equals(id)) {
                return movie.getTitle();
            }
        }
        return "ID was not found";
    }

    public String getId (String title) {
        for (Movie movie : myMovies) {
            if (movie.getTitle().toLowerCase().trim().equals(title.toLowerCase().trim())) {
                return movie.getID();
            }
        }
        return "NO SUCH TITLE";
    };
}