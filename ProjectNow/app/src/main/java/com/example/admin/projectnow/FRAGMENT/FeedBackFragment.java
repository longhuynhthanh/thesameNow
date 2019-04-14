package com.example.admin.projectnow.FRAGMENT;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.projectnow.R;

public class FeedBackFragment extends Fragment {
    private static FeedBackFragment instance;

    //region Instance
    public static FeedBackFragment Instance()
    {
        if(instance == null)
        {
            instance = new FeedBackFragment();
        }
        return instance;
    }
    private void Instance(FeedBackFragment instance)
    {
        FeedBackFragment.instance = instance;
    }
    //endregion

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        return view;
    }
}
