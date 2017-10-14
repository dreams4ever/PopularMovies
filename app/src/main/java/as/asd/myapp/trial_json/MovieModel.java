package as.asd.myapp.trial_json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class MovieModel extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("original_title")
    @Expose
    private String originalTitle;

    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("vote_average")
    @Expose
    private String rate;



    public Integer getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public Boolean getAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
    public String getRate() {
        return rate;
    }


    public void setId(Integer id) {
       this.id=id;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath=posterPath;
    }
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle=originalTitle;
    }

    public void setAdult(Boolean adult) {
        this.adult=adult;
    }

    public void setOverview(String overview) {
        this.overview=overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate=releaseDate;
    }

    public void setRate(String rate) {
        this.rate=rate;
    }
}

