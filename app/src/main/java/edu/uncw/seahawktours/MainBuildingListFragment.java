package edu.uncw.seahawktours;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import io.objectbox.Box;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainBuildingListFragment extends Fragment {

        interface Listener{
            void itemClicked(long id);
        }

    private Listener listener;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            RecyclerView mainRecycler=(RecyclerView) inflater.inflate(R.layout.fragment_main,container,false);


            Box<Building> buildingBox=((App) getActivity().getApplication()).getBoxStore().boxFor(Building.class);
            CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(buildingBox);
            //CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(buildingNames, buildingImages);
            mainRecycler.setAdapter(adapter);
            GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),2);
            mainRecycler.setLayoutManager(layoutManager);
            return mainRecycler;
        }

        @Override
        public void onAttach(Context context){
            super.onAttach(context);
            this.listener=(Listener) context;
        }

    }

