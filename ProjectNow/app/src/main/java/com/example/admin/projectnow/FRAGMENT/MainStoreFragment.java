package com.example.admin.projectnow.FRAGMENT;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.projectnow.ADAPTER.MainFoodAdapter;
import com.example.admin.projectnow.DAO.categoryDAO;
import com.example.admin.projectnow.DAO.foodDAO;
import com.example.admin.projectnow.DAO.locationDAO;
import com.example.admin.projectnow.DAO.storeDAO;
import com.example.admin.projectnow.INTERFACE.ShoppingCart;
import com.example.admin.projectnow.MODEL.account;
import com.example.admin.projectnow.MODEL.category;
import com.example.admin.projectnow.MODEL.food;
import com.example.admin.projectnow.MODEL.location;
import com.example.admin.projectnow.MODEL.store;
import com.example.admin.projectnow.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainStoreFragment extends Fragment implements OnMapReadyCallback, ShoppingCart {
    private static MainStoreFragment instance;
    private int idStore;
    private String nameLocation;
    private store myStore;
    private location myLocation;
    private List<MainFoodAdapter> mainFoodAdapterList;
    private List<RecyclerView> recyclerViewList;
    private List<category> categoryList;
    private List<food> foodList;
    private List<Integer> countList;
    private account account;
    private double totalPrice = 0;
    private int totalCount = 0;
    //region Location
    private GoogleMap googleMap;
    private LocationManager service;
    //endregion -----------------
    //region BindView
    @BindView(R.id.main_nameStore)TextView tvNameStore;
    @BindView(R.id.main_nameLocation)TextView tvNameLocation;
    @BindView(R.id.main_phone)TextView tvPhone;
    @BindView(R.id.main_info)TextView tvInfo;
    @BindView(R.id.shopping_cart) Button shoppingCart;
    @BindView(R.id.layout_placeHolder) LinearLayout placeHolder;
    //endregion

    //region Instance
    public static MainStoreFragment Instance(int idStore, String nameLocation, account account)
    {
        instance = new MainStoreFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("idStore", idStore);
        bundle.putString("nameLocation", nameLocation);
        bundle.putSerializable("account", account);
        instance.setArguments(bundle);
        return instance;
    }
    private void Instance(MainStoreFragment instance)
    {
        MainStoreFragment.instance = instance;
    }
    //endregion

    //region Override Shopping Cart
    @Override
    public void TransactionData(food food, int count, double Price, boolean check) {
        totalPrice += Price;
        totalCount += count;
        shoppingCart.setText("SL: " + totalCount + " -- Tổng Tiền: " + totalPrice + " VNĐ");
        if(totalCount ==  1)
        {
            shoppingCart.setVisibility(View.VISIBLE);
        }
        else if(totalCount == 0)
        {
            shoppingCart.setVisibility(View.GONE);
        }
        if(count == 1)
        {
            if(foodList.size() == 0 && countList.size() == 0)
            {
                foodList.add(food);
                countList.add(count);
                return;
            }
            for(int i = 0; i < foodList.size(); i++)
            {
                if(foodList.get(i).Id() == food.Id())
                {
                    countList.set(i, countList.get(i) + count);
                    return;
                }
            }
            foodList.add(food);
            countList.add(count);
            return;
        }
        else if(count == -1)
        {
            if(check)
            {
                for(int i = 0; i < foodList.size(); i++)
                {
                    if(food.Id() == foodList.get(i).Id())
                    {
                        countList.set(i, countList.get(i) + count);
                        return;
                    }
                }
            }
            else
            {
                for(int i = 0; i < foodList.size(); i++)
                {
                    if(food.Id() == foodList.get(i).Id())
                    {
                        foodList.remove(i);
                        countList.remove(i);
                        return;
                    }
                }
            }
        }
    }
    //endregion --------------------------------------

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainFoodAdapterList = new ArrayList<>();
        recyclerViewList = new ArrayList<>();
        categoryList = new ArrayList<>();
        foodList = new ArrayList<>();
        countList = new ArrayList<>();
        GetData();
        View view = inflater.inflate(R.layout.fragment_main_store, null, false);
        ButterKnife.bind(this, view);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
        SetInterFace();
        return view;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        UiSettings uiSettings = this.googleMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//bang M
            if (ActivityCompat.checkSelfPermission(getContext(),android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(),android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                XuLyQuyen();
            } else {
                ActivityCompat.requestPermissions(this.getActivity(), new
                        String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        } else {
            XuLyQuyen();
        }
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }


    //region Method
    private void SetInterFace()
    {
        tvNameStore.setText(myStore.NameStore());
        tvNameLocation.setText(nameLocation);
        tvPhone.setText(myStore.PhoneNumber());
        tvInfo.setText(myStore.Info());
        GetCategoryList();
        for(category category : this.categoryList)
        {
            List<food> foods = foodDAO.Instance(getContext()).GetFoodByStoreID(myStore.Id(), category.Id());
            AddView(foods, category.NameCategory());
        }
    }
    private void GetData()
    {
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            idStore = bundle.getInt("idStore");
            nameLocation = bundle.getString("nameLocation");
            account = (account)bundle.getSerializable("account");
            myStore = storeDAO.Instance(getContext()).GetStoreByID(idStore);
            myLocation = locationDAO.Instance(getContext()).getLocationByID(myStore.IdLocation());
        }
    }
    public void XuLyQuyen()
    {
        this.googleMap.setMyLocationEnabled(true);
        service = (LocationManager)getActivity().getSystemService(getContext().LOCATION_SERVICE);
        Location location= service.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null)
        {
            LatLng latLng = new LatLng(myLocation.Longitude(), myLocation.Latitude());
            CreateMarker(latLng);
            this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }
    }
    private void CreateMarker(LatLng latLng)
    {
        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(myStore.NameStore())
                .snippet(myLocation.NameLocation())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
        );
    }
    private void GetCategoryList()
    {
        categoryList = categoryDAO.Instance(getContext()).GetCategoryByStoreID(myStore.Id());
    }
    private void AddView(List<food> foods, String nameCategory)
    {
        View view = getActivity().getLayoutInflater().inflate(R.layout.item_recyclerview_food, null);
        TextView tv_title = (TextView)view.findViewById(R.id.header_id);
        tv_title.setText(nameCategory);
        RecyclerView rvFoods = (RecyclerView)view.findViewById(R.id.rv_foods);
        rvFoods.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvFoods.setHasFixedSize(true);
        rvFoods.setNestedScrollingEnabled(false);
        MainFoodAdapter mainFoodAdapter = new MainFoodAdapter(foods, this, this, account);
        mainFoodAdapterList.add(mainFoodAdapter);
        rvFoods.setAdapter(mainFoodAdapter);
        recyclerViewList.add(rvFoods);
        placeHolder.addView(view);
    }
    //endregion -----------------------------------------------------

    //region Event
    @OnClick(R.id.shopping_cart)
    public void shopping_cart_click()
    {
        DialogShoppingCart dialogShoppingCart = new DialogShoppingCart();
        Bundle bundle = new Bundle();
        bundle.putSerializable("FoodList", (Serializable) foodList);
        bundle.putSerializable("CountList", (Serializable) countList);
        bundle.putSerializable("account", account);
        bundle.putDouble("TotalPrice", totalPrice);
        bundle.putInt("idStore", myStore.Id());
        dialogShoppingCart.setArguments(bundle);
        dialogShoppingCart.show(getFragmentManager(), "DialogShoppingCart");
    }
    //endregion -----------------------------------------------------
}
