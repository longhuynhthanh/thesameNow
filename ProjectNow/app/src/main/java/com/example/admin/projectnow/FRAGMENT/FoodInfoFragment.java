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
import android.widget.TextView;

import com.example.admin.projectnow.ADAPTER.FoodInfoAdapter;
import com.example.admin.projectnow.DAO.billDAO;
import com.example.admin.projectnow.DAO.foodInfoDAO;
import com.example.admin.projectnow.MODEL.foodInfo;
import com.example.admin.projectnow.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FoodInfoFragment extends Fragment {
    private static FoodInfoFragment instance;
    private List<foodInfo> foodInfoList;
    private FoodInfoAdapter foodInfoAdapter;
    private int idBill;
    private int status;
    //region BindView
    @BindView(R.id.rv_foods_info)RecyclerView rvFoods_info;
    @BindView(R.id.tv_total_price)TextView totalPrice;
    @BindView(R.id.action) TextView acction;
    //endregion

    //region Instance
    public static FoodInfoFragment Instance(int idBill, int status)
    {
        instance = new FoodInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("idBill", idBill);
        bundle.putInt("status", status);
        instance.setArguments(bundle);
        return instance;
    }
    private void Instance(FoodInfoFragment instance)
    {
        FoodInfoFragment.instance = instance;
    }
    //endregion

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        GetData();
        View view = inflater.inflate(R.layout.fragment_food_info, container, false);
        ButterKnife.bind(this, view);
        SetInterface();

        return view;
    }

    //region Method
    private void GetData()
    {
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            idBill = bundle.getInt("idBill");
            status = bundle.getInt("status");
        }
    }
    private void SetInterface()
    {
        if(status == 1)
        {
            acction.setVisibility(View.GONE);
        }
        foodInfoList = foodInfoDAO.Instance(getContext()).GetFoodInfoByBillID(idBill);
        rvFoods_info.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvFoods_info.setHasFixedSize(true);
        foodInfoAdapter = new FoodInfoAdapter(foodInfoList, this);
        rvFoods_info.setAdapter(foodInfoAdapter);
        double total = 0;
        for(foodInfo f : this.foodInfoList)
        {
            total += f.TotalPrice();
        }
        totalPrice.setText("Tổng Tiền: " + total + " VNĐ");
    }
    //endregion

    //region Event
    @OnClick(R.id.action)
    public void actionClick()
    {
        billDAO.Instance(getContext()).UpdateBill(idBill);
        getActivity().onBackPressed();
    }
    //endregion
}
