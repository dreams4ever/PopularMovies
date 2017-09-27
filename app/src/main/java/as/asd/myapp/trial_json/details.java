package as.asd.myapp.trial_json;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class details extends Activity {
TextView film_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        film_title= (TextView) findViewById(R.id.film_title);


    }
}
