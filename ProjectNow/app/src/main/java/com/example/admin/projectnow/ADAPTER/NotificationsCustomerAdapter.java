package com.example.admin.projectnow.ADAPTER;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.projectnow.FRAGMENT.FoodInfoFragment;
import com.example.admin.projectnow.INTERFACE.ItemClickListener;
import com.example.admin.projectnow.MODEL.bill;
import com.example.admin.projectnow.R;
import com.example.admin.projectnow.UTILITIES.function;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsCustomerAdapter extends RecyclerView.Adapter<NotificationsCustomerAdapter.NotificationsCustomerViewholder> {
    private List<bill> billList;
    private Fragment fragment;

    //region init
    public NotificationsCustomerAdapter(List<bill> list, Fragment fragment)
    {
        this.billList = list;
        this.fragment = fragment;
    }
    //endregion

    @NonNull
    @Override
    public NotificationsCustomerViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View  view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notification_customer, viewGroup, false);
        return new NotificationsCustomerViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsCustomerViewholder notificationsCustomerViewholder, int i) {
        final bill bill = this.billList.get(i);
        notificationsCustomerViewholder.tvCheckIn.setText("Thời Gian Đặt Hàng: " + bill.getCheckIn().toString());
        notificationsCustomerViewholder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position, boolean isLongClick) {
                if(isLongClick){
                    Toast.makeText(fragment.getContext(), "Lộc Óc Chó Ăn Cức", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    function.Instance().initFragment(fragment.getActivity().getSupportFragmentManager(), FoodInfoFragment.Instance(bill.Id(), 1));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.billList.size();
    }

    //region Class View Holder
    public static class NotificationsCustomerViewholder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener{
        @BindView(R.id.noti_customer_check_in)TextView tvCheckIn;
        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }
        public NotificationsCustomerViewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
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
    //endregion
}
