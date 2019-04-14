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

import com.example.admin.projectnow.ADAPTER.NotificationsAdapter;
import com.example.admin.projectnow.ADAPTER.NotificationsCustomerAdapter;
import com.example.admin.projectnow.DAO.billDAO;
import com.example.admin.projectnow.DAO.notificationDAO;
import com.example.admin.projectnow.DAO.storeDAO;
import com.example.admin.projectnow.MODEL.account;
import com.example.admin.projectnow.MODEL.bill;
import com.example.admin.projectnow.MODEL.notification;
import com.example.admin.projectnow.MODEL.store;
import com.example.admin.projectnow.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsFragment extends Fragment {
    private static NotificationsFragment instance;
    private account account;
    private List<notification> notificationList;
    private NotificationsAdapter notificationsAdapter;
    private List<bill> billList;
    private NotificationsCustomerAdapter notificationsCustomerAdapter;

    //region BindView
    @BindView(R.id.rv_notifications)RecyclerView rvNotifications;
    @BindView(R.id.notification)TextView tvNoti;
    //endregion

    //region Instance
    public static NotificationsFragment Instance()
    {
        if(instance == null)
        {
            instance = new NotificationsFragment();
        }
        return instance;
    }
    private void Instance(NotificationsFragment instance)
    {
        NotificationsFragment.instance = instance;
    }
    //endregion

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        GetData();
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(this, view);
        ShowInterface();
        return  view;
    }

    //region Method
    private void GetData()
    {
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            account = (account) bundle.getSerializable("account");
        }
    }
    private void ShowInterface()
    {
        if(account == null)
        {
            rvNotifications.setVisibility(View.GONE);
        }
        else if(account.Type() == 1)
        {
            store store = storeDAO.Instance(getContext()).GetStoreByUserName(account.UserName());
            notificationList = notificationDAO.Instance(getContext()).GetNotificationByIDStore(store.Id());
            tvNoti.setVisibility(View.GONE);
            notificationsAdapter = new NotificationsAdapter(notificationList, this, account.Type());
            rvNotifications.setLayoutManager(new LinearLayoutManager(this.getContext()));
            rvNotifications.setHasFixedSize(true);
            rvNotifications.setAdapter(notificationsAdapter);
        }
        else if(account.Type() == 2)
        {
            billList = billDAO.Instance(getContext()).GetBillByUserName(account.UserName());
            tvNoti.setVisibility(View.GONE);
            notificationsCustomerAdapter = new NotificationsCustomerAdapter(billList, this);
            rvNotifications.setLayoutManager(new LinearLayoutManager(this.getContext()));
            rvNotifications.setHasFixedSize(true);
            rvNotifications.setAdapter(notificationsCustomerAdapter);
        }
        else if(account.Type() == 0)
        {
            tvNoti.setText("Bạn Vui Lòng Đăng Nhập Bằng Tài Khoản Khác Không Phải Là Admin Để Có Thể Xem Thông Báo");
        }
    }
    //endregion

}
