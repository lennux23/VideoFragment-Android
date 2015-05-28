package lennux.com.mx.movie_android.models;

import android.media.Rating;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mobilestudio06 on 18/05/15.
 */
public class Movie implements Serializable{
    private String title;
    private int year;
    private String runtime;
    private Ratings rating;
    private String synopsis;
    private String posterUrl;
    private ArrayList<Cast> casting;

    public Movie(String title, int year, String runtime, Ratings rating, String synopsis, String posterUrl, ArrayList<Cast>casting) {
        this.title = title;
        this.year = year;
        this.runtime = runtime;
        this.rating = rating;
        this.synopsis = synopsis;
        this.posterUrl = posterUrl;
        this.casting = casting;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public Ratings getRating() {
        return rating;
    }

    public void setRating(Ratings rating) {
        this.rating = rating;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public ArrayList<Cast> getCasting() {
        return casting;
    }

    public void setCasting(ArrayList<Cast> casting) {
        this.casting = casting;
    }

    public static ArrayList<Movie> parseJSON(String response){

        ArrayList<Movie> movies = new ArrayList<Movie>();

        try {
            JSONObject responseJSON = new JSONObject(response);
            JSONArray moviesJSON = responseJSON.getJSONArray("movies");
            for(int i=0; i<moviesJSON.length();i++){
                JSONObject movieJSON = moviesJSON.getJSONObject(i);
                String title = movieJSON.optString("title");
                int year = movieJSON.optInt("year");
                String runtime = movieJSON.optString("year");

                JSONObject ratingJSON = movieJSON.getJSONObject("ratings");

                Ratings rating = new Ratings(ratingJSON.optInt("critics_score"),ratingJSON.optInt("audience_score"));
                String synopsis = movieJSON.optString("synopsis");
                JSONObject posters = movieJSON.getJSONObject("posters");
                String posterUrl = posters.optString("thumbnail");


                JSONArray castingArray = movieJSON.getJSONArray("abridged_cast");
                ArrayList<Cast> casting = new ArrayList<Cast>();

                for (int j= 0; j < castingArray.length(); j++){
                    JSONObject castJSON = castingArray.getJSONObject(j);
                    Cast cast = new Cast(castJSON.optString("name"));
                    casting.add(cast);
                }

                Movie movie = new Movie(title,year,runtime,rating,synopsis,posterUrl,casting);
                movies.add(movie);
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        return movies;

    }


}
