package com.example.admin.projectnow.FRAGMENT;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.projectnow.ADAPTER.FoodAdapter;
import com.example.admin.projectnow.DAO.categoryDAO;
import com.example.admin.projectnow.DAO.foodDAO;
import com.example.admin.projectnow.INTERFACE.OnInputSelected;
import com.example.admin.projectnow.MODEL.category;
import com.example.admin.projectnow.MODEL.food;
import com.example.admin.projectnow.MODEL.store;
import com.example.admin.projectnow.R;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FoodFragment extends Fragment implements OnInputSelected {
    private static FoodFragment instance;
    private List<FoodAdapter> foodAdapterList;
    private List<RecyclerView> recyclerViewList;
    private List<category> categoryList;
    private store myStore;
    private food newFood;

    //region BindView
    @BindView(R.id.layout_id) LinearLayout placeHolder;
    //endregion

    //region Instance
    public static FoodFragment Instance()
    {
        if(instance == null)
        {
            instance = new FoodFragment();
        }
        return instance;
    }
    private void Instance(FoodFragment instance)
    {
        FoodFragment.instance = instance;
    }
    //endregion

    //region Override
    @Override
    public void sendInput(String nameFood, double price, int idCategory, String nameCategory, int status) {
        newFood = new food(nameFood, price, status, idCategory, myStore.Id());
        foodDAO.Instance(getContext()).InsertFood(newFood);
        for (int i = 0; i < categoryList.size(); i++)
        {
            if(idCategory == categoryList.get(i).Id())
            {
                foodAdapterList.get(i).AddItem(newFood);
                recyclerViewList.get(i).setAdapter(foodAdapterList.get(i));
                return;
            }
        }
        categoryList.add(new category(idCategory, nameCategory));
        List<food> foods = new ArrayList<>();
        foods.add(newFood);
        AddView(foods, nameCategory);
    }

    @Override
    public void DeleteFood(food food) {
        foodDAO.Instance(getContext()).DeleteFood(food.Id());
        for(int i = 0; i < categoryList.size(); i++)
        {
            if(food.IdCategory() == categoryList.get(i).Id())
            {
                foodAdapterList.get(i).RemoveItem(food);
                recyclerViewList.get(i).setAdapter(foodAdapterList.get(i));
                return;
            }
        }
    }

    @Override
    public void UpdateFood(food f, int oldIdCategory, String nameCategory) {
        foodDAO.Instance(getContext()).UpdateFood(f);
        if(f.IdCategory() == oldIdCategory)
        {
            for(int i = 0; i < categoryList.size(); i++)
            {
                if(f.IdCategory() == categoryList.get(i).Id())
                {
                    foodAdapterList.get(i).UpdateItem(f);
                    recyclerViewList.get(i).setAdapter(foodAdapterList.get(i));
                    return;
                }
            }
        }
        else
        {
            for(int i = 0; i < categoryList.size(); i++)
            {
                if(oldIdCategory == categoryList.get(i).Id())
                {
                    foodAdapterList.get(i).RemoveItem(f);
                    recyclerViewList.get(i).setAdapter(foodAdapterList.get(i));
                    break;
                }
            }
            for (int i = 0; i < categoryList.size(); i++)
            {
                if(f.IdCategory() == categoryList.get(i).Id())
                {
                    foodAdapterList.get(i).AddItem(f);
                    recyclerViewList.get(i).setAdapter(foodAdapterList.get(i));
                    return;
                }
            }
            categoryList.add(new category(f.IdCategory(), nameCategory));
            List<food> foods = new ArrayList<>();
            foods.add(newFood);
            AddView(foods, nameCategory);
        }
    }
    //endregion -----------------------------------------------------------

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodAdapterList = new ArrayList<>();
        recyclerViewList = new ArrayList<>();
        categoryList = new ArrayList<>();
        GetData();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        ButterKnife.bind(this ,view);
        
        GetCategoryList();
        GetFoodList();

        return view;
    }

    //region Method
    private void GetData()
    {
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            myStore = (store) bundle.getSerializable("store");
        }
    }
    private void GetCategoryList()
    {
        categoryList = categoryDAO.Instance(getContext()).GetCategoryByStoreID(myStore.Id());
    }
    private void GetFoodList()
    {
        for (category category : this.categoryList)
        {
            List<food> foods = foodDAO.Instance(getContext()).GetFoodByStoreID(myStore.Id(), category.Id());
            AddView(foods, category.NameCategory());
        }
    }
    private void AddView(List<food> foods, String nameCategory)
    {
        View view = getActivity().getLayoutInflater().inflate(R.layout.item_recyclerview_food, null);
        TextView tv_title = (TextView)view.findViewById(R.id.header_id);
        tv_title.setText(nameCategory);
        RecyclerView rvFoods = (RecyclerView)view.findViewById(R.id.rv_foods);
        rvFoods.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvFoods.setHasFixedSize(true);
        rvFoods.setNestedScrollingEnabled(false);
        FoodAdapter foodAdapter = new FoodAdapter(foods, this);
        foodAdapterList.add(foodAdapter);
        rvFoods.setAdapter(foodAdapter);
        recyclerViewList.add(rvFoods);
        placeHolder.addView(view);
    }
    //endregion -------------------------------------------------


    //region Event
    @OnClick(R.id.insertFood)
    public void InsertFood()
    {
        DialogFoodFragment dialogFoodFragment = new DialogFoodFragment();
        dialogFoodFragment.setTargetFragment(this, 1);
        dialogFoodFragment.show(getFragmentManager(), "DialogFragment");
    }
    //endregion -------------------------------------------------
}
