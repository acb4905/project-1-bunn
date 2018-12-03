package edu.uncw.seahawktours;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import edu.uncw.seahawktours.Building;
import edu.uncw.seahawktours.MyObjectBox;
import edu.uncw.seahawktours.R;
import io.objectbox.Box;
import io.objectbox.BoxStore;

public class App extends Application {

    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the main data access object
        boxStore = MyObjectBox.builder().androidContext(App.this).build();

        // Get the wrapper (Box) for the Book table that lets us store Book objects
        Box<Building> buildingBox = boxStore.boxFor(Building.class);

        // Initialize with some data
        buildingBox.removeAll();
            List<Building> initialBuildings = new ArrayList<>();
            initialBuildings.add(new Building(getString(R.string.cis), "cis", getString(R.string.cisCaption), getString(R.string.cis_desc), getString(R.string.cisURL)));
            initialBuildings.add(new Building(getString(R.string.rl), "randall",getString(R.string.rlCaption), getString(R.string.rl_desc), getString(R.string.rlURL)));
            initialBuildings.add(new Building(getString(R.string.dl), "deloach", getString(R.string.dlCaption),getString(R.string.dl_desc), getString(R.string.dlURL)));
            initialBuildings.add(new Building (getString(R.string.br), "bear_hall", getString(R.string.brCaption), getString(R.string.br_desc), getString(R.string.brURL)));
            initialBuildings.add(new Building (getString(R.string.wa), "wag", getString(R.string.waCaption), getString(R.string.wa_desc),getString(R.string.waURL)));

            // ObjectBox is smart enough to handle CRUD on Collections of entities
            buildingBox.put(initialBuildings);

    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}