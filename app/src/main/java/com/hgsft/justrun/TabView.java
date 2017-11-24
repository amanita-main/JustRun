package com.hgsft.justrun;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
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

    LayoutInflater mInflater;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_view, container, false);
        TabLayout tabs = (TabLayout) view.findViewById(R.id.tab);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        TabsPagerAdapter adapter = new TabsPagerAdapter();

        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
        tabs.setLeft(0);

        return view;
    }

    public  class TabsPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //TODO
            //LayoutInflater inflater = getLayoutInflater();
            View tabView = mInflater.inflate(R.layout.fragment_left_tab, container, false);
            container.addView(tabView);
            return tabView;
        }

    }

}
