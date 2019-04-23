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

import com.example.admin.projectnow.FRAGMENT.DialogFoodFragment;
import com.example.admin.projectnow.INTERFACE.ItemClickListener;
import com.example.admin.projectnow.MODEL.food;
import com.example.admin.projectnow.R;
import com.example.admin.projectnow.UTILITIES.function;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{
    private List<food> foodList;
    private Fragment fragment;

    //region init
    public FoodAdapter(List<food> list, Fragment fragment)
    {
        this.foodList = list;
        this.fragment = fragment;
    }
    //endregion

    //region Method
    public void AddItem(food f)
    {
        this.foodList.add(f);
        notifyItemInserted(this.foodList.size() - 1);
    }
    public void RemoveItem(food f)
    {
        for (int i = 0; i < this.foodList.size(); i++)
        {
            if(this.foodList.get(i).Id() == f.Id())
            {
                this.foodList.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, this.foodList.size());
                break;
            }
        }
    }
    public void UpdateItem(food f)
    {
        int index = 0;
        for(food food : foodList)
        {
            if(food.Id() == f.Id())
            {
                foodList.set(index, f);
                notifyItemChanged(index);
                break;
            }
            index++;
        }
    }
    //endregion

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food, viewGroup, false);
        return new FoodViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder foodViewHolder, int i) {
        final food newfood = this.foodList.get(i);
        foodViewHolder.tvNameFood.setText("Tên Món Ăn: " + newfood.NameFood());
        foodViewHolder.tvPrice.setText("Giá: " + newfood.Price() + " VNĐ");
        if(newfood.Status() == 0)
        {
            foodViewHolder.tvStatus.setVisibility(View.VISIBLE);
            foodViewHolder.tvStatus.setText("Tình Trạng: Đã Hết Hàng" );
        }
        else if(newfood.Status() == 1)
        {
            foodViewHolder.tvStatus.setVisibility(View.GONE);
        }
        if(newfood.IdCategory() == 1)
        {
            foodViewHolder.image.setImageResource(R.drawable.ic_coffee_cup);
        }
        else if(newfood.IdCategory() == 2)
        {
            foodViewHolder.image.setImageResource(R.drawable.ic_iced_tea);
        }
        else if(newfood.IdCategory() == 3)
        {
            foodViewHolder.image.setImageResource(R.drawable.ic_cake);
        }
        else if(newfood.IdCategory() == 4)
        {
            foodViewHolder.image.setImageResource(R.drawable.ic_sandwich);
        }
        else if(newfood.IdCategory() == 5)
        {
            foodViewHolder.image.setImageResource(R.drawable.ic_avocado);
        }
        else if(newfood.IdCategory() == 6)
        {
            foodViewHolder.image.setImageResource(R.drawable.ic_energy_bar);
        }
        else if(newfood.IdCategory() == 7)
        {
            foodViewHolder.image.setImageResource(R.drawable.ic_juice);
        }
        else if(newfood.IdCategory() == 8)
        {
            foodViewHolder.image.setImageResource(R.drawable.ic_pint);
        }
        else if(newfood.IdCategory() == 9)
        {
            foodViewHolder.image.setImageResource(R.drawable.ic_fried_rice);
        }
        else if(newfood.IdCategory() == 10)
        {
            foodViewHolder.image.setImageResource(R.drawable.ic_noodle);
        }
        else
        {
            foodViewHolder.image.setImageResource(R.drawable.ic_breakfast);
        }
        foodViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                {
                    Toast.makeText(fragment.getContext(), "Lộc Óc Chó", Toast.LENGTH_SHORT).show();
                }
                else {
                    DialogFoodFragment dialogFoodFragment = new DialogFoodFragment();
                    function.Instance().SetData("food", newfood, dialogFoodFragment);
                    dialogFoodFragment.setTargetFragment(fragment, 1);
                    dialogFoodFragment.show(fragment.getFragmentManager(), "DialogFragment");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.foodList.size();
    }

    //region Class View Holder
    public static class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener{
        @BindView(R.id.tv_nameFood) TextView tvNameFood;
        @BindView(R.id.tv_price) TextView tvPrice;
        @BindView(R.id.tv_status) TextView tvStatus;
        @BindView(R.id.image) ImageView image;

        private ItemClickListener itemClickListener;
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        public FoodViewHolder(@NonNull View itemView) {
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
