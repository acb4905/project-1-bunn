package edu.uncw.seahawktours;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity {

    protected String building;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClickBuildingDetails(View view){
        //Go to detail page
        //Get a reference to the spinner
        Spinner buildings = findViewById(R.id.buildings);
        //Get the selected item in the spinner
        building=String.valueOf(buildings.getSelectedItem());

        Intent intent = new Intent(this,DetailActivity.class);
        intent.putExtra("MainActivity.building",building);
        startActivity(intent);
        }
}
