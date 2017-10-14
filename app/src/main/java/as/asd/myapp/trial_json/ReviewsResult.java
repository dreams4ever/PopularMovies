package as.asd.myapp.trial_json;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsResult {

        @SerializedName("results")
        @Expose
        private List<ReviewsModel> results = null;

        public List<ReviewsModel> getResults() {
            return results;
        }
    }


