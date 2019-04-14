package com.example.admin.projectnow.ADAPTER;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.admin.projectnow.INTERFACE.ItemClickListener;
import com.example.admin.projectnow.MODEL.account;
import com.example.admin.projectnow.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder> {
    private List<account> accountList;
    private Fragment fragment;
    private int type;

    public AdminAdapter(List<account> list, Fragment fragment, int type)
    {
        this.accountList = list;
        this.fragment = fragment;
        this.type = type;
    }

    public void AddItem(account a)
    {
        this.accountList.add(a);
        notifyItemInserted(this.accountList.size() - 1);
    }
    public void UpdateItem(account a)
    {
        int index = 0;
        for(account account : accountList)
        {
            if(account.UserName().equals(a.UserName()))
            {
                accountList.set(index, a);
                notifyItemChanged(index);
                break;
            }
            index++;
        }
    }
    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_account, viewGroup, false);
        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder adminViewHolder, int i) {
        final account account = this.accountList.get(i);
        if(type == 2)
        {
            adminViewHolder.image.setImageResource(R.drawable.ic_girl);
        }
        adminViewHolder.tvUserName.setText("User Name: " + account.UserName());
        adminViewHolder.tvDisplayName.setText("Display Name: " + account.DisplayName());
        adminViewHolder.tvPassword.setText("Password: " + account.Password());
        adminViewHolder.tvPhone.setText("Phone: " + account.Phone());
        adminViewHolder.tvAddress.setText("Address: " + account.Address());
        adminViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position, boolean isLongClick) {
                if(isLongClick){

                }
                else
                {

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.accountList.size();
    }

    public static class AdminViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        @BindView(R.id.image) ImageView image;
        @BindView(R.id.tv_userName) TextView tvUserName;
        @BindView(R.id.tv_displayName) TextView tvDisplayName;
        @BindView(R.id.tv_passWord) TextView tvPassword;
        @BindView(R.id.tv_phone) TextView tvPhone;
        @BindView(R.id.tv_address) TextView tvAddress;
        @BindView(R.id.image_delete)ImageView delete;
        private ItemClickListener itemClickListener;
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }
        public AdminViewHolder(@NonNull View itemView) {
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
