package com.example.admin.projectnow.ADAPTER;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.projectnow.DAO.categoryDAO;
import com.example.admin.projectnow.MODEL.category;
import com.example.admin.projectnow.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>{
    private List<category> list;
    private Fragment fragment;

    public CategoriesAdapter(List<category> list, Fragment fragment)
    {
        this.list = list;
        this.fragment = fragment;
    }
    public void AddItem(category category)
    {
        this.list.add(category);
        notifyItemInserted(this.list.size() - 1);
    }
    public void RemoveItem(int position)
    {
        categoryDAO.Instance(fragment.getContext()).DeleteCategory(this.list.get(position).Id());
        this.list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this.list.size());
    }
    public void UpdateItem(int position, String nameCategory)
    {
        list.get(position).NameCategory(nameCategory);
        list.set(position, list.get(position));
        notifyItemChanged(position);
        categoryDAO.Instance(fragment.getContext()).UpdateCategory(list.get(position));
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category, viewGroup, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
        final category category = this.list.get(i);
        categoryViewHolder.tv_categoryName.setText(category.NameCategory());
        if(category.Id() == 1)
        {
            categoryViewHolder.image.setImageResource(R.drawable.ic_coffee_cup);
        }
        else if(category.Id() == 2)
        {
            categoryViewHolder.image.setImageResource(R.drawable.ic_iced_tea);
        }
        else if(category.Id() == 3)
        {
            categoryViewHolder.image.setImageResource(R.drawable.ic_cake);
        }
        else if(category.Id() == 4)
        {
            categoryViewHolder.image.setImageResource(R.drawable.ic_sandwich);
        }
        else if(category.Id() == 5)
        {
            categoryViewHolder.image.setImageResource(R.drawable.ic_avocado);
        }
        else if(category.Id() == 6)
        {
            categoryViewHolder.image.setImageResource(R.drawable.ic_energy_bar);
        }
        else if(category.Id() == 7)
        {
            categoryViewHolder.image.setImageResource(R.drawable.ic_juice);
        }
        else if(category.Id() == 8)
        {
            categoryViewHolder.image.setImageResource(R.drawable.ic_pint);
        }
        else if(category.Id() == 9)
        {
            categoryViewHolder.image.setImageResource(R.drawable.ic_fried_rice);
        }
        else if(category.Id() == 10)
        {
            categoryViewHolder.image.setImageResource(R.drawable.ic_noodle);
        }
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_categoryName)
        TextView tv_categoryName;
        @BindView(R.id.image2)
        ImageView image;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
