package com.example.craiger.nav.nav;

import java.util.List;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;

import com.example.craiger.nav.LLog;
import com.example.craiger.nav.fragment.AbstractNavFragment;

/**
 * This class encapsulates both the Tab layout, and the View pager. It manages the relationship between the two.
 *
 * Created by craig on 2/2/16.
 */
public class LumosTabbedView {

    private TabLayout mTabLayout;
    private LumosTabFragmentAdapter mAdapter;
    private ViewPager mViewPager;

    private ArrayMap<CharSequence, FragmentCreator> mTabCreatorMap = new ArrayMap<>();

    //this class will be the middle man between the TabLayout, and the adapter.

    public LumosTabbedView(TabLayout tabLayout, ViewPager viewPager, FragmentManager fm) {
        mTabLayout = tabLayout;
        mViewPager = viewPager;
        mAdapter = new LumosTabFragmentAdapter(fm);
    }

    //TODO make a LumosTab class that encapsulates the tab and the string tag
    public void removeTab(String tabTag) {
        TabLayout.Tab tab = findTabByIdentifier(tabTag);
        if (tab != null) {
            LLog.d("CRAIG", "Removing tab = %s", tab.getContentDescription());
            mTabCreatorMap.remove(tab.getContentDescription());
            mTabLayout.removeTab(tab);
            mAdapter.notifyDataSetChanged();
        } else {
            LLog.d("CRAIG", "cant find tab with identifier = %s", tabTag);
        }
    }

    private TabLayout.Tab findTabByIdentifier(String id) {
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            String tabCreatorTag = tab.getContentDescription().toString();
            if (tabCreatorTag.equals(id)) {
                LLog.d("CRAIG", "found a tag match %s at index %d", tabCreatorTag, i);
                return tab;
            }
        }
        return null;
    }

    public void initWithTabCreators(List<TabCreatorHolder> listTabCreators) {
        if (mTabLayout.getTabCount() > 0) {
            throw new RuntimeException("can only call init once!");
        }
        initTabs(listTabCreators);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void initTabs(List<TabCreatorHolder> listTabCreators) {
        for (TabCreatorHolder holder : listTabCreators) {
            TabLayout.Tab tabToAdd = holder.mTab;

            //TODO use something better than the description
            mTabCreatorMap.put(tabToAdd.getContentDescription(), holder.mCreator);
            mTabLayout.addTab(tabToAdd);
        }
    }


    /**
     * This data structure hold onto a Tab, and also a Creator which is an object that knows how to create the fragment
     * represented by the Tab. (Instead of holding onto the fragment itself, which only the FM should hold onto it)
     */
    public static class TabCreatorHolder {

        public TabLayout.Tab mTab;
        public FragmentCreator mCreator;

        public TabCreatorHolder(TabLayout.Tab tab, FragmentCreator creator) {
            mTab = tab;
            mCreator = creator;
        }
    }

    public class LumosTabFragmentAdapter extends FragmentStatePagerAdapter {
        public LumosTabFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            TabLayout.Tab tab = mTabLayout.getTabAt(position);

            //TODO use something better than the description
            FragmentCreator creator = mTabCreatorMap.get(tab.getContentDescription());
            LLog.d("CRAIG", "getItem at index %d... creator = %s", position, creator.getTag());

            return creator.create();
        }

        @Override
        public int getCount() {
            return mTabLayout.getTabCount();
        }

        /**
         * is only called when dataSetChanged. We need to see if this Fragment (object) that is passed in, still deserves to be
         * here. We iterate over all of the Tab objects in our current mTabLayout, and if this Fragment's "tag" matches any of
         * the tab's tag, then we return the position (index) of where that tab lived in its layout.
         *
         * if we didn't find where this fragment should be in relation to the tabs, then we return NONE and the framework will
         * take care of cleaning it up.
         *
         * @param object
         * @return
         */
        @Override
        public int getItemPosition(Object object) {
            AbstractNavFragment frag = (AbstractNavFragment) object;
            String fragTag = frag.getFragmentTag();

            for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = mTabLayout.getTabAt(i);

                String tabTag = tab.getContentDescription().toString();

                if (fragTag.equals(tabTag)) {
                    LLog.d("CRAIG", "found a tag match %s at index %d", tabTag, i);
                    return i;
                }
            }

            LLog.d("CRAIG", "DID NOT FIND A POSITION FOR %s ", fragTag);

            return POSITION_NONE;

        }
    }
}
