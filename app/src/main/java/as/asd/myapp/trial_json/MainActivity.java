package as.asd.myapp.trial_json;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {
    List<MovieModel> arrayList;
    DataAdapter adapter;
    int s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GridView list = (GridView) findViewById(R.id.grid_films);
        adapter = new DataAdapter(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/discover/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);

        final Call<MovieResponse> repos = service.listMovies();
        repos.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, final Response<MovieResponse> response) {
                if (response != null) {
                    Toast.makeText(MainActivity.this, "results:" + response.body().getTotalResults(), Toast.LENGTH_SHORT).show();

                    arrayList = response.body().getResults();
                    adapter.setData(arrayList);
                    adapter.notifyDataSetChanged();

                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent=new Intent(MainActivity.this,details.class);
                            Toast.makeText(MainActivity.this,"position :"+position,Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
        list.setAdapter(adapter);

    }
    public interface Service {
        @GET("movie?api_key=00563628f45262fc9ea62d52baab6e4a")
        Call<MovieResponse> listMovies();
    }
}
