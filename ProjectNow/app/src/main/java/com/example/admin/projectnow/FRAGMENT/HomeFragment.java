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
import com.example.admin.projectnow.MODEL.account;
import com.example.admin.projectnow.MODEL.storeLocation;
import com.example.admin.projectnow.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
    private static HomeFragment instance;
    private List<storeLocation> storeLocationList;
    private StoresAdapter storesAdapter;
    private account account;

    //region BindView
    @BindView(R.id.rv_stores) RecyclerView rv_stores;
    //endregion

    //region Instance
    public static HomeFragment Instance()
    {
        if(instance == null)
        {
            instance = new HomeFragment();
        }
        return instance;
    }
    private void Instance(HomeFragment instance)
    {
        HomeFragment.instance = instance;
    }
    //endregion

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        rv_stores.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        rv_stores.setHasFixedSize(true);
        GetData();
        UpdateInterface();
        return view;
    }

    public void UpdateInterface()
    {
        storeLocationList = storeLocationDAO.Instace(getContext()).GetLocation();
        storesAdapter = new StoresAdapter(storeLocationList, this, 1, account);
        rv_stores.setAdapter(storesAdapter);
    }
    private void GetData()
    {
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            account = (account)bundle.getSerializable("account");
        }
    }
}
