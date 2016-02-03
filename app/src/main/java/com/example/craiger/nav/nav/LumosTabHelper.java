package com.example.craiger.nav.nav;

import java.util.LinkedList;
import java.util.List;

import android.support.design.widget.TabLayout;

import com.example.craiger.nav.fragment.DashboardFragment;
import com.example.craiger.nav.fragment.GameListFragment;
import com.example.craiger.nav.fragment.PurchaseFragment;
import com.example.craiger.nav.nav.LumosTabbedView.TabCreatorHolder;

/**
 * Created by craig on 2/1/16.
 */
public class LumosTabHelper {


    public static List<TabCreatorHolder> getTabsForUser(TabLayout tabLayoutView, boolean isFreeUser) {
        List<TabCreatorHolder> tabs = new LinkedList<>();


        tabs.add(new TabCreatorHolder(DashboardFragment.getTab(tabLayoutView), new DashboardFragment.Creator()));
        if (isFreeUser) {
            tabs.add(new TabCreatorHolder(PurchaseFragment.getTab(tabLayoutView), new PurchaseFragment.Creator()));
        }
        tabs.add(new TabCreatorHolder(GameListFragment.getTab(tabLayoutView), new GameListFragment.Creator()));

        return tabs;
    }

}
