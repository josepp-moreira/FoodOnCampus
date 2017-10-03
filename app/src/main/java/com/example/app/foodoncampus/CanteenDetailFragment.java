package com.example.app.foodoncampus;

/**
 * Created by joseppmoreira on 02/10/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CanteenDetailFragment extends Fragment {
    private Canteen canteen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        canteen = (Canteen) getArguments().getSerializable("canteen");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_canteen_detail,
                container, false);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(canteen.getMeals());
        tvTitle.setMovementMethod(new ScrollingMovementMethod());
        return view;
    }

    // CanteenDetailFragment.newInstance(menu)
    public static CanteenDetailFragment newInstance(Canteen canteen) {
        CanteenDetailFragment fragmentDemo = new CanteenDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("canteen", canteen);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }
}
