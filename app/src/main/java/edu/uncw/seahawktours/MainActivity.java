package edu.uncw.seahawktours;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listView,
                                    View itemView,
                                    int position,
                                    long id) {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("buildingNum",position);
                    startActivity(intent);
            }
        };
        //Add the listener to the list view
        ListView listView= (ListView)findViewById(R.id.building_options);
        listView.setOnItemClickListener(itemClickListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }


/*    public boolean onMenuItemClicked(Menu menu){

    }*/

    public void onClickBuildingDetails(View view) {
        //Go to detail page


/*        //Get a reference to the spinner
        Spinner buildings = findViewById(R.id.buildings);
        //Get the selected item in the spinner
        building = String.valueOf(buildings.getSelectedItem());

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("MainActivity.building", building);
        startActivity(intent);*/
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
