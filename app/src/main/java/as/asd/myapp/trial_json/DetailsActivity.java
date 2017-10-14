package as.asd.myapp.trial_json;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import as.asd.myapp.trial_json.service.ApiService;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity {
    int mId;
    TextView mFilm_Title, mFilm_adult, mFilm_Rate, mFilm_Overview, mFilm_Date;
    RecyclerView mTrailers;
    RecyclerView mReviews;
    ImageView mFilm_Poster;
    MaterialFavoriteButton mMaterialFavoriteButton;
    List<TrailersModel> mMoviesTrailers;
    List<ReviewsModel> mMovieReviews;
    TrailersAdapter mTrailersAdapter;
    ReviewsAdapter mReviewsAdapter;
    Realm mRealm;
    MovieModel mModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_details);

        mFilm_Title = (TextView) findViewById(R.id.film_title);
        mFilm_Date = (TextView) findViewById(R.id.film_date);
        mFilm_adult = (TextView) findViewById(R.id.film_adult);
        mFilm_Rate = (TextView) findViewById(R.id.film_rate);
        mFilm_Overview = (TextView) findViewById(R.id.film_overview);
        mMaterialFavoriteButton = (MaterialFavoriteButton) findViewById(R.id.favorite_bt);


        mTrailers = (RecyclerView) findViewById(R.id.Rec_view_trailers);
        mReviews = (RecyclerView) findViewById(R.id.Rec_view_reviews);

        mFilm_Poster = (ImageView) findViewById(R.id.film_poster);

        mTrailersAdapter = new TrailersAdapter(DetailsActivity.this);
        mTrailers.setLayoutManager(new LinearLayoutManager(this));
        mTrailers.setHasFixedSize(true);
        mTrailers.setNestedScrollingEnabled(false);

        mReviewsAdapter = new ReviewsAdapter(DetailsActivity.this);
        mReviews.setLayoutManager(new LinearLayoutManager(this));
        mReviews.setHasFixedSize(true);
        mReviews.setNestedScrollingEnabled(false);

        Realm.init(this);
        mRealm = Realm.getDefaultInstance();


        Intent intent = getIntent();
        mId = intent.getIntExtra("key", 0);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        final Call<MovieModel> MovieCall = service.Movie(mId);
        MovieCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                mModel = mRealm.where(MovieModel.class).equalTo("id", response.body().getId()).findFirst();
                if (mModel != null)
                    mMaterialFavoriteButton.setFavorite(true);
                else {
                    mMaterialFavoriteButton.setFavorite(false);
                    mModel = response.body();
                }

                String title = response.body().getOriginalTitle();
                mFilm_Title.setText(title);
                String date = response.body().getReleaseDate();
                mFilm_Date.setText(date);
                Boolean adult = response.body().getAdult();

                if (adult == true)
                    mFilm_adult.setText("for adult only");
                else
                    mFilm_adult.setText("for all");

                String overview = response.body().getOverview();
                mFilm_Overview.setText(overview);

                String rate = response.body().getRate();
                mFilm_Rate.setText(rate + " /10");

                Picasso.with(DetailsActivity.this).load("http://image.tmdb.org/t/p/w500" + response.body().getPosterPath()).into(mFilm_Poster);

            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

        //MovieModelTrailer
        ApiService TrailerService = retrofit.create(ApiService.class);
        final Call<TrailerResult> TrailerMovieCall = TrailerService.trailer(mId);
        TrailerMovieCall.enqueue(new Callback<TrailerResult>() {
            @Override
            public void onResponse(Call<TrailerResult> call, final Response<TrailerResult> response) {
                if (response != null) {

                    mMoviesTrailers = response.body().getResults();
                    mTrailersAdapter.setData(mMoviesTrailers);
                    mTrailersAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<TrailerResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
        mTrailers.setAdapter(mTrailersAdapter);


        //MovieModelReviews
        ApiService ReviewsService = retrofit.create(ApiService.class);
        final Call<ReviewsResult> ReviewsMovieCall = ReviewsService.reviews(mId);
        ReviewsMovieCall.enqueue(new Callback<ReviewsResult>() {
            @Override
            public void onResponse(Call<ReviewsResult> call, Response<ReviewsResult> response) {
                if (response != null) {
                    mMovieReviews = response.body().getResults();
                    mReviewsAdapter.setData(mMovieReviews);
                    mReviewsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ReviewsResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
        mReviews.setAdapter(mReviewsAdapter);

        //Favorite button

        mMaterialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                if (favorite) {
                    saveFavorite();
                } else {
                    removeFavorite();
                }
            }
        });
    }

    public void watchYoutubeVideo(String key) {
        Intent applicationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + key));
        try {
            startActivity(applicationIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(browserIntent);
        }
    }

    public void saveFavorite() {
        mRealm.beginTransaction();
        mRealm.copyToRealm(mModel);
        mRealm.commitTransaction();
        mMaterialFavoriteButton.setFavorite(true);
    }

    public void removeFavorite() {
        mRealm.beginTransaction();
        MovieModel model = mRealm.where(MovieModel.class).equalTo("id", mModel.getId()).findFirst();
        if (model != null)
            model.deleteFromRealm();
        mRealm.commitTransaction();
        mMaterialFavoriteButton.setFavorite(false);
    }

}

