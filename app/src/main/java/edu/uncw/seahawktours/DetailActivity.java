package edu.uncw.seahawktours;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int buildingId=(Integer)getIntent().getIntExtra("buildingNum",0);

        Building building0=new Building(getString(R.string.cis), getResources().getIdentifier("cis","drawable",getPackageName()), getString(R.string.cisCaption), getString(R.string.cis_desc), getString(R.string.cisURL));
        Building building1 = new Building(getString(R.string.rl), getResources().getIdentifier("randall","drawable",getPackageName()),getString(R.string.rlCaption), getString(R.string.rl_desc), getString(R.string.rlURL));
        Building building2 = new Building(getString(R.string.dl), getResources().getIdentifier("deloach_collage","drawable",getPackageName()), getString(R.string.dlCaption),getString(R.string.dl_desc), getString(R.string.dlURL));
        Building building3 = new Building (getString(R.string.br), getResources().getIdentifier("bear_hall","drawable",getPackageName()), getString(R.string.brCaption), getString(R.string.br_desc), getString(R.string.brURL));
        Building building4 = new Building (getString(R.string.wa), getResources().getIdentifier("wag","drawable",getPackageName()), getString(R.string.waCaption), getString(R.string.wa_desc),getString(R.string.waURL));

        Building building = new Building("",1,"","","");
        building.loadBuilding(building0,building1,building2,building3,building4,buildingId);

        TextView title=findViewById(R.id.detailTitle);
        title.setText(building.getName());

        ImageView buildingImage=findViewById(R.id.buildingImage);
        buildingImage.setImageResource(building.getImageResourceId());

        TextView caption=findViewById(R.id.caption);
        caption.setText(building.getCaption());

        TextView description=findViewById(R.id.description);
        description.setText(building.getDescription());

        TextView url=findViewById(R.id.url);
        url.setText(Html.fromHtml(building.getUrl()));
        url.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //action to take when the action create order button is tapped
            case R.id.menu:
                Intent intent = new Intent (this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.up:
                Intent intentUp = new Intent (DetailActivity.this, MainActivity.class);
                startActivity(intentUp);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
