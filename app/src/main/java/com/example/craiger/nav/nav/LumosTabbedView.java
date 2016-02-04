package com.example.craiger.nav.nav;

import java.util.List;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.craiger.nav.FragmentItemIdStatePagerAdapter;
import com.example.craiger.nav.LLog;
import com.example.craiger.nav.fragment.AbstractNavFragment;

/**
 * This class encapsulates both the Tab layout, and the View pager. It manages the relationship between the two.
 *
 * Created by craig on 2/2/16.
 */
public class LumosTabbedView {

    private TabLayout mTabLayout;
    private LumosTabFragmentItemIdStatePagerAdapter mAdapter;
    private ViewPager mViewPager;
    private TabLayout.TabLayoutOnPageChangeListener mTabChangedListener;

    private List<LumosNavTab> mActiveNavTabs;

    public LumosTabbedView(TabLayout tabLayout, ViewPager viewPager, FragmentManager fm, List<LumosNavTab> activeNavTabs) {
        mActiveNavTabs = activeNavTabs;
        mTabLayout = tabLayout;
        mViewPager = viewPager;
        mTabChangedListener = new TabLayout.TabLayoutOnPageChangeListener(mTabLayout);

        //add the "active" tabs into the tabLayout
        for (LumosNavTab tab : mActiveNavTabs) {
            mTabLayout.addTab(tab.getTab());
        }

        //also give our tabs to the adapter
        mAdapter = new LumosTabFragmentItemIdStatePagerAdapter(fm, mActiveNavTabs);

        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(mTabChangedListener);
        mTabLayout.setOnTabSelectedListener(mLumosTabSelectedListener);

    }

    /**
     * Now that the underlying LumosTabNav objects might have changed, we need to reset the adapter's collection to only
     * include those that are "active". We will simply clear out the "active" collection, and the add back in the ones that are
     * now active
     *
     * because the adapter's might pick up on this change, we need to temporarily remove the change listeners on the tab and
     * the viewpager. We then restore them when we are done mucking around with the underlying collection
     *
     * We also need to reset which Tab is currently selected.
     */
    public void reloadTabAndAdapter() {

        mTabLayout.setOnTabSelectedListener(null);
        mViewPager.removeOnPageChangeListener(mTabChangedListener);
        mTabLayout.removeAllTabs();
        //add the "active" tabs into the tabLayout
        for (LumosNavTab tab : mActiveNavTabs) {
            mTabLayout.addTab(tab.getTab());
        }
        mTabLayout.setOnTabSelectedListener(mLumosTabSelectedListener);
        mViewPager.addOnPageChangeListener(mTabChangedListener);
        mAdapter.notifyDataSetChanged();

        int currentItemIndex = mViewPager.getCurrentItem();
        TabLayout.Tab currentTab = mTabLayout.getTabAt(currentItemIndex);
        if (currentTab != null) {
            currentTab.select();
        }
    }

    private TabLayout.OnTabSelectedListener mLumosTabSelectedListener = new TabLayout.OnTabSelectedListener() {
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
    };

    /**
     * This adapter currently uses the parent class's mTabLayout to
     */
    public class LumosTabFragmentItemIdStatePagerAdapter extends FragmentItemIdStatePagerAdapter {
        private List<LumosNavTab> mTabs;

        public LumosTabFragmentItemIdStatePagerAdapter(FragmentManager fm, List<LumosNavTab> tabs) {
            super(fm);
            mTabs = tabs;
        }

        /**
         * I have to return a unique identifier for the item that should be in ths position slot.
         *
         * In this case, i am choosing to use a hash code of the class name of the fragment the tab represents, which will be
         * unique
         *
         * @param position
         * @return a hash code of the class name of the fragment the tab represents
         */
        @Override
        public long getItemId(int position) {
            LumosNavTab tab = mTabs.get(position);
            int hashCode = tab.getNavFragmentClass().getSimpleName().hashCode();
            LLog.d("CRAIG", "getItemId  hashcode = %d", hashCode);

            return hashCode;
        }

        @Override
        public Fragment getItem(int position) {
            LumosNavTab tab = mTabs.get(position);
            AbstractNavFragment f = tab.getFragment(mTabLayout.getContext());

            LLog.d("CRAIG", "getItem frag = %s position %d ", f.getClass().getSimpleName(), position);
            return f;
        }

        @Override
        public int getCount() {
            return mTabs.size();
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

            Class navClass = object.getClass();
            for (int i = 0; i < mTabs.size(); i++) {
                LumosNavTab navTab = mTabs.get(i);

                if (navClass.equals(navTab.getNavFragmentClass())) {
                    LLog.d("CRAIG", "found a tag match %s at index %d", navClass.getSimpleName(), i);
                    return i;
                }
            }

            LLog.d("CRAIG", "DID NOT FIND A POSITION FOR %s ", navClass.getSimpleName());

            return POSITION_NONE;
        }
    }


    //TODO This is a regular (non-state) pagerAdapter. We should use this when testing with our bigger fragments
    public class LumosTabFragmentAdapter extends FragmentPagerAdapter {
        private List<LumosNavTab> mTabs;

        public LumosTabFragmentAdapter(FragmentManager fm, List<LumosNavTab> tabs) {
            super(fm);
            mTabs = tabs;
        }

        /**
         * I have to return a unique identifier for the item that should be in ths position slot.
         *
         * In this case, i am choosing to use a hash code of the class name of the fragment the tab represents, which will be
         * unique
         *
         * @param position
         * @return a hash code of the class name of the fragment the tab represents
         */
        @Override
        public long getItemId(int position) {
            LumosNavTab tab = mTabs.get(position);
            int hashCode = tab.getNavFragmentClass().getSimpleName().hashCode();
            LLog.d("CRAIG", "getItemId  hashcode = %d", hashCode);

            return hashCode;
        }

        @Override
        public Fragment getItem(int position) {
            LumosNavTab tab = mTabs.get(position);
            AbstractNavFragment f = tab.getFragment(mTabLayout.getContext());

            LLog.d("CRAIG", "getItem frag = %s position %d ", f.getClass().getSimpleName(), position);
            return f;
        }

        @Override
        public int getCount() {
            return mTabs.size();
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
            Class navClass = object.getClass();
            for (int i = 0; i < mTabs.size(); i++) {
                LumosNavTab navTab = mTabs.get(i);

                if (navClass.equals(navTab.getNavFragmentClass())) {
                    LLog.d("CRAIG", "found a tag match %s at index %d", navClass.getSimpleName(), i);
                    return i;
                }
            }

            LLog.d("CRAIG", "DID NOT FIND A POSITION FOR %s ", navClass.getSimpleName());
            return POSITION_NONE;
        }
    }
}
