package com.example.craiger.nav.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by craig on 2/2/16.
 */
public class GameListFragment extends AbstractNavFragment {
    private String mText;

    private PressSplitTestButtonCallback buttonCallback;

    public interface PressSplitTestButtonCallback {
        void handlePressSplitTestButton();
    }

    public static GameListFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString("key", text);
        GameListFragment fragment = new GameListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            buttonCallback = (PressSplitTestButtonCallback) context;
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
        b.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams
            .WRAP_CONTENT));
        b.setText("click me to toggle split test state");
        b.setGravity(Gravity.CENTER);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCallback.handlePressSplitTestButton();
            }
        });

        TextView tv = new TextView(inflater.getContext());
        tv.setTextColor(getResources().getColor(android.R.color.black, inflater.getContext().getTheme()));
        tv.setText(mText);
        tv.setGravity(Gravity.CENTER);

        frame.addView(b);
        frame.addView(tv);
        return frame;
    }
}
