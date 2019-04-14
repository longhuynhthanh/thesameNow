package com.example.admin.projectnow.FRAGMENT;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.projectnow.DAO.accountDAO;
import com.example.admin.projectnow.MODEL.account;
import com.example.admin.projectnow.R;
import com.example.admin.projectnow.UTILITIES.function;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyUserFragment extends Fragment {
    private static MyUserFragment instance;
    private account account;

    //region BindView
    @BindView(R.id.userAccount) EditText etUserName;
    @BindView(R.id.passwordAccount) EditText etPassword;
    @BindView(R.id.displayNameAccount) EditText etDisplayName;
    @BindView(R.id.TypeAccount) EditText etTypeAccount;
    @BindView(R.id.phoneAccount) EditText etPhone;
    @BindView(R.id.addressAccount) EditText etAddress;
    //endregion

    //region Instance
    public static MyUserFragment Instance()
    {
        if(instance == null)
        {
            instance = new MyUserFragment();
        }
        return instance;
    }
    private void Instance(MyUserFragment instance)
    {
        MyUserFragment.instance = instance;
    }
    //endregion

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_user, container, false);
        ButterKnife.bind(this, view);
        account = new account();
        getData();
        SetInterface();
        return view;
    }

    //region Method
    private void getData()
    {
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            account = (account) bundle.getSerializable("account");
        }
    }
    private void SetInterface()
    {
        etUserName.setText(account.UserName());
        etDisplayName.setText(account.DisplayName());
        etPassword.setText(account.Password());
        if(account.Type() == 0)
        {
            etTypeAccount.setText("Admin");
        }
        else if(account.Type() == 1)
        {
            etTypeAccount.setText("Chủ Store");
        }
        else if(account.Type() == 2)
        {
            etTypeAccount.setText("Khách hàng");
        }
        etPhone.setText(account.Phone());
        etAddress.setText(account.Address());
    }

    //endregion ---------------------------------------------

    //region Event
    @OnClick(R.id.btn_updateAccount)
    public void btnUpdateClick()
    {
        account newAccount = new account(etUserName.getText().toString(), etPassword.getText().toString(), etDisplayName.getText().toString(), etPhone.getText().toString(), etAddress.getText().toString(), account.Type());
        if(accountDAO.Instance(getContext()).UpdateAccount(newAccount) > 0)
        {
            function.Instance().SetData("account", newAccount, UserFragment.Instance());
            Toast.makeText(getContext(), "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
            function.Instance().initFragment(getActivity().getSupportFragmentManager(), UserFragment.Instance());
        }
    }
    //endregion ------------------------------------
}
