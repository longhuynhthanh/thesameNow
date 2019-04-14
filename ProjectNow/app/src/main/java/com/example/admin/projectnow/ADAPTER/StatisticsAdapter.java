package com.example.admin.projectnow.ADAPTER;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.projectnow.MODEL.statistics;
import com.example.admin.projectnow.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.StatisticsViewHolder>{
    private List<statistics> statisticsList;
    private Fragment fragment;
    public StatisticsAdapter(List<statistics> list, Fragment fragment)
    {
        this.statisticsList = list;
        this.fragment = fragment;
    }
    @NonNull
    @Override
    public StatisticsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_statistics, viewGroup, false);
        return new StatisticsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticsViewHolder statisticsViewHolder, int i) {
        final statistics statistics = this.statisticsList.get(i);
        statisticsViewHolder.tvCheckIn.setText("Thời Gian Đặt Hàng: " + statistics.CheckIn().toString());
        statisticsViewHolder.tvCheckOut.setText("Thanh Toán: " + statistics.CheckOut().toString());
        statisticsViewHolder.tvTotalPrice.setText("Tổng Hóa Đơn: " + statistics.TotalPrice() + " VNĐ");
    }

    @Override
    public int getItemCount() {
        return this.statisticsList.size();
    }

    //region Method
    public void UpdateAllItem(List<statistics> list)
    {
        this.statisticsList.clear();
        this.statisticsList.addAll(list);
        notifyDataSetChanged();
    }
    //endregion

    public static class StatisticsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.statistics_checkIn)TextView tvCheckIn;
        @BindView(R.id.statistics_checkout)TextView tvCheckOut;
        @BindView(R.id.statistics_totalPrice)TextView tvTotalPrice;
        public StatisticsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
