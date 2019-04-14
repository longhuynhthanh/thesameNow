package com.example.admin.projectnow.FRAGMENT;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.projectnow.DAO.locationDAO;
import com.example.admin.projectnow.DAO.storeDAO;
import com.example.admin.projectnow.MODEL.account;
import com.example.admin.projectnow.MODEL.location;
import com.example.admin.projectnow.MODEL.store;
import com.example.admin.projectnow.R;
import com.example.admin.projectnow.UTILITIES.function;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyStoreFragment extends Fragment {
    private static MyStoreFragment instance;
    private account account;
    private store myStore;
    private location myLocation;

    //region BindView
    @BindView(R.id.input_nameStore) EditText etNameStore;
    @BindView(R.id.input_nameLocation) EditText etNameLocation;
    @BindView(R.id.input_phone) EditText etPhone;
    @BindView(R.id.input_info) EditText etInfo;
    @BindView(R.id.input_longitude) EditText etLongitude;
    @BindView(R.id.input_latitude) EditText etLatitude;
    @BindView(R.id.btn_fix_info) Button btnFixInfo;
    @BindView(R.id.btn_update_info) Button btnUpdateInfo;
    @BindView(R.id.btn_show_location) Button btnShowLocation;
    @BindView(R.id.btn_food) Button btnFood;
    //endregion

    //region Instance
    public static MyStoreFragment Instance()
    {
        if(instance == null)
        {
            instance = new MyStoreFragment();
        }
        return instance;
    }
    private void Instance(MyStoreFragment instance)
    {
        MyStoreFragment.instance = instance;
    }
    //endregion

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getData();
        myStore = storeDAO.Instance(getContext()).GetStoreByUserName(account.UserName());
        myLocation = locationDAO.Instance(getContext()).getLocationByID(myStore.IdLocation());
        View view = inflater.inflate(R.layout.fragment_my_store, container, false);
        ButterKnife.bind(this, view);
        SetInterface();
        return view;
    }

    //region Method
    private void getData()
    {
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            account = (account) bundle.getSerializable("account");
        }
    }

    private void SetInterface()
    {
        etNameStore.setText(myStore.NameStore());
        etNameLocation.setText(myLocation.NameLocation());
        etPhone.setText(myStore.PhoneNumber());
        etInfo.setText(myStore.Info());
        etLongitude.setText(myLocation.Longitude() + "");
        etLatitude.setText(myLocation.Latitude()+ "");
    }
    //endregion ------------------------------------------------------

    //region Event
    @OnClick(R.id.btn_fix_info)
    public void btnFixInfoClick()
    {
        btnFixInfo.setVisibility(View.GONE);
        btnUpdateInfo.setVisibility(View.VISIBLE);
        btnShowLocation.setVisibility(View.GONE);
        btnFood.setVisibility(View.GONE);
        etNameStore.setEnabled(true);
        etNameStore.requestFocus();
        etNameLocation.setEnabled(true);
        etPhone.setEnabled(true);
        etInfo.setEnabled(true);
        etLongitude.setEnabled(true);
        etLatitude.setEnabled(true);
    }
    @OnClick(R.id.btn_update_info)
    public void btnUpdateInfoClick()
    {
        store newStore = new store(myStore.Id(), etNameStore.getText().toString(), etPhone.getText().toString(), etInfo.getText().toString(), myStore.UserName(), myStore.IdLocation());
        location newLocation = new location(myLocation.Id(), etNameLocation.getText().toString(), Double.parseDouble(etLongitude.getText().toString()), Double.parseDouble(etLatitude.getText().toString()));
        locationDAO.Instance(getContext()).UpdateLocation(newLocation);
        storeDAO.Instance(getContext()).UpdateStore(newStore);
        btnFixInfo.setVisibility(View.VISIBLE);
        btnUpdateInfo.setVisibility(View.GONE);
        btnShowLocation.setVisibility(View.VISIBLE);
        btnFood.setVisibility(View.VISIBLE);
        etNameStore.setEnabled(false);
        etNameLocation.setEnabled(false);
        etPhone.setEnabled(false);
        etInfo.setEnabled(false);
        etLongitude.setEnabled(false);
        etLatitude.setEnabled(false);
        Toast.makeText(getContext(), "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.btn_show_location)
    public void btnShowLocationClick()
    {
        Bundle bundle = new Bundle();
        bundle.putString("title", myStore.NameStore());
        bundle.putSerializable("location", myLocation);
        MyLocationFragment.Instance().setArguments(bundle);
        function.Instance().initFragment(getActivity().getSupportFragmentManager(), MyLocationFragment.Instance());

    }
    @OnClick(R.id.btn_food)
    public void btnFoodClick()
    {
        function.Instance().SetData("store", myStore, FoodFragment.Instance());
        function.Instance().initFragment(getActivity().getSupportFragmentManager(), FoodFragment.Instance());
    }
    //endregion ------------------------------------------------------
}
