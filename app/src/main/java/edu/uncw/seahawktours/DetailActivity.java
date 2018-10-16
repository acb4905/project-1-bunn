package edu.uncw.seahawktours;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String building = intent.getStringExtra("MainActivity.building");
        //How do you check which building was chosen!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        if (building.equals("Computer Information Systems (CIS)")) {
            setContentView(R.layout.activity_detail);
        } else {
            if (building.equals("Randall Library (RL)")) {
                setContentView(R.layout.activity_detail2);
            } else {
                setContentView(R.layout.activity_detail3);
            }
        }

    }

    public void onClickBack(View view) {
        Intent intent2 = new Intent(this, MainActivity.class);
        startActivity(intent2);
    }
}
