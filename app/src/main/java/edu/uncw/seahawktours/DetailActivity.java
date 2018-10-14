package edu.uncw.seahawktours;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Spinner;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        String building = extras.getString("MainActivity.building");
        //How do you check which building was chosen!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        if (building.equals("@string/cis")){
            setContentView(R.layout.activity_detail);
        }
        if (building.equals("Randall Library (RL)")){
            setContentView(R.layout.activity_detail2);
        }
        else{
            setContentView(R.layout.activity_detail3);
        }

    }
}
