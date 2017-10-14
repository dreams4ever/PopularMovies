package as.asd.myapp.trial_json;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import as.asd.myapp.trial_json.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    List<MovieModel> mMoviesArray;
    MovieRecyclerAdapter mMoviesAdapter;
    MovieRecyclerAdapter mDesMoviesAdapter;
    MovieRecyclerAdapter mVoteMoviesAdapter;
    RecyclerView mMovieList;
    RecyclerView mDesMovieList;
    RecyclerView mVoteMovieList;
    Retrofit mRetrofit;
    ApiService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieList = (RecyclerView) findViewById(R.id.rv_popular);
        mDesMovieList = (RecyclerView) findViewById(R.id.rv_des);
        mVoteMovieList = (RecyclerView) findViewById(R.id.rv_rated);
        mMoviesAdapter = new MovieRecyclerAdapter(this);
        mDesMoviesAdapter = new MovieRecyclerAdapter(this);
        mVoteMoviesAdapter = new MovieRecyclerAdapter(this);

        mMovieList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mDesMovieList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mVoteMovieList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/discover/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = mRetrofit.create(ApiService.class);

        Call<MovieResponse> ascMoviesCall = mService.lowListMovies();
        ascMoviesCall.enqueue(new popularCallback());

        Call<MovieResponse> highMovieCall = mService.voteListMovies();
        highMovieCall.enqueue(new highVoteCallback());

        Call<MovieResponse> desMovieCall = mService.mostListMovies();
        desMovieCall.enqueue(new desCallback());

        mMovieList.setAdapter(mMoviesAdapter);
        mDesMovieList.setAdapter(mDesMoviesAdapter);
        mVoteMovieList.setAdapter(mVoteMoviesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public class popularCallback implements Callback<MovieResponse> {

        @Override
        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
            if (response != null) {
                mVoteMoviesAdapter.setData(response.body().getResults());
                mVoteMoviesAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFailure(Call<MovieResponse> call, Throwable t) {
            t.printStackTrace();
        }
    }
    public class highVoteCallback implements Callback<MovieResponse> {

        @Override
        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
            if (response != null) {
                mMoviesAdapter.setData(response.body().getResults());
                mMoviesAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFailure(Call<MovieResponse> call, Throwable t) {
            t.printStackTrace();
        }
    }
    public class desCallback implements Callback<MovieResponse> {

        @Override
        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
            if (response != null) {
                mDesMoviesAdapter.setData(response.body().getResults());
                mDesMoviesAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFailure(Call<MovieResponse> call, Throwable t) {
            t.printStackTrace();
        }
    }
}
