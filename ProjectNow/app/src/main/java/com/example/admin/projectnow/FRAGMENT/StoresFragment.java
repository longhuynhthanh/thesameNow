package com.example.admin.projectnow.FRAGMENT;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.projectnow.ADAPTER.StoresAdapter;
import com.example.admin.projectnow.DAO.storeLocationDAO;
import com.example.admin.projectnow.MODEL.storeLocation;
import com.example.admin.projectnow.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoresFragment extends Fragment {
    private static StoresFragment instance;
    private List<storeLocation> storeLocationList;
    private StoresAdapter storesAdapter;

    //region BindView
    @BindView(R.id.rv_stores) RecyclerView rvStores;
    //endregion

    //region Instance
    public static StoresFragment Instance()
    {
        if(instance == null)
        {
            instance = new StoresFragment();
        }
        return instance;
    }
    private void Instance(StoresFragment instance)
    {
        StoresFragment.instance = instance;
    }
    //endregion


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stores, container, false);
        ButterKnife.bind(this, view);
        rvStores.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        rvStores.setHasFixedSize(true);

        UpdateInterface();
        return view;
    }

    public void UpdateInterface()
    {
        storeLocationList = storeLocationDAO.Instace(getContext()).GetLocation();
        storesAdapter = new StoresAdapter(storeLocationList, this, 0, null);
        rvStores.setAdapter(storesAdapter);
    }
}
