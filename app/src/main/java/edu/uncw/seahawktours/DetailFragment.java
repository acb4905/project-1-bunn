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


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    public DetailFragment() {
        // Required empty public constructor
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
            int id=getActivity().getIntent().getIntExtra(DetailActivity.EXTRA_BUILDINGID, 1);
            setBuilding(id);
        }
    }

    public void setBuilding(int id){
        View view =getView();
        Building building0=new Building(getString(R.string.cis), getActivity().getResources().getIdentifier("cis","drawable",getActivity().getPackageName()), getString(R.string.cisCaption), getString(R.string.cis_desc), getString(R.string.cisURL));
        Building building1 = new Building(getString(R.string.rl), getActivity().getResources().getIdentifier("randall","drawable",getActivity().getPackageName()),getString(R.string.rlCaption), getString(R.string.rl_desc), getString(R.string.rlURL));
        Building building2 = new Building(getString(R.string.dl), getActivity().getResources().getIdentifier("deloach_collage","drawable",getActivity().getPackageName()), getString(R.string.dlCaption),getString(R.string.dl_desc), getString(R.string.dlURL));
        Building building3 = new Building (getString(R.string.br), getActivity().getResources().getIdentifier("bear_hall","drawable",getActivity().getPackageName()), getString(R.string.brCaption), getString(R.string.br_desc), getString(R.string.brURL));
        Building building4 = new Building (getString(R.string.wa), getActivity().getResources().getIdentifier("wag","drawable",getActivity().getPackageName()), getString(R.string.waCaption), getString(R.string.wa_desc),getString(R.string.waURL));

        Building building = new Building("",1,"","","");
        building.loadBuilding(building0,building1,building2,building3,building4, id);

        TextView title=view.findViewById(R.id.detailTitle);
        title.setText(building.getName());

        ImageView buildingImage=view.findViewById(R.id.buildingImage);
        buildingImage.setImageResource(building.getImageResourceId());

        TextView caption=view.findViewById(R.id.caption);
        caption.setText(building.getCaption());

        TextView description=view.findViewById(R.id.description);
        description.setText(building.getDescription());

        TextView url=view.findViewById(R.id.url);
        url.setText(Html.fromHtml(building.getUrl()));
        url.setMovementMethod(LinkMovementMethod.getInstance());
    }


}


