package com.example.admin.projectnow.FRAGMENT;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.admin.projectnow.ADAPTER.CategoriesAdapter;
import com.example.admin.projectnow.CONTROLLER.SwipeController;
import com.example.admin.projectnow.CONTROLLER.SwipeControllerActions;
import com.example.admin.projectnow.DAO.categoryDAO;
import com.example.admin.projectnow.MODEL.category;
import com.example.admin.projectnow.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoriesFragment extends Fragment {
    private static CategoriesFragment instance;
    private List<category> categoryList;
    private CategoriesAdapter categoriesAdapter;
    SwipeController swipeController = null;

    //region BindView
    @BindView(R.id.rv_categories) RecyclerView rvCategories;
    //endregion

    //region Instance
    public static CategoriesFragment Instance()
    {
        if(instance == null)
        {
            instance = new CategoriesFragment();
        }
        return instance;
    }
    private void Instance(CategoriesFragment instance)
    {
        CategoriesFragment.instance = instance;
    }
    //endregion

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        rvCategories.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvCategories.setHasFixedSize(true);

        ShowInterface();

        return view;
    }
    public void ShowInterface()
    {
        categoryList = categoryDAO.Instance(getContext()).Getcategories();
        if(categoryList.size() == 0)
        {
            categoryDAO.Instance(getContext()).InsertCategory("Coffee");
            categoryDAO.Instance(getContext()).InsertCategory("Milk Tea");
            categoryDAO.Instance(getContext()).InsertCategory("Cake");
            categoryDAO.Instance(getContext()).InsertCategory("Bread");
            categoryDAO.Instance(getContext()).InsertCategory("Fruit");
            categoryDAO.Instance(getContext()).InsertCategory("Snack");
            categoryDAO.Instance(getContext()).InsertCategory("Juice");
            categoryDAO.Instance(getContext()).InsertCategory("Beer");
            categoryDAO.Instance(getContext()).InsertCategory("Rice");
            categoryDAO.Instance(getContext()).InsertCategory("Noodle");
            categoryList = categoryDAO.Instance(getContext()).Getcategories();
        }
        categoriesAdapter = new CategoriesAdapter(categoryList, this);
        rvCategories.setAdapter(categoriesAdapter);
        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                categoriesAdapter.RemoveItem(position);
            }
        });
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(rvCategories);

        rvCategories.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    @OnClick(R.id.insertCategory)
    public void InsertCategory()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inf = getLayoutInflater();
        final  View view = inf.inflate(R.layout.dialog_insert_category, null);
        final EditText et_categoryName = (EditText)view.findViewById(R.id.etCategoryNameDA);
        alertDialog.setView(view);
        alertDialog.setNegativeButton("Insert", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             categoryDAO.Instance(getContext()).InsertCategory(et_categoryName.getText().toString());
             category newCategory = new category(et_categoryName.getText().toString());
             categoriesAdapter.AddItem(newCategory);
            }
        });

        alertDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }
}
