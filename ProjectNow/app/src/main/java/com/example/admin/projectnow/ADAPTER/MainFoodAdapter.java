package com.example.admin.projectnow.ADAPTER;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.projectnow.INTERFACE.ShoppingCart;
import com.example.admin.projectnow.MODEL.food;
import com.example.admin.projectnow.MODEL.account;
import com.example.admin.projectnow.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFoodAdapter extends RecyclerView.Adapter<MainFoodAdapter.MainFoodViewHolder> {
    private List<food> foodList;
    private Fragment fragment;
    private account account;
    private ShoppingCart shoppingCart;
    public MainFoodAdapter(List<food> list, Fragment fragment, ShoppingCart shoppingCart, account account)
    {
        this.foodList = list;
        this.fragment = fragment;
        this.shoppingCart = shoppingCart;
        this.account = account;
    }
    @NonNull
    @Override
    public MainFoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_food, viewGroup, false);
        return new MainFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainFoodViewHolder mainFoodViewHolder, int i) {
        final food food = this.foodList.get(i);
        final int a[] = {0};
        mainFoodViewHolder.tvNameFood.setText("Tên Món Ăn: " + food.NameFood());
        mainFoodViewHolder.tvPrice.setText("Giá: " + food.Price() + " VNĐ");
        if(food.Status() == 0)
        {
            mainFoodViewHolder.tvStatus.setText("Tình Trạng: Đã Hết Hàng" );
        }
        else if(food.Status() == 1)
        {
            mainFoodViewHolder.tvStatus.setVisibility(View.GONE);
        }
        if(food.IdCategory() == 1)
        {
            mainFoodViewHolder.image.setImageResource(R.drawable.ic_coffee_cup);
        }
        else if(food.IdCategory() == 2)
        {
            mainFoodViewHolder.image.setImageResource(R.drawable.ic_iced_tea);
        }
        else if(food.IdCategory() == 3)
        {
            mainFoodViewHolder.image.setImageResource(R.drawable.ic_cake);
        }
        else if(food.IdCategory() == 4)
        {
            mainFoodViewHolder.image.setImageResource(R.drawable.ic_sandwich);
        }
        else if(food.IdCategory() == 5)
        {
            mainFoodViewHolder.image.setImageResource(R.drawable.ic_avocado);
        }
        else if(food.IdCategory() == 6)
        {
            mainFoodViewHolder.image.setImageResource(R.drawable.ic_energy_bar);
        }
        else if(food.IdCategory() == 7)
        {
            mainFoodViewHolder.image.setImageResource(R.drawable.ic_juice);
        }
        else if(food.IdCategory() == 8)
        {
            mainFoodViewHolder.image.setImageResource(R.drawable.ic_pint);
        }
        else if(food.IdCategory() == 9)
        {
            mainFoodViewHolder.image.setImageResource(R.drawable.ic_fried_rice);
        }
        else if(food.IdCategory() == 10)
        {
            mainFoodViewHolder.image.setImageResource(R.drawable.ic_noodle);
        }
        mainFoodViewHolder.imgMinus.setVisibility(View.INVISIBLE);
        mainFoodViewHolder.tvCount.setVisibility(View.INVISIBLE);
        if(food.Status() == 1)
        {
            mainFoodViewHolder.imgPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(account == null)
                    {
                        Toast.makeText(fragment.getContext(), "Bạn Chưa Đăng Nhập Tài Khoản, Vui Lòng Đăng Nhập Để Có Thể Đặt Món Ăn", Toast.LENGTH_SHORT).show();
                    }
                    else if(account.Type() == 0 || account.Type() == 1)
                    {
                        Toast.makeText(fragment.getContext(), "Bạn Vui Lòng Tạo Tài Khoản Dành Cho Khách Hàng Để Có Thể Đặt Món Ăn", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        a[0]++;
                        if(a[0] == 1)
                        {
                            mainFoodViewHolder.imgMinus.setVisibility(View.VISIBLE);
                            mainFoodViewHolder.tvCount.setVisibility(View.VISIBLE);
                        }
                        mainFoodViewHolder.tvCount.setText(a[0] + "");
                        shoppingCart.TransactionData(food ,1, food.Price(), true);
                    }

                }
            });
            mainFoodViewHolder.imgMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a[0]--;
                    mainFoodViewHolder.tvCount.setText(a[0] + "");
                    if(a[0] == 0)
                    {
                        mainFoodViewHolder.imgMinus.setVisibility(View.INVISIBLE);
                        mainFoodViewHolder.tvCount.setVisibility(View.INVISIBLE);
                        shoppingCart.TransactionData(food, -1, -food.Price(), false);
                    }
                    else
                    {
                        shoppingCart.TransactionData(food, -1, -food.Price(), true);
                    }

                }
            });
        }
        else
        {
            mainFoodViewHolder.imgPlus.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return this.foodList.size();
    }

    public static class MainFoodViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.main_food_image) ImageView image;
        @BindView(R.id.main_food_nameFood) TextView tvNameFood;
        @BindView(R.id.main_food_price) TextView tvPrice;
        @BindView(R.id.main_food_status) TextView tvStatus;
        @BindView(R.id.main_food_minus) ImageView imgMinus;
        @BindView(R.id.main_food_count) TextView tvCount;
        @BindView(R.id.main_food_plus) ImageView imgPlus;

        public MainFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
