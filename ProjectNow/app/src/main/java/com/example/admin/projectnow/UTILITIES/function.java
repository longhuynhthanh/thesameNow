package com.example.admin.projectnow.UTILITIES;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.EditText;
import android.widget.Toast;
import com.example.admin.projectnow.R;
import java.io.Serializable;

public class function {
    private static function instance;
    public static function Instance()
    {
        if(instance == null)
        {
            instance = new function();
        }
        return instance;
    }
    private void Instance(function instance)
    {
        function.instance = instance;
    }
    public void initFragment(FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
    public void SetData(String key, Object object, Fragment fragment)
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable(key, (Serializable)object);
        fragment.setArguments(bundle);
    }
    public void CheckInput(EditText et , Context context){
        try {
            et.getText().toString();
        }catch (Exception e){
            Toast.makeText(context, "Vui lòng Nhập Lại", Toast.LENGTH_SHORT).show();
        }
    }
}
