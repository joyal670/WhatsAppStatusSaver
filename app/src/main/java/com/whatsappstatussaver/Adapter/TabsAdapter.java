package com.whatsappstatussaver.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.whatsappstatussaver.Fragment.ImagesFragment;
import com.whatsappstatussaver.Fragment.VideoFragment;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public TabsAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                ImagesFragment home = new ImagesFragment();
                return home;
            case 1:
                VideoFragment about = new VideoFragment();
                return about;

            default:
                return null;
        }
    }
}
