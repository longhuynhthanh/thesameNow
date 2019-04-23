package com.example.admin.projectnow.ADAPTER;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.projectnow.MODEL.food;
import com.example.admin.projectnow.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartViewHolder> {
    private List<food> foodList;
    private List<Integer> countList;
    private Fragment fragment;

    //region init
    public ShoppingCartAdapter(List<food> foodList, List<Integer> countList, Fragment fragment)
    {
        this.foodList = foodList;
        this.countList = countList;
        this.fragment = fragment;
    }
    //endregion

    @NonNull
    @Override
    public ShoppingCartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_shopping_cart, viewGroup, false);
        return new ShoppingCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartViewHolder shoppingCartViewHolder, int i) {
        final food food = this.foodList.get(i);
        final int count = this.countList.get(i);
        if(food.IdCategory() == 1)
        {
            shoppingCartViewHolder.image.setImageResource(R.drawable.ic_coffee_cup);
        }
        else if(food.IdCategory() == 2)
        {
            shoppingCartViewHolder.image.setImageResource(R.drawable.ic_iced_tea);
        }
        else if(food.IdCategory() == 3)
        {
            shoppingCartViewHolder.image.setImageResource(R.drawable.ic_cake);
        }
        else if(food.IdCategory() == 4)
        {
            shoppingCartViewHolder.image.setImageResource(R.drawable.ic_sandwich);
        }
        else if(food.IdCategory() == 5)
        {
            shoppingCartViewHolder.image.setImageResource(R.drawable.ic_avocado);
        }
        else if(food.IdCategory() == 6)
        {
            shoppingCartViewHolder.image.setImageResource(R.drawable.ic_energy_bar);
        }
        else if(food.IdCategory() == 7)
        {
            shoppingCartViewHolder.image.setImageResource(R.drawable.ic_juice);
        }
        else if(food.IdCategory() == 8)
        {
            shoppingCartViewHolder.image.setImageResource(R.drawable.ic_pint);
        }
        else if(food.IdCategory() == 9)
        {
            shoppingCartViewHolder.image.setImageResource(R.drawable.ic_fried_rice);
        }
        else if(food.IdCategory() == 10)
        {
            shoppingCartViewHolder.image.setImageResource(R.drawable.ic_noodle);
        }
        else
        {
            shoppingCartViewHolder.image.setImageResource(R.drawable.ic_breakfast);
        }
        shoppingCartViewHolder.tvNameFood.setText("Tên Món Ăn: " + food.NameFood());
        shoppingCartViewHolder.tvPrice.setText("Giá: " + food.Price() + " VNĐ");
        shoppingCartViewHolder.tvCount.setText("Số Lượng: " + count);
    }

    @Override
    public int getItemCount() {
        return this.foodList.size();
    }

    //region Class View Holder
    public static class ShoppingCartViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shopping_cart_image) ImageView image;
        @BindView(R.id.shopping_cart_nameFood) TextView tvNameFood;
        @BindView(R.id.shopping_cart_price) TextView tvPrice;
        @BindView(R.id.shopping_cart_count) TextView tvCount;
        public ShoppingCartViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    //endregion
}
