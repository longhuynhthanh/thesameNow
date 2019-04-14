package com.example.admin.projectnow.FRAGMENT;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.projectnow.ADAPTER.FavoritesAdapter;
import com.example.admin.projectnow.DAO.favoritesDAO;
import com.example.admin.projectnow.MODEL.favorites;
import com.example.admin.projectnow.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesFragment extends Fragment {
    private static FavoritesFragment instance;
    private List<favorites> favoritesList;
    private FavoritesAdapter favoritesAdapter;
    //region BindView
    @BindView(R.id.rvFavorites)RecyclerView recyclerView;
    //endregion

    //region Instance
    public static FavoritesFragment Instance()
    {
        if(instance == null)
        {
            instance = new FavoritesFragment();
        }
        return instance;
    }
    private void Instance(FavoritesFragment instance)
    {
        FavoritesFragment.instance = instance;
    }
    //endregion

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, view);
        SetInterface();
        return view;
    }

    //region Method
    private void SetInterface()
    {
        favoritesList = favoritesDAO.Instance(getContext()).GetFavorites();
        favoritesAdapter = new FavoritesAdapter(favoritesList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(favoritesAdapter);
    }
    //endregion
}
