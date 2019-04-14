package com.example.admin.projectnow.FRAGMENT;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.admin.projectnow.ADAPTER.AdminAdapter;
import com.example.admin.projectnow.DAO.accountDAO;
import com.example.admin.projectnow.DAO.locationDAO;
import com.example.admin.projectnow.DAO.storeDAO;
import com.example.admin.projectnow.MODEL.account;
import com.example.admin.projectnow.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCustomerFragment extends Fragment {
    private static MyCustomerFragment instance;
    private List<account> accountList;
    private AdminAdapter adminAdapter;

    //region BindView
    @BindView(R.id.rv_accounts)
    RecyclerView rvAccounts;
    //endregion
    //region Instance
    public static MyCustomerFragment Instance()
    {
        if (instance == null)
        {
            instance = new MyCustomerFragment();
        }
        return instance;
    }
    //endregion

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        ButterKnife.bind(this, view);
        rvAccounts.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvAccounts.setHasFixedSize(true);
        UpdateInterface();
        return view;
    }

    public void UpdateInterface()
    {
        accountList = accountDAO.Instance(this.getContext()).getAccountIsCustomer();
        adminAdapter = new AdminAdapter(accountList, this, 2);
        rvAccounts.setAdapter(adminAdapter);
    }

    @OnClick(R.id.insertAccount)
    public void Insert()
    {
        AlertDialog.Builder alerDialog = new AlertDialog.Builder(MyCustomerFragment.this.getContext(),android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        LayoutInflater inf = getLayoutInflater();
        View view = inf.inflate(R.layout.dialog_insert_account, null);
        final EditText etUserName = (EditText)view.findViewById(R.id.etUserNameDA);
        final EditText etDisplayName = (EditText)view.findViewById(R.id.etDisplayNameDA);
        final EditText etPassword = (EditText)view.findViewById(R.id.etPasswordDA);
        final EditText etPhone = (EditText)view.findViewById(R.id.etPhoneDA);
        final EditText etAddress = (EditText)view.findViewById(R.id.etAddressDA);
        alerDialog.setView(view);
        alerDialog.setNegativeButton("Insert", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                account newAccount = new account(etUserName.getText().toString(),
                        etPassword.getText().toString(),
                        etDisplayName.getText().toString(),
                        etPhone.getText().toString(),
                        etAddress.getText().toString(), 2);
                accountDAO.Instance(MyCustomerFragment.this.getContext()).InsertAccount(newAccount);
                adminAdapter.AddItem(newAccount);
            }
        });
        alerDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alerDialog.show();
    }
}
