package com.example.app.foodoncampus;

/**
 * Created by joseppmoreira on 02/10/2017.
 */

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class CanteenDetailActivity extends FragmentActivity {
    CanteenDetailFragment fragmentCanteenDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteen_detail);
        Canteen canteen = (Canteen) getIntent().getSerializableExtra("canteen");
        if (savedInstanceState == null) {
            fragmentCanteenDetail = CanteenDetailFragment.newInstance(canteen);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flDetailContainer, fragmentCanteenDetail);
            ft.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.canteen_detail, menu);
        return true;
    }
}