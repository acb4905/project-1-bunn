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
        url.setText(building.getUrl());
        url.setText(Html.fromHtml(building.getUrl()));
        url.setMovementMethod(LinkMovementMethod.getInstance());
    }

/*        TextView title=findViewById(R.id.detailTitle);
        ImageView buildingImage=findViewById(R.id.buildingImage);
        TextView caption=findViewById(R.id.caption);
        TextView description1=findViewById(R.id.description1);
        TextView description2=findViewById(R.id.description2);
        TextView description3=findViewById(R.id.description3);
        String cisUrl = getResources().getString(R.string.cisURL);
        String rlUrl=getResources().getString(R.string.rlURL);
        String dlUrl=getResources().getString(R.string.dlURL);
        TextView url=findViewById(R.id.url);
        if (building.equals("Computer Information Systems (CIS)")) {
            title.setText(getString(R.string.cis));
            buildingImage.setImageResource(R.drawable.cis);
            caption.setText(getString(R.string.cisCaption));
            description1.setText(getString(R.string.building1_desc1));
            description2.setText(getString(R.string.building1_desc2));
            description3.setText(getString(R.string.building1_desc3));
            url.setText(cisUrl);
            url.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            if (building.equals("Randall Library (RL)")) {
                title.setText(getString(R.string.rl));
                buildingImage.setImageResource(R.drawable.randall);
                caption.setText(getString(R.string.rlCaption));
                description1.setText(getString(R.string.building2_desc));
                description2.setText("");
                description3.setText("");
                url.setText(rlUrl);
                url.setMovementMethod(LinkMovementMethod.getInstance());
            } else {
                title.setText(getString(R.string.dl));
                buildingImage.setImageResource(R.drawable.deloach_collage);
                caption.setText(getString(R.string.dlCaption));
                description1.setText(getString(R.string.building3_desc));
                description2.setText("");
                description3.setText("");
                url.setText(dlUrl);
                url.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }*/



    public void onClickBack(View view) {
        Intent intent2 = new Intent(this, MainActivity.class);
        startActivity(intent2);
    }
}
