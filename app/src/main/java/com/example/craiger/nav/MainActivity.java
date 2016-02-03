package com.example.craiger.nav;

import java.util.List;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.craiger.nav.fragment.DashboardFragment;
import com.example.craiger.nav.fragment.PurchaseFragment;
import com.example.craiger.nav.nav.LumosTabHelper;
import com.example.craiger.nav.nav.LumosTabManager;

public class MainActivity extends AppCompatActivity implements DashboardFragment.ButtonPress {

    private static String TAG = "MainActivity";

    LumosTabManager tabManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupTabbedNav();
    }

    private void setupTabbedNav() {
        final ViewPager mViewPager = (ViewPager) findViewById(R.id.activity_main_tabbed_pager);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_main_tabbed_tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabManager = new LumosTabManager(tabLayout, mViewPager, getSupportFragmentManager());

        //todo better way to get the TabHolder objects
        List<LumosTabManager.TabCreatorHolder> tabsToUse = LumosTabHelper.getTabsForUser(tabLayout, true);

        tabManager.initWithTabCreators(tabsToUse);
    }


    @Override
    public void handlePress() {
        tabManager.removeTab(PurchaseFragment.TAG);
    }
}
