package com.eye2web.travel.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.eye2web.travel.fragment.DetailArroundFragment;
import com.eye2web.travel.fragment.DetailCommonFragment;
import com.eye2web.travel.vo.DetailCommonItem;
import com.eye2web.travel.vo.ListItem;

public class DetailViewTabPagerAdapger extends FragmentPagerAdapter {

    private DetailCommonItem detailCommonItem;
    private ListItem item;
    private FragmentManager fragmentManager;
    private double mapx = 0;
    private double mapy = 0;
    private Bundle bundle;

    public DetailViewTabPagerAdapger(FragmentManager fragmentManager
            , DetailCommonItem detailCommonItem, ListItem item, double mapx, double mapy) {
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
        this.detailCommonItem = detailCommonItem;
        this.item = item;
        this.mapx = mapx;
        this.mapy = mapy;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        bundle = new Bundle();
        bundle.putSerializable("detailCommonItem", detailCommonItem);
        bundle.putSerializable("item", item);

        switch(position) {
            case 0 :
                fragment = new DetailCommonFragment();
                fragment.setArguments(bundle);
                break;

                /**
            case 1 :
                fragment = new DetailInfoFragment();
                fragment.setArguments(bundle);
                break;

            case 2 :
                fragment = new DetailMapFragment();
                fragment.setArguments(bundle);
                break;
            **/
            case 1 :
                fragment = new DetailArroundFragment();
                fragment.setArguments(bundle);
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    /**
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";

        switch (position) {
            case 0 :
                title = "기본정보";
                break;

            case 1 :
                title = "상세정보";
                break;

            case 2 :
                title = "위치정보";
                break;

            case 3 :
                title = "주변정보";
                break;
        }

        return title;
    }
    **/
}
