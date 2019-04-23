package com.example.admin.projectnow.ADAPTER;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.projectnow.FRAGMENT.MainStoreFragment;
import com.example.admin.projectnow.INTERFACE.ItemClickListener;
import com.example.admin.projectnow.MODEL.account;
import com.example.admin.projectnow.MODEL.storeLocation;
import com.example.admin.projectnow.R;
import com.example.admin.projectnow.UTILITIES.function;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.StoresViewHolder> {
    private List<storeLocation> list;
    private Fragment fragment;
    private int status;
    private account account;

    //region init
    public StoresAdapter(List<storeLocation> list, Fragment fragment, int status, account account)
    {
        this.list = list;
        this.fragment = fragment;
        this.status = status;
        this.account = account;
    }
    //endregion

    @NonNull
    @Override
    public StoresViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_store, viewGroup, false);
        return new StoresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoresViewHolder storesViewHolder, int i) {
        final storeLocation storeLocation = this.list.get(i);
        storesViewHolder.tvNameStore.setText(storeLocation.NameStore());
        storesViewHolder.tvLocationStore.setText(storeLocation.NameLocation());
        if(status == 1)
        {
            storesViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void OnClick(View view, int position, boolean isLongClick) {
                    if(isLongClick){
                        Toast.makeText(fragment.getContext(), "Lộc Óc Chó", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        function.Instance().initFragment(fragment.getActivity().getSupportFragmentManager(), MainStoreFragment.Instance(storeLocation.IdStore(), storeLocation.NameLocation(), account));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    //region Class View Holder
    public static class StoresViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        @BindView(R.id.tv_nameStore) TextView tvNameStore;
        @BindView(R.id.tv_locationStore) TextView tvLocationStore;
        private ItemClickListener itemClickListener;
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }
        public StoresViewHolder(@NonNull View itemView) {
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
