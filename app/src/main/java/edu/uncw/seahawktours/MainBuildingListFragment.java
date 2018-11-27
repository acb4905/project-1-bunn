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


/**
 * A simple {@link Fragment} subclass.
 */
public class MainBuildingListFragment extends Fragment {

        static interface Listener{
            void itemClicked(long id);
        };

        private Listener listener;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            RecyclerView mainRecycler=(RecyclerView) inflater.inflate(R.layout.fragment_main,container,false);
            String[] buildingNames = new String []{getString(R.string.cis), getString(R.string.rl), getString(R.string.dl), getString(R.string.br), getString(R.string.wa)};
            int[] buildingImages = new int[]{getResources().getIdentifier("cis", "drawable", getActivity().getPackageName()),getResources().getIdentifier("randall", "drawable", getActivity().getPackageName())
                    ,getResources().getIdentifier("deloach_collage", "drawable", getActivity().getPackageName()),getResources().getIdentifier("bear_hall", "drawable", getActivity().getPackageName()),
                    getResources().getIdentifier("wag", "drawable", getActivity().getPackageName())};
            CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(buildingNames, buildingImages);
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

