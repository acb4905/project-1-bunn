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

