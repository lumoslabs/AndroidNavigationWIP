package com.example.craiger.nav.nav;

import android.content.Context;
import android.support.design.widget.TabLayout;

import com.example.craiger.nav.fragment.AbstractNavFragment;

/**
 * Base abstract class that represents a Tab that will go in our toolbar
 *
 * Created by craig on 2/3/16.
 */
public abstract class LumosNavTab {

    /**
     * Provide a getter for your tab. (the constructor should actually create it
     */
    public abstract TabLayout.Tab getTab();

    /**
     * Every subclass should provide a way to tell anybody whether or not he should be included in the "active" tabs
     *
     * @return whether or not to include me in the active tabs, based on business logic
     */
    public abstract boolean shouldBeIncludedInLayout();

    /**
     * Provide a way of creating an instance of the Fragment class
     *
     * @param context
     * @return
     */
    public abstract AbstractNavFragment getFragment(Context context);

    /**
     * Also provide a Class object. This is mainly used by the adapter to compare with other items
     *
     * @return
     */
    public abstract Class<? extends AbstractNavFragment> getNavFragmentClass();
}
