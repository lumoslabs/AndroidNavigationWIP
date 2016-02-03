package com.example.craiger.nav.nav;

import com.example.craiger.nav.fragment.AbstractNavFragment;

/**
 * Created by craig on 2/1/16.
 */
public interface FragmentCreator {
    AbstractNavFragment create();
    String getTag();
}
