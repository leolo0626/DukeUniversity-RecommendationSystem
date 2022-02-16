import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ThirdRatings {

    private HashMap<String, Rater> myRaters;

    public ThirdRatings() throws IOException {
        // default constructor
        this("ratings.csv");
    }

    public ThirdRatings(String ratingsfile) throws IOException {
        FirstRatings firstRatings = new FirstRatings();
        myRaters = firstRatings.loadRaters(ratingsfile);
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
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for (String id : movies) {
            double averageRating = getAverageByID(id, minimalRaters);
            if (averageRating != 0.0) {
                ratings.add(new Rating(id, averageRating));
            }
        }
        return ratings;
    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        ArrayList<Rating> ratings = new ArrayList<>();
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        for (String id : movies) {
            double averageRating = getAverageByID(id, minimalRaters);
            if (averageRating != 0.0) {
                ratings.add(new Rating(id, averageRating));
            }
        }
        return ratings;
    }


}
