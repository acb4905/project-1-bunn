package edu.uncw.seahawktours;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity{

    public static final String EXTRA_BUILDINGID = "buildingId";
    Building chosenBuilding=new Building ("",1,"","","");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        DetailFragment frag = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detailFragment);
        int buildingId=getIntent().getIntExtra(DetailActivity.EXTRA_BUILDINGID, 0);
        frag.setBuildingId(buildingId);

    }

    //Creates menu on toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;

    }

    //Creates intents for toolbar buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //action to take when the action create order button is tapped
            case R.id.map:
                Intent intent1 = new Intent (this, MapActivity.class);
                startActivity(intent1);
                return true;
            case R.id.menu:
                Intent intent = new Intent (this, AboutActivity.class);
                startActivity(intent);
                return true;
/*            case R.id.up:
                Intent intentUp = new Intent (DetailActivity.this, MainActivity.class);
                startActivity(intentUp);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
