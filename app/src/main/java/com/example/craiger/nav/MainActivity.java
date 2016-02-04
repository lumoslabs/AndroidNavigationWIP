package com.example.craiger.nav;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.craiger.nav.fragment.DashboardFragment;
import com.example.craiger.nav.fragment.GameListFragment;
import com.example.craiger.nav.nav.LumosTabHolder;
import com.example.craiger.nav.nav.LumosTabbedView;

public class MainActivity extends AppCompatActivity implements DashboardFragment.ButtonPress, GameListFragment
    .PressSplitTestButtonCallback {

    private static String TAG = "MainActivity";

    LumosTabbedView newTabView;
    LumosTabHolder mAllLumosTabHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupTabbedNavWithTabsObject();
    }

    private void setupTabbedNavWithTabsObject() {
        final ViewPager mViewPager = (ViewPager) findViewById(R.id.activity_main_tabbed_pager);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_main_tabbed_tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //this will insantiate all of the TabLayout.Tab objects
        mAllLumosTabHolder = new LumosTabHolder(tabLayout, true, true);
        newTabView = new LumosTabbedView(tabLayout, mViewPager, getSupportFragmentManager(), mAllLumosTabHolder.getActiveTabs());
    }


    @Override
    public void handlePress() {
        //tell the Tabs object that some data changed (would be coming in from bus or something)
        //then tell the views to refresh (will reset the tabLayout, and notify on the adapter
        mAllLumosTabHolder.userChangedEvent();
        newTabView.reloadTabAndAdapter();
    }

    @Override
    public void handlePressSplitTestButton() {
        mAllLumosTabHolder.splitTestChangedEvent();
        newTabView.reloadTabAndAdapter();

    }
}
