package com.example.admin.projectnow.FRAGMENT;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.projectnow.ADAPTER.ShoppingCartAdapter;
import com.example.admin.projectnow.DAO.billDAO;
import com.example.admin.projectnow.DAO.billInfoDAO;
import com.example.admin.projectnow.MODEL.account;
import com.example.admin.projectnow.MODEL.food;
import com.example.admin.projectnow.MainActivity;
import com.example.admin.projectnow.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogShoppingCart extends DialogFragment {
    private List<food> foodList;
    private List<Integer> countList;
    private account account;
    private double totalPrice;
    private int idStore;
    private ShoppingCartAdapter shoppingCartAdapter;

    //region BindView
    @BindView(R.id.rv_foods) RecyclerView rvFoods;
    @BindView(R.id.action_pay) TextView btn_pay;
    @BindView(R.id.action_cancel) TextView btn_cancel;
    @BindView(R.id.shopping_cart_total_price) TextView tv_Total_Price;
    //endregion --------------------------------------------------------

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
        foodList = new ArrayList<>();
        countList = new ArrayList<>();
        GetData();
    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_shopping_cart, container, false);
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
            foodList = (List<food>)bundle.getSerializable("FoodList");
            countList = (List<Integer>)bundle.getSerializable("CountList");
            totalPrice = bundle.getDouble("TotalPrice");
            idStore = bundle.getInt("idStore");
            account = (account) bundle.getSerializable("account");
        }
    }
    private void SetInterface()
    {
        rvFoods.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvFoods.setHasFixedSize(true);
        shoppingCartAdapter = new ShoppingCartAdapter(foodList, countList, this);
        rvFoods.setAdapter(shoppingCartAdapter);
        tv_Total_Price.setText("Tổng Tiền: " + totalPrice + " VNĐ");
    }
    //endregion ----------------------------------------------

    //region Event
    @OnClick(R.id.action_cancel)
    public void CancelClick()
    {
        getDialog().dismiss();
    }

    @OnClick(R.id.action_pay)
    public void PayClick()
    {
        billDAO.Instance(getContext()).InsertBill(idStore, account.UserName());
        int maxBillID = billDAO.Instance(getContext()).GetMaxBillID();
        if(maxBillID != -1)
        {
            for(int i = 0; i < foodList.size(); i++)
            {
                billInfoDAO.Instance(getContext()).InsertBillInfo(maxBillID, foodList.get(i).Id(), countList.get(i));
            }
            Toast.makeText(getContext(), "Bạn Đã Thanh Toán, Vui Lòng Chờ Cuộc Gọi Xác Nhận Từ Cửa Hàng", Toast.LENGTH_SHORT).show();
        }
        getDialog().dismiss();
    }
    //endregion -----------------------------------------------
}
