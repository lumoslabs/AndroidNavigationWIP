package com.example.craiger.nav.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.craiger.nav.nav.FragmentCreator;

/**
 * Created by craig on 2/2/16.
 */
public class DashboardFragment extends AbstractNavFragment {
    private static final String TAG = "DashboardFragment";

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    private String mText;

    public interface ButtonPress {
        void handlePress();
    }

    private ButtonPress pressListener;

    public static DashboardFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("key", text);
        DashboardFragment fragment = new DashboardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            pressListener = (ButtonPress) context;
        } catch (ClassCastException cce) {
            throw new RuntimeException("need to implement this damnit");
        }
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
        Button b = new Button(inflater.getContext());
        b.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams
            .WRAP_CONTENT));
        b.setText(mText);
        b.setGravity(Gravity.CENTER);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressListener.handlePress();
            }
        });
        frame.addView(b);
        return frame;
    }

    //TODO might move where and how this Tab object gets created.
    public static TabLayout.Tab getTab(TabLayout tabLayoutView) {
        return tabLayoutView.newTab().setText("DASH").setContentDescription(TAG);
    }

    public static class Creator implements FragmentCreator {
        public Creator() {
        }

        @Override
        public String getTag() {
            return TAG;
        }

        public DashboardFragment create() {
            return newInstance("DashboardCreator");
        }
    }

}
