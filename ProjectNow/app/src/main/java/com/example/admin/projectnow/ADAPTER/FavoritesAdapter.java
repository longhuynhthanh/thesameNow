package com.example.admin.projectnow.ADAPTER;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.projectnow.MODEL.favorites;
import com.example.admin.projectnow.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    private List<favorites> favoritesList;
    private Fragment fragment;

    public FavoritesAdapter(List<favorites> list, Fragment fragment)
    {
        this.favoritesList = list;
        this.fragment = fragment;
    }
    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_favorite, viewGroup, false);
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder favoritesViewHolder, int i) {
        final favorites favorites = this.favoritesList.get(i);
        if(favorites.IdCategory() == 1)
        {
            favoritesViewHolder.image.setImageResource(R.drawable.ic_coffee_cup);
        }
        else if(favorites.IdCategory() == 2)
        {
            favoritesViewHolder.image.setImageResource(R.drawable.ic_iced_tea);
        }
        else if(favorites.IdCategory() == 3)
        {
            favoritesViewHolder.image.setImageResource(R.drawable.ic_cake);
        }
        else if(favorites.IdCategory() == 4)
        {
            favoritesViewHolder.image.setImageResource(R.drawable.ic_sandwich);
        }
        else if(favorites.IdCategory() == 5)
        {
            favoritesViewHolder.image.setImageResource(R.drawable.ic_avocado);
        }
        else if(favorites.IdCategory() == 6)
        {
            favoritesViewHolder.image.setImageResource(R.drawable.ic_energy_bar);
        }
        else if(favorites.IdCategory() == 7)
        {
            favoritesViewHolder.image.setImageResource(R.drawable.ic_juice);
        }
        else if(favorites.IdCategory() == 8)
        {
            favoritesViewHolder.image.setImageResource(R.drawable.ic_pint);
        }
        else if(favorites.IdCategory() == 9)
        {
            favoritesViewHolder.image.setImageResource(R.drawable.ic_fried_rice);
        }
        else if(favorites.IdCategory() == 10)
        {
            favoritesViewHolder.image.setImageResource(R.drawable.ic_noodle);
        }
        favoritesViewHolder.tvNameFood.setText("Tên Món Ăn: " + favorites.NameFood());
        favoritesViewHolder.tvNameStore.setText("Đang Bán Tại Cửa Hàng: " + favorites.NameStore());
        favoritesViewHolder.tvCountFood.setText("Số Lượng Mua: " + favorites.Count());
        favoritesViewHolder.tvPrice.setText("Giá: " + favorites.Price() + " VNĐ");
    }

    @Override
    public int getItemCount() {
        return this.favoritesList.size();
    }

    public static class FavoritesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fa_image)ImageView image;
        @BindView(R.id.fa_nameFood)TextView tvNameFood;
        @BindView(R.id.fa_nameStore)TextView tvNameStore;
        @BindView(R.id.fa_countFood)TextView tvCountFood;
        @BindView(R.id.fa_price)TextView tvPrice;
        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
