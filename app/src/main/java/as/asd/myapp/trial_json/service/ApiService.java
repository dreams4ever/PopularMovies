package as.asd.myapp.trial_json.service;

import as.asd.myapp.trial_json.BuildConfig;
import as.asd.myapp.trial_json.MovieModel;
import as.asd.myapp.trial_json.MovieResponse;
import as.asd.myapp.trial_json.ReviewsResult;
import as.asd.myapp.trial_json.TrailerResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ApiService {
    @GET("movie?api_key="+BuildConfig.THE_MOVIE_DB_API_TOKEN )
    Call<MovieResponse> mostListMovies();

    @GET("movie?api_key="+BuildConfig.THE_MOVIE_DB_API_TOKEN +"&&sort_by=vote_average.desc")
    Call<MovieResponse> voteListMovies();


    @GET("movie?api_key=" + BuildConfig.THE_MOVIE_DB_API_TOKEN + "&&sort_by=popularity.asc")
    Call<MovieResponse> lowListMovies();


    @GET("{FILM_ID}?api_key="+BuildConfig.THE_MOVIE_DB_API_TOKEN )
    Call<MovieModel> Movie(@Path("FILM_ID") int filmID);

    @GET("{FILM_ID}/videos?api_key="+BuildConfig.THE_MOVIE_DB_API_TOKEN )
    Call<TrailerResult> trailer(@Path("FILM_ID") int filmID);

    @GET("{FILM_ID}/reviews?api_key="+BuildConfig.THE_MOVIE_DB_API_TOKEN )
    Call<ReviewsResult> reviews(@Path("FILM_ID") int filmID);
}
