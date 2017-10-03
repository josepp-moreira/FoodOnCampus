package com.example.app.foodoncampus;

/**
 * Created by joseppmoreira on 02/10/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.FrameLayout;

import org.json.JSONException;

public class CanteensListActivity extends FragmentActivity implements CanteensListFragment.OnItemSelectedListener {
    private boolean isTwoPane = false;
    private String jsonResults;
    private MenuParser parser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteens_list);
        parser = new MenuParser();
        jsonResults = parser.retrieveMenu();
        try {
            String[] canteens = parser.getCanteens(jsonResults);
            for (int i = 0; i <canteens.length; i++) {
                Canteen.addItem(canteens[i], parser.getMenusDataFromJson(jsonResults, canteens[i]));
            }
        } catch (JSONException e) {
        }
        determinePaneLayout();
    }

    private void determinePaneLayout() {
        FrameLayout fragmentCanteenDetail = (FrameLayout) findViewById(R.id.flDetailContainer);
        if (fragmentCanteenDetail != null) {
            isTwoPane = true;
            CanteensListFragment fragmentItemsList =
                    (CanteensListFragment) getSupportFragmentManager().findFragmentById(R.id.all);
            fragmentItemsList.setActivateOnItemClick(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.canteens, menu);
        return true;
    }

    @Override
    public void onItemSelected(Canteen canteen) {
        if (isTwoPane) { // single activity with list and detail
            // Replace frame layout with correct detail fragment
            CanteenDetailFragment fragmentItem = CanteenDetailFragment.newInstance(canteen);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flDetailContainer, fragmentItem);
            ft.commit();
        } else { // separate activities
            // launch detail activity using intent
            Intent i = new Intent(this, CanteenDetailActivity.class);
            i.putExtra("canteen", canteen);
            startActivity(i);
        }
    }
}
