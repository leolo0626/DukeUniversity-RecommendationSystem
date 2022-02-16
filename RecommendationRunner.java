import java.util.ArrayList;
import java.util.Random;

public class RecommendationRunner implements Recommender{
    @Override
    public ArrayList<String> getItemsToRate() {
        MovieDatabase.initialize("ratedmoviesfull.csv");
        AllFilters filters = new AllFilters();
        YearAfterFilter yearAfterFilter = new YearAfterFilter(2005);
        MinutesFilter minutesFilter = new MinutesFilter(100, 150);
        filters.addFilter(yearAfterFilter);
        filters.addFilter(minutesFilter);
        ArrayList<String> movieIds = MovieDatabase.filterBy(filters);
        ArrayList<String> selectedMovieIds = new ArrayList<>();
        int numOfMovie = 0 ;
        Random rand = new Random();
        while (numOfMovie < 30) {
            int selectedMovieIdIndex = rand.nextInt(movieIds.size());
            String selectedMovieId = movieIds.get(selectedMovieIdIndex);
            selectedMovieIds.add(selectedMovieId);
            numOfMovie += 1 ;
        }
       return selectedMovieIds ;
    }

    @Override
    public void printRecommendationsFor(String webRaterID) {
        ForthRatings forthRating = new ForthRatings();
        RaterDatabase.initialize("ratings.csv");
        ArrayList<Rating> movieRatings = forthRating.getSimilarRatings(webRaterID, 20, 3);
        String openHtml =
                """ 
                        <style>
                         .checked {
                           color: orange;
                         }
                         
                         table {
                           font-family: arial, sans-serif;
                           border-collapse: collapse;
                           width: 100%;
                         }
                         
                         td, th {
                           border: 1px solid #dddddd;
                           text-align: left;
                           padding: 8px;
                         }
                         
                         tr:nth-child(even) {
                           background-color: #dddddd;
                         }
                        </style>
                        <table>
                        <tr>
                                <th>Title</th>
                                <th>Genres</th>
                                <th>Year</th>
                                <th>Minutes</th>
                                <th>Ratings</th>
                        </tr>
                                        
                                        
                        """;

        for (Rating movieRating : movieRatings) {
            String movieId = movieRating.getItem();
            int movieWA = (int) movieRating.getValue();
            String title = MovieDatabase.getTitle(movieId);
            String genres = MovieDatabase.getGenres(movieId);
            int year = MovieDatabase.getYear(movieId);
            int minutes = MovieDatabase.getMinutes(movieId);
            String poster = MovieDatabase.getPoster(movieId);
            String checkedStar = """
                    <span class="checked">&spades;
                    </span>""";
            String uncheckedStar = """
                    <span >&spades;</span>
                    """;
            String star = "" ;
            for (int i = 0; i < 10 ; i++) {
                if (i < movieWA) {
                    star += checkedStar;
                } else {
                    star += uncheckedStar;
                }
            }

            String tableRow = String.format("""
                    <tr>
                        <td><a href="%s">%s</a></td>
                        <td>%s</td>
                        <td>%d</td>
                        <td>%d</td>
                        <td>%s</td>
                    </tr>
                    """,poster, title, genres, year, minutes, star);
            openHtml += tableRow;
        }
        openHtml = openHtml + "</table>";
        System.out.println(openHtml);
    }

}
