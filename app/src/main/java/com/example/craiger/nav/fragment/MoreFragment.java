package com.example.craiger.nav.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by craig on 2/2/16.
 */
public class MoreFragment extends AbstractNavFragment {
    private String mText;

    public static MoreFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("key", text);
        MoreFragment fragment = new MoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mText = getArguments().getString("key", "FORGOT TO ADD KEY");

        FrameLayout frame = new FrameLayout(inflater.getContext());
        frame.setBackgroundColor(ContextCompat.getColor(inflater.getContext(), android.R.color.white));
        frame.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams
            .MATCH_PARENT));

        TextView tv = new TextView(inflater.getContext());
        tv.setTextColor(ContextCompat.getColor(inflater.getContext(), android.R.color.black));
        tv.setText(mText);
        tv.setGravity(Gravity.CENTER);

        frame.addView(tv);
        return frame;
    }
}
