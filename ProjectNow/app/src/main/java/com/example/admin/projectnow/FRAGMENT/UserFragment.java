package com.example.admin.projectnow.FRAGMENT;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.projectnow.LoginActivity;
import com.example.admin.projectnow.MODEL.account;
import com.example.admin.projectnow.R;
import com.example.admin.projectnow.UTILITIES.function;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserFragment extends Fragment {
    private static UserFragment instance;
    private account account;

    //region BindView
    @BindView(R.id.myLogin) LinearLayout llMyLogin;
    @BindView(R.id.noLogin) LinearLayout llNoLogin;
    @BindView(R.id.MyStore) CardView cvMyStore;
    @BindView(R.id.stores) CardView cvStores;
    @BindView(R.id.MyAdmin) CardView cvAdmin;
    @BindView(R.id.MyCustomer) CardView cvCustomer;
    @BindView(R.id.MyPay) CardView cvMyPay;
    @BindView(R.id.MyCategory) CardView cvCategory;
    @BindView(R.id.feedback) CardView cvFeedBack;
    @BindView(R.id.guide) CardView cvGuide;
    @BindView(R.id.Private) CardView cvPrivate;
    @BindView(R.id.myDispute) CardView cvDispute;
    @BindView(R.id.myLogout) CardView cvMyLogout;
    @BindView(R.id.Invite) CardView cvInvite;
    @BindView(R.id.Statistics) CardView cvStatistics;
    @BindView(R.id.textview_userName) TextView tvUserName;
    //endregion

    //region Instance
    public static UserFragment Instance()
    {
        if(instance == null)
        {
            instance = new UserFragment();
        }
        return instance;
    }
    private void Instance(UserFragment instance)
    {
        UserFragment.instance = instance;
    }
    //endregion


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        account = new account();
        getData();
        showInterface();
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

    private void showInterface()
    {
        if(account == null)
        {
            llMyLogin.setVisibility(View.GONE);
            cvMyLogout.setVisibility(View.GONE);
            cvAdmin.setVisibility(View.GONE);
            cvCustomer.setVisibility(View.GONE);
            cvMyStore.setVisibility(View.GONE);
            cvStatistics.setVisibility(View.GONE);
            cvStores.setVisibility(View.GONE);
            cvCategory.setVisibility(View.GONE);
        }
        else
        {
            llNoLogin.setVisibility(View.GONE);
            tvUserName.setText(account.DisplayName());
            if(account.Type() == 2)
            {
                cvMyStore.setVisibility(View.GONE);
                cvStatistics.setVisibility(View.GONE);
                cvAdmin.setVisibility(View.GONE);
                cvCustomer.setVisibility(View.GONE);
                cvStores.setVisibility(View.GONE);
                cvCategory.setVisibility(View.GONE);
            }
            else if (account.Type() == 1)
            {
                cvAdmin.setVisibility(View.GONE);
                cvCustomer.setVisibility(View.GONE);
                cvStores.setVisibility(View.GONE);
                cvCategory.setVisibility(View.GONE);
            }
            else if (account.Type() == 0)
            {
                cvMyStore.setVisibility(View.GONE);
                cvStatistics.setVisibility(View.GONE);
            }
        }
    }

    //endregion --------------------------------------------

    //region Event
    @OnClick(R.id.noLogin)
    public void noLoginClick()
    {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
    @OnClick(R.id.myLogin)
    public void myLoginClick()
    {
        function.Instance().SetData("account", account, MyUserFragment.Instance());
        function.Instance().initFragment(getActivity().getSupportFragmentManager(), MyUserFragment.Instance());
    }
    @OnClick(R.id.MyStore)
    public void cvMyStoreClick()
    {
        function.Instance().SetData("account", account, MyStoreFragment.Instance());
        function.Instance().initFragment(getActivity().getSupportFragmentManager(), MyStoreFragment.Instance());

    }
    @OnClick(R.id.Statistics)
    public void cvStatisticsClick()
    {
        function.Instance().SetData("account", account, StatisticsFragment.Instance());
        function.Instance().initFragment(getActivity().getSupportFragmentManager(), StatisticsFragment.Instance());
    }
    @OnClick(R.id.stores)
    public void cvStoresClick()
    {
        function.Instance().initFragment(getActivity().getSupportFragmentManager(), StoresFragment.Instance());
    }
    @OnClick(R.id.MyAdmin)
    public void cvAdminClick()
    {
        function.Instance().initFragment(getActivity().getSupportFragmentManager(), StoreAccountFragment.Instance());

    }
    @OnClick(R.id.MyCustomer)
    public void cvCustomerClick()
    {
        function.Instance().initFragment(getActivity().getSupportFragmentManager(), MyCustomerFragment.Instance());
    }
    @OnClick(R.id.MyPay)
    public void cvMyPayClick()
    {
    }
    @OnClick(R.id.MyCategory)
    public void cvCategoryClick()
    {
        function.Instance().initFragment(getActivity().getSupportFragmentManager(), CategoriesFragment.Instance());
    }
    @OnClick(R.id.Invite)
    public void cvInviteClick()
    {
    }
    @OnClick(R.id.feedback)
    public void cvFeedBackClick()
    {
    }
    @OnClick(R.id.guide)
    public void cvGuideClick()
    {
    }
    @OnClick(R.id.Private)
    public void cvPrivateClick()
    {
    }
    @OnClick(R.id.myDispute)
    public void cvDisputeClick()
    {
    }
    @OnClick(R.id.myLogout)
    public void cvLogoutClick() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    //endregion
}
