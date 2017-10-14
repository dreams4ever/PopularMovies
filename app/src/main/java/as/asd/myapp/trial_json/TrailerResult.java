package as.asd.myapp.trial_json;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResult {
    @SerializedName("results")
    @Expose
    private List<TrailersModel> results = null;

    public List<TrailersModel> getResults() {
        return results;
    }
}
