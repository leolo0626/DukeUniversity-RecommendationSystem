import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class FirstRatings {
    public ArrayList<Movie> loadMovies (String filename) throws IOException {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        File csvData  = new File(filename);
        CSVParser parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.DEFAULT.withFirstRecordAsHeader()) ;
        for (CSVRecord csvRecord : parser) {
            movies.add(parseMovieFromCSVRecord(csvRecord));
        }
        return movies;
    }

    public HashMap<String, Rater> loadRaters (String filename) throws IOException {
        HashMap<String, Rater> raters = new HashMap<String, Rater>();
        File csvData  = new File(filename);
        CSVParser parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.DEFAULT.withFirstRecordAsHeader()) ;
        for (CSVRecord csvRecord : parser) {
//            raters.add(parseRaterFromCSVRecord(csvRecord))
            String raterId = csvRecord.get(0);
            String movieId = csvRecord.get(1);
            Double rating = Double.parseDouble(csvRecord.get(2));
            if (raters.containsKey(raterId)) {
                Rater existingRater = raters.get(raterId);
                existingRater.addRating(movieId, rating);
                raters.put(raterId, existingRater);
            } else {
                Rater rater = new PlainRater(raterId);
                rater.addRating(movieId, rating);
                raters.put(raterId, rater);
            }
        }
        return raters;
    }

//    public ArrayList<Rating> loadRatings(String filename) throws IOException {
//        ArrayList<Rating> ratings = new ArrayList<Rating>();
//        File csvData  = new File(filename);
//        CSVParser parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.DEFAULT.withFirstRecordAsHeader()) ;
//        for (CSVRecord csvRecord : parser) {
//            ratings.add(parseMovieFromCSVRecord(csvRecord));
//        }
//        return ratings;
//    }
//    private Rater parseRaterFromCSVRecord(CSVRecord record) {
//        String rater_id = record.get(0);
//        String movie_id = record.get(1);
//        double rating = Double.parseDouble(record.get(2));
//        return new Rater(rater_id, movie_id, rating);
//    }

    private Movie parseMovieFromCSVRecord(CSVRecord record) {
        String id = record.get(0);
        String title = record.get(1);
        String year = record.get(2);
        String country = record.get(3);
        String genre = record.get(4);
        String director = record.get(5);
        int minutes = Integer.parseInt(record.get(6));
        String poster = record.get(7);

        return new Movie(id, title, year, genre, director, country, poster, minutes);
    }

    public int countSpecificGenre(ArrayList<Movie> movies, String genre) {
        int count = 0;
        for (Movie movie : movies) {
            String genres = movie.getGenres();
            boolean isGenre = Arrays.stream(genres.split(",")).map(String::trim).collect(Collectors.toList()).contains(genre);
            if (isGenre) count += 1;
        }
        return count;
    }

    public int countMovieLengthGreaterThan(ArrayList<Movie> movies, int minutes) {
        int count = 0;
        for (Movie movie : movies) {
            if (movie.getMinutes() > minutes) count += 1;
        }
        return count;
    }

    public String [] findDirectorWithMaxNumMovie (ArrayList<Movie> movies) {
        HashMap<String, Integer> directorMap = new HashMap<String, Integer>();
        int maxNumberMovie = 0;
        String directorWithMaxNumberMovie = "" ;

        for (Movie movie : movies) {
            String directors = movie.getDirector();
            for (String director : directors.split(",")) {
                if (directorMap.keySet().contains(director)) {
                    int newDirectorCount = directorMap.get(director)+1;
                    if (newDirectorCount > maxNumberMovie) {
                        directorWithMaxNumberMovie = director;
                        maxNumberMovie = newDirectorCount;
                    }
                    directorMap.put(director, newDirectorCount);
                } else {
                    directorMap.put(director, 1);
                }
            }
        }
        return new String[]{directorWithMaxNumberMovie, Integer.toString(maxNumberMovie)};
    };

    public int findNumberOfRatings(HashMap<String, Rater> raters, String raterId) {
        return raters.get(raterId).numRatings();
    }

    public int findMaxNumOfRatings(HashMap<String, Rater> raters) {
        return Collections.max(raters.values(), new SortByRatings()).numRatings();
    }

    public ArrayList<Rater> findRatersWithMaxNumOfRatings(HashMap<String, Rater> raters, int maxNumber) {
        ArrayList<Rater> raterList = new ArrayList<Rater>();
        for (String raterId : raters.keySet()) {
            Rater rater = raters.get(raterId) ;
            if (rater.numRatings() == maxNumber) {
                raterList.add(rater);
            }
        }
        return raterList;
    }

    public int findRatingsForParticularMovie(HashMap<String, Rater> raters, String item) {
        int numOfRatings = 0 ;
        for (String raterId : raters.keySet()) {
            Rater rater = raters.get(raterId);
            if(rater.hasRating(item)) numOfRatings += 1;
        }
        return numOfRatings ;
    }

    public int findNumDistinctMovie(HashMap<String, Rater> raters) {
        HashMap<String, Integer> distinctMovie = new HashMap<String, Integer>();
        for (Rater rater : raters.values()) {
            ArrayList<String> movies = rater.getItemsRated();
            for (String movieString : movies) {
                if (!distinctMovie.containsKey(movieString)) {
                    distinctMovie.put(movieString, 1);
                }
            }
        }
        return distinctMovie.keySet().size();
    }
    public void testLoadMovies () throws IOException {
        ArrayList<Movie> movies = loadMovies("./data/ratedmoviesfull.csv");
//        ArrayList<Movie> movies = loadMovies("./data/ratedmoviesfull.csv");
        System.out.println("Number of Movies : " + movies.size()) ;
        final String genre = "Comedy";
        final int minutesLength = 150;
        int genreCount = countSpecificGenre(movies, genre);
        int minutesCount = countMovieLengthGreaterThan(movies, minutesLength);
        String [] directorsAndNumber = findDirectorWithMaxNumMovie(movies);
        System.out.println(String.format("Number of Movie, including %s : %d", genre, genreCount));
        System.out.println(String.format("Number of Movie, which are greater than %d minutes : %d", minutesLength, minutesCount));
        System.out.println(directorsAndNumber[0] + " " + directorsAndNumber[1]);
    }

    public void testLoadRaters () throws IOException {
        HashMap<String, Rater> raters = loadRaters("./data/ratings.csv");
        System.out.println(raters.size());
        System.out.println("The number of ratings : "+findNumberOfRatings(raters, "193"));
        int maxRating  = findMaxNumOfRatings(raters) ;
        System.out.println("The max. number of ratings by any rater: " + maxRating);
        ArrayList<Rater> maxRaterList = findRatersWithMaxNumOfRatings(raters, maxRating);
        for (Rater maxRater : maxRaterList) {
            System.out.println(maxRater);
        }
        System.out.println("The number of ratings for particular movie: " + findRatingsForParticularMovie(raters, "1798709"));
        System.out.println("Number of distinct movie: " + findNumDistinctMovie(raters));
    }

    public static void main (String [] args) throws IOException {
        FirstRatings firstRatings = new FirstRatings();
        firstRatings.testLoadMovies();
        firstRatings.testLoadRaters();

    }

}

