package com.hgsft.justrun;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabView extends Fragment {

    public TabView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_view, container, false);
        TabLayout tabs = (TabLayout) view.findViewById(R.id.tab);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        TabsPagerAdapter adapter = new TabsPagerAdapter(getChildFragmentManager());
        //TODO
        adapter.addFragment(new LeftTab(), "Run");
        adapter.addFragment(new LeftTab(), "Trainer");
        viewPager.setAdapter(adapter);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        return view;
    }
}
