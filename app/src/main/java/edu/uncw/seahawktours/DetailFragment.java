package edu.uncw.seahawktours;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.objectbox.Box;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    private int buildingId;


    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            buildingId=savedInstanceState.getInt("buildingId");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);

    }

    public void onStart(){
        super.onStart();

        View view = getView();
        if (view != null){
            Box<Building> buildingBox=((App) getActivity().getApplication()).getBoxStore().boxFor(Building.class);
            drawBuilding(buildingId, buildingBox);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putLong("buildingId", buildingId);
    }

    public void setBuildingId(int id) {
        buildingId = id;
    }

    private void drawBuilding(int id, Box<Building> buildingBox){
        //get reference to building box and use ID, get methods from captioned images adapter.
        View view =getView();
        List<Building> buildings=buildingBox.getAll();
        Building building=buildings.get(id);
/*        Building building0=new Building(getString(R.string.cis), getContext().getResources().getIdentifier("cis","drawable",getActivity().getPackageName()), getString(R.string.cisCaption), getString(R.string.cis_desc), getString(R.string.cisURL));
        Building building1 = new Building(getString(R.string.rl), getContext().getResources().getIdentifier("randall","drawable",getActivity().getPackageName()),getString(R.string.rlCaption), getString(R.string.rl_desc), getString(R.string.rlURL));
        Building building2 = new Building(getString(R.string.dl), getContext().getResources().getIdentifier("deloach_collage","drawable",getActivity().getPackageName()), getString(R.string.dlCaption),getString(R.string.dl_desc), getString(R.string.dlURL));
        Building building3 = new Building (getString(R.string.br), getContext().getResources().getIdentifier("bear_hall","drawable",getActivity().getPackageName()), getString(R.string.brCaption), getString(R.string.br_desc), getString(R.string.brURL));
        Building building4 = new Building (getString(R.string.wa), getContext().getResources().getIdentifier("wag","drawable",getActivity().getPackageName()), getString(R.string.waCaption), getString(R.string.wa_desc),getString(R.string.waURL));

        Building building = new Building("",1,"","","");
        building.loadBuilding(building0,building1,building2,building3,building4, id);*/

        TextView title=view.findViewById(R.id.detailTitle);
        title.setText(building.getName());

        ImageView buildingImage=view.findViewById(R.id.buildingImage);
        buildingImage.setImageResource(getContext().getResources().getIdentifier(building.getImageResource(), "drawable", getContext().getPackageName()));

        TextView caption=view.findViewById(R.id.caption);
        caption.setText(building.getCaption());

        TextView description=view.findViewById(R.id.description);
        description.setText(building.getDescription());

        TextView url=view.findViewById(R.id.url);
        url.setText(Html.fromHtml(building.getUrl()));
        url.setMovementMethod(LinkMovementMethod.getInstance());
    }


}

