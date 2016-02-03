package com.example.craiger.nav.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.craiger.nav.nav.FragmentCreator;

/**
 * Created by craig on 2/2/16.
 */
public class PurchaseFragment extends AbstractNavFragment {
    public static final String TAG = "PurchaseFragment";

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    private String mText;

    public static PurchaseFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("key", text);
        PurchaseFragment fragment = new PurchaseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mText = getArguments().getString("key", "FORGOT TO ADD KEY");

        FrameLayout frame = new FrameLayout(inflater.getContext());
        frame.setBackgroundColor(getResources().getColor(android.R.color.white, inflater.getContext().getTheme()));
        frame.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams
            .MATCH_PARENT));
        TextView tv = new TextView(inflater.getContext());
        tv.setTextColor(getResources().getColor(android.R.color.black, inflater.getContext().getTheme()));
        tv.setText(mText);
        tv.setGravity(Gravity.CENTER);

        frame.addView(tv);
        return frame;
    }

    //TODO might move where and how this Tab object gets created.
    public static TabLayout.Tab getTab(TabLayout tabLayoutView) {
        return tabLayoutView.newTab().setText("$$$$").setContentDescription(TAG);
    }

    public static class Creator implements FragmentCreator {
        public Creator() {
        }

        @Override
        public String getTag() {
            return TAG;
        }

        public PurchaseFragment create() {
            return newInstance("Made from PurchaseFragment CREATOR");
        }
    }

}
