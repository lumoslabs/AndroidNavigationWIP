package com.example.craiger.nav.nav;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.support.design.widget.TabLayout;

import com.example.craiger.nav.R;
import com.example.craiger.nav.fragment.AbstractNavFragment;
import com.example.craiger.nav.fragment.DashboardFragment;
import com.example.craiger.nav.fragment.GameListFragment;
import com.example.craiger.nav.fragment.LabsFragment;
import com.example.craiger.nav.fragment.MoreFragment;
import com.example.craiger.nav.fragment.PurchaseFragment;
import com.example.craiger.nav.fragment.StatsFragment;

/**
 * This class is in charge of holding onto ALL Tabs, and also tabs that are considered "active"
 *
 * In the future, it should be registered on the bus to be able to react to changes, or it could be told to handle changes by
 * its owner (should be activity)
 *
 * Created by craig on 2/3/16.
 */
public class LumosTabHolder {

    private boolean mIsFreeUser, mOtherSplitTests;

    //This should be immutable once its created. I use this as a reference for computing which tabs should be in the
    // activeNavTabs collection
    private List<LumosNavTab> allNavTabs = new LinkedList<>();

    //This List will hold onto the tabs that should actually be displayed (the adapters will use this class
    private List<LumosNavTab> activeNavTabs = new LinkedList<>();


    public LumosTabHolder(TabLayout tabLayout, boolean isFreeUser, boolean otherSplitTests) {
        mIsFreeUser = isFreeUser;
        mOtherSplitTests = otherSplitTests;

        //add ALL of the tabs, their methods will determine if they get put into the layout
        allNavTabs.add(new DashboardTab(tabLayout));
        allNavTabs.add(new StatsTab(tabLayout));
        allNavTabs.add(new GamesTab(tabLayout));
        allNavTabs.add(new PurchaseTab(tabLayout));
        allNavTabs.add(new LabsTab(tabLayout));
        allNavTabs.add(new MoreTab(tabLayout));

        refreshActiveTabs();

    }

    /**
     * This will basically revalidate the activeNavTabs collection.
     */
    public void refreshActiveTabs() {
        activeNavTabs.clear();
        for (LumosNavTab tab : allNavTabs) {
            if (tab.shouldBeIncludedInLayout()) {
                activeNavTabs.add(tab);
            }
        }
    }

    /**
     * @return List of the "active" tabs
     */
    public List<LumosNavTab> getActiveTabs() {
        return activeNavTabs;
    }

    //TODO future hook up to event bus
    //@Subscribe
    public void userChangedEvent() {
        mIsFreeUser = !mIsFreeUser;
        refreshActiveTabs();
    }

    //TODO future hook up to event bus
    //@Subscribe - maybe when the user model updates... do some checks here
    public void splitTestChangedEvent() {
        mOtherSplitTests = !mOtherSplitTests;
        refreshActiveTabs();
    }


    //These classes all represent a tab
    public class DashboardTab extends LumosNavTab {
        private TabLayout.Tab mTab;

        public DashboardTab(TabLayout tabLayout) {
            mTab = tabLayout.newTab().setIcon(R.drawable.selector_home_tab);
        }

        @Override
        public TabLayout.Tab getTab() {
            return mTab;
        }

        @Override
        public AbstractNavFragment getFragment(Context context) {
            return DashboardFragment.newInstance("from DashboardTab in TABS class");
        }

        @Override
        public Class<? extends AbstractNavFragment> getNavFragmentClass() {
            return DashboardFragment.class;
        }

        @Override
        public boolean shouldBeIncludedInLayout() {
            return true;
        }
    }

    public class StatsTab extends LumosNavTab {
        private TabLayout.Tab mTab;

        public StatsTab(TabLayout tabLayout) {
            mTab = tabLayout.newTab().setIcon(R.drawable.selector_stats_tab);
        }

        @Override
        public TabLayout.Tab getTab() {
            return mTab;
        }

        @Override
        public AbstractNavFragment getFragment(Context context) {
            return StatsFragment.newInstance("from StatsTab in TABS class");
        }

        @Override
        public Class<? extends AbstractNavFragment> getNavFragmentClass() {
            return StatsFragment.class;
        }

        @Override
        public boolean shouldBeIncludedInLayout() {
            return true;
        }
    }

    public class PurchaseTab extends LumosNavTab {
        private TabLayout.Tab mTab;

        public PurchaseTab(TabLayout tabLayout) {
            mTab = tabLayout.newTab().setIcon(R.drawable.selector_unlock_tab);
        }

        @Override
        public TabLayout.Tab getTab() {
            return mTab;
        }

        @Override
        public AbstractNavFragment getFragment(Context context) {
            return PurchaseFragment.newInstance("from PurchaseTab in TABS class");
        }

        @Override
        public Class<? extends AbstractNavFragment> getNavFragmentClass() {
            return PurchaseFragment.class;
        }

        @Override
        public boolean shouldBeIncludedInLayout() {
            return mIsFreeUser;
        }
    }

    public class LabsTab extends LumosNavTab {
        private TabLayout.Tab mTab;

        public LabsTab(TabLayout tabLayout) {
            mTab = tabLayout.newTab().setIcon(R.drawable.selector_labs_tab);
        }

        @Override
        public TabLayout.Tab getTab() {
            return mTab;
        }

        @Override
        public AbstractNavFragment getFragment(Context context) {
            return LabsFragment.newInstance("from LabsTab in TABS class");
        }

        @Override
        public Class<? extends AbstractNavFragment> getNavFragmentClass() {
            return LabsFragment.class;
        }

        @Override
        public boolean shouldBeIncludedInLayout() {
            return !mIsFreeUser;
        }
    }

    public class GamesTab extends LumosNavTab {
        private TabLayout.Tab mTab;

        public GamesTab(TabLayout tabLayout) {
            mTab = tabLayout.newTab().setIcon(R.drawable.selector_game_tab);
        }

        @Override
        public TabLayout.Tab getTab() {
            return mTab;
        }

        @Override
        public AbstractNavFragment getFragment(Context context) {
            return GameListFragment.newInstance("from GamesTab in TABS class");
        }

        @Override
        public Class<? extends AbstractNavFragment> getNavFragmentClass() {
            return GameListFragment.class;
        }

        @Override
        public boolean shouldBeIncludedInLayout() {
            return true;
        }
    }

    public class MoreTab extends LumosNavTab {
        private TabLayout.Tab mTab;

        public MoreTab(TabLayout tabLayout) {
            mTab = tabLayout.newTab().setIcon(R.drawable.selector_more_tab);
        }

        @Override
        public TabLayout.Tab getTab() {
            return mTab;
        }

        @Override
        public AbstractNavFragment getFragment(Context context) {
            return MoreFragment.newInstance("from MoreTab in TABS class");
        }

        @Override
        public Class<? extends AbstractNavFragment> getNavFragmentClass() {
            return MoreFragment.class;
        }

        @Override
        public boolean shouldBeIncludedInLayout() {
            return mOtherSplitTests;
        }
    }

}
