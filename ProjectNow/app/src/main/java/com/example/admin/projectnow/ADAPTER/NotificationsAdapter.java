package com.example.admin.projectnow.ADAPTER;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.projectnow.FRAGMENT.FoodInfoFragment;
import com.example.admin.projectnow.INTERFACE.ItemClickListener;
import com.example.admin.projectnow.MODEL.notification;
import com.example.admin.projectnow.R;
import com.example.admin.projectnow.UTILITIES.function;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder> {
    private List<notification> notificationList;
    private Fragment fragment;
    private int type;

    public NotificationsAdapter(List<notification> list, Fragment fragment, int type)
    {
        this.notificationList = list;
        this.fragment = fragment;
        this.type = type;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notification_store, viewGroup, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder notificationViewHolder, int i) {
        final notification notification = this.notificationList.get(i);
        notificationViewHolder.tvName.setText("Tên Khách Hàng: " + notification.Name());
        notificationViewHolder.tvPhone.setText("SĐT: " + notification.Phone());
        notificationViewHolder.tvAddress.setText("Địa Chỉ: " + notification.Address());
        notificationViewHolder.tvCheckIn.setText("Thời Gian Đặt Hàng: " + notification.CheckIn().toString());
        notificationViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position, boolean isLongClick) {
                if(isLongClick){}
                else
                {
                    function.Instance().initFragment(fragment.getActivity().getSupportFragmentManager(), FoodInfoFragment.Instance(notification.IdBill(), 0));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.notificationList.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.noti_customer_name)TextView tvName;
        @BindView(R.id.noti_customer_phone)TextView tvPhone;
        @BindView(R.id.noti_customer_address)TextView tvAddress;
        @BindView(R.id.noti_check_in)TextView tvCheckIn;
        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.OnClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            this.itemClickListener.OnClick(v, getAdapterPosition(), true);
            return true;
        }
    }
}
