package com.example.admin.projectnow.FRAGMENT;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.projectnow.DAO.categoryDAO;
import com.example.admin.projectnow.INTERFACE.OnInputSelected;
import com.example.admin.projectnow.MODEL.food;
import com.example.admin.projectnow.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class DialogFoodFragment extends DialogFragment {

    private List<String> Category;
    private String[] Status = {"Hết Hàng", "Còn Hàng"};
    private int idCategory = 0;
    private String nameCategory = "";
    private OnInputSelected onInputSelected;
    private food food;

    //region BindView
    @BindView(R.id.heading) TextView tvHeading;
    @BindView(R.id.input_name_food) EditText et_NameFood;
    @BindView(R.id.input_price) EditText et_price;
    @BindView(R.id.spinner_category) Spinner spn_Category;
    @BindView(R.id.spinner_status) Spinner spn_Status;
    @BindView(R.id.action_ok) Button btn_ok;
    @BindView(R.id.action_delete) Button btn_delete;
    @BindView(R.id.action_update) Button btn_update;
    //endregion

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
        GetData();
    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onInputSelected = (OnInputSelected) getTargetFragment();
        }catch (ClassCastException e){
            Log.e("DialogFragment", "onAttach: ClassCastException : " + e.getMessage() );
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_food_fragment, container, false);
        ButterKnife.bind(this, view);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, Category);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_Category.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, Status);
        adapter2.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_Status.setAdapter(adapter2);

        SetInterfaceByIndex();

        CategoryItem_Selected();
        StatusItem_Selected();

        return view;
    }

    //region Method
    private void GetData()
    {
        Category = categoryDAO.Instance(getContext()).GetNameCategory();
    }
    private void SetInterfaceByIndex()
    {
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            food = (food)bundle.getSerializable("food");
            if(food != null)
            {
                tvHeading.setText("FOOD");
                btn_ok.setVisibility(View.GONE);
                et_NameFood.setText(food.NameFood());
                et_price.setText(food.Price() + "");
                spn_Category.setSelection(food.IdCategory() - 1);
                spn_Status.setSelection(food.Status());
            }
        }
        else
        {
            btn_delete.setVisibility(View.GONE);
            btn_update.setVisibility(View.GONE);
        }

    }
    //endregion ----------------------------------------------------

    //region Event
    private void CategoryItem_Selected()
    {
        spn_Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idCategory = position + 1;
                nameCategory = Category.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private int status = 0;
    private void StatusItem_Selected()
    {
        spn_Status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @OnClick(R.id.action_cancel)
    public void CancelClick()
    {
        getDialog().dismiss();
    }

    @OnClick(R.id.action_ok)
    public void OkClick()
    {
        String nameFood = et_NameFood.getText().toString();
        String priceFood = et_price.getText().toString();
        if(!nameFood.equals("") && !priceFood.equals(""))
        {
            double price = Double.parseDouble(priceFood);
            Log.e("onInput",  "Object = " + onInputSelected);
            if(onInputSelected != null)
            {
                onInputSelected.sendInput(nameFood, price, idCategory, nameCategory, status);
            }
        }
        getDialog().dismiss();
    }
    @OnClick(R.id.action_delete)
    public void DeleteClick()
    {
        String nameFood = et_NameFood.getText().toString();
        String priceFood = et_price.getText().toString();
        if(!nameFood.equals("") && !priceFood.equals(""))
        {
            double price = Double.parseDouble(priceFood);
            food newFood = new food(food.Id(), nameFood, price, status, idCategory, food.IdStore());
            if(onInputSelected != null)
            {
                onInputSelected.DeleteFood(newFood);
            }
        }
        getDialog().dismiss();
    }
    @OnClick(R.id.action_update)
    public void UpdateClick()
    {
        String nameFood = et_NameFood.getText().toString();
        String priceFood = et_price.getText().toString();
        if(!nameFood.equals("") && !priceFood.equals(""))
        {
            double price = Double.parseDouble(priceFood);
            food newFood = new food(food.Id(), nameFood, price, status, idCategory, food.IdStore());
            if(onInputSelected != null)
            {
                onInputSelected.UpdateFood(newFood, food.IdCategory(), nameCategory);
            }
        }
        getDialog().dismiss();
    }
    //endregion ----------------------------------------------------
}
