package com.onlineexamination.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 庞品 on 2017/2/9.
 */

public class MenuTipAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragmentList;

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public MenuTipAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragmentList = fragments;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }
}
