package edu.uncw.seahawktours;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;



/**
 * A simple {@link Fragment} subclass.
 */
public class MainBuildingListFragment extends ListFragment {

        static interface Listener{
            void itemClicked(long id);
        };

        private Listener listener;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            String[] buildings = new String []{getString(R.string.cis), getString(R.string.rl), getString(R.string.dl), getString(R.string.br), getString(R.string.wa)};
            ArrayAdapter<String> adapter =new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_activated_1,buildings);
            setListAdapter(adapter);
            //container.setBackgroundColor(Color.parseColor("#7FFFFFFF"));
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        @Override
        public void onAttach(Context context){
            super.onAttach(context);
            this.listener=(Listener) context;
        }

        @Override
        public void onListItemClick(ListView listView, View itemView, int position, long id){
            if (listener != null){
                listener.itemClicked(id);
            }
        }
    }

