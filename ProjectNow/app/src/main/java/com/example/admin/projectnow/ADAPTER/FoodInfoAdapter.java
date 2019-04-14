package com.example.admin.projectnow.ADAPTER;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.projectnow.MODEL.foodInfo;
import com.example.admin.projectnow.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodInfoAdapter extends RecyclerView.Adapter<FoodInfoAdapter.FoodInfoViewHolder>{
    private List<foodInfo> foodInfoList;
    private Fragment fragment;

    public FoodInfoAdapter(List<foodInfo> list, Fragment fragment)
    {
        this.foodInfoList = list;
        this.fragment = fragment;
    }
    @NonNull
    @Override
    public FoodInfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_info, viewGroup, false);
        return new FoodInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodInfoViewHolder foodInfoViewHolder, int i) {
        final foodInfo foodInfo = this.foodInfoList.get(i);
        if(foodInfo.IdCategory() == 1)
        {
            foodInfoViewHolder.image.setImageResource(R.drawable.ic_coffee_cup);
        }
        else if(foodInfo.IdCategory() == 2)
        {
            foodInfoViewHolder.image.setImageResource(R.drawable.ic_iced_tea);
        }
        else if(foodInfo.IdCategory() == 3)
        {
            foodInfoViewHolder.image.setImageResource(R.drawable.ic_cake);
        }
        else if(foodInfo.IdCategory() == 4)
        {
            foodInfoViewHolder.image.setImageResource(R.drawable.ic_sandwich);
        }
        else if(foodInfo.IdCategory() == 5)
        {
            foodInfoViewHolder.image.setImageResource(R.drawable.ic_avocado);
        }
        else if(foodInfo.IdCategory() == 6)
        {
            foodInfoViewHolder.image.setImageResource(R.drawable.ic_energy_bar);
        }
        else if(foodInfo.IdCategory() == 7)
        {
            foodInfoViewHolder.image.setImageResource(R.drawable.ic_juice);
        }
        else if(foodInfo.IdCategory() == 8)
        {
            foodInfoViewHolder.image.setImageResource(R.drawable.ic_pint);
        }
        else if(foodInfo.IdCategory() == 9)
        {
            foodInfoViewHolder.image.setImageResource(R.drawable.ic_fried_rice);
        }
        else if(foodInfo.IdCategory() == 10)
        {
            foodInfoViewHolder.image.setImageResource(R.drawable.ic_noodle);
        }
        foodInfoViewHolder.tvNameFood.setText("Tên Món Ăn: " + foodInfo.NameFood());
        foodInfoViewHolder.tvPrice.setText("Giá: " + foodInfo.Price() + " VNĐ");
        foodInfoViewHolder.tvCount.setText("Số Lượng: " + foodInfo.Count());
        foodInfoViewHolder.tvTotalPrice.setText("Tổng: " + foodInfo.TotalPrice() + " VNĐ");
    }

    @Override
    public int getItemCount() {
        return this.foodInfoList.size();
    }

    public static class FoodInfoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.info_nameFood)TextView tvNameFood;
        @BindView(R.id.info_price)TextView tvPrice;
        @BindView(R.id.info_countFood)TextView tvCount;
        @BindView(R.id.info_totalPrice)TextView tvTotalPrice;
        @BindView(R.id.image)ImageView image;
        public FoodInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
