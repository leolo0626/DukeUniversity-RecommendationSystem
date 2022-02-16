import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ForthRatings {
     private double dotProduct(Rater me, Rater r) {
        double totalRatings = 0;
        for (String movieRatedByme : me.getItemsRated()) {
            if (r.getRating(movieRatedByme) != -1) {
                totalRatings += (me.getRating(movieRatedByme) - 5) * (r.getRating(movieRatedByme) - 5);
            }
        }
        return totalRatings;
    };

    private ArrayList<Rating> getSimilarities(String id) {
        ArrayList<Rating> ratings = new ArrayList<>();
        Rater me = RaterDatabase.getRater(id) ;
        ArrayList<Rater> OtherRaters = RaterDatabase.getRaters() ;
        for (Rater otherRater : OtherRaters) {
            if (!otherRater.equals(me)) {
                double similarities = dotProduct(me, otherRater);
                if (similarities > 0) {
                    ratings.add(new Rating(otherRater.getID(), similarities)) ;
                }
            }
        }
        Collections.sort(ratings, Collections.reverseOrder());
        return ratings ;
    }

    public ArrayList<Rating> getSimilarRatings(String id, Integer numSimilarRaters, Integer minimalRaters) {
        ArrayList<Rating> ratings = getSimilarities(id) ; //Rating(raterID, similarities)
        ArrayList<Rating> topMovieRating = new ArrayList<>(); // Rating(movieId, weighted average rating)

        HashMap<String, Double> movieWeightedAverageRatings = new HashMap<>();
        HashMap<String, Integer> movieWACount = new HashMap<>() ;

        for (int k = 0; k < numSimilarRaters; k++) {
            Rating raterSimilarities = ratings.get(k);
            Rater otherRater = RaterDatabase.getRater(raterSimilarities.getItem());
            for (String movieId : otherRater.getItemsRated()) {
                double movieRating = otherRater.getRating(movieId);
                double raterWeightedRating = movieRating * raterSimilarities.getValue();
                if (movieWeightedAverageRatings.containsKey(movieId)) {
                    double previousRaterWeightedRating = movieWeightedAverageRatings.get(movieId);
                    int movieCount = movieWACount.get(movieId);
                    movieWACount.put(movieId, movieCount+1);
                    movieWeightedAverageRatings.put(movieId, previousRaterWeightedRating + raterWeightedRating);
                } else {
                    movieWACount.put(movieId, 1);
                    movieWeightedAverageRatings.put(movieId, raterWeightedRating);
                }
            }
        }

        for (String movieId : movieWACount.keySet()) {
            if (movieWACount.get(movieId) >= minimalRaters) {
                topMovieRating.add(new Rating(movieId, movieWeightedAverageRatings.get(movieId)/movieWACount.get(movieId)));
            }
        }

        Collections.sort(topMovieRating, Collections.reverseOrder());
        return topMovieRating ;

    }

    public ArrayList<Rating> getSimilarRatingsByFilter(String id, Integer numSimilarRaters, Integer minimalRaters, Filter filterCriteria) {
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        ArrayList<Rating> topMovieRating = getSimilarRatings(id, numSimilarRaters, minimalRaters);
        ArrayList<Rating> movieMatchedCriteria = new ArrayList<>();
        for (Rating movieRating : topMovieRating) {
            String movieId = movieRating.getItem();
            if (movies.contains(movieId)) {
                movieMatchedCriteria.add(movieRating);
            }
        }
        return movieMatchedCriteria;
    }

}
