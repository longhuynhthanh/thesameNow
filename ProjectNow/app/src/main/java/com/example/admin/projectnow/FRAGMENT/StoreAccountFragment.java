package com.example.admin.projectnow.FRAGMENT;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.admin.projectnow.ADAPTER.AdminAdapter;
import com.example.admin.projectnow.CONTROLLER.SwipeController;
import com.example.admin.projectnow.CONTROLLER.SwipeControllerActions;
import com.example.admin.projectnow.DAO.accountDAO;
import com.example.admin.projectnow.DAO.locationDAO;
import com.example.admin.projectnow.DAO.storeDAO;
import com.example.admin.projectnow.MODEL.account;
import com.example.admin.projectnow.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StoreAccountFragment extends Fragment {
    private static StoreAccountFragment instance;
    private List<account> accountList;
    private AdminAdapter adminAdapter;
    SwipeController swipeController = null;

    //region BindView
    @BindView(R.id.rv_accounts) RecyclerView rvAccounts;
    //endregion

    //region Instance
    public static StoreAccountFragment Instance()
    {
        if (instance == null)
        {
            instance = new StoreAccountFragment();
        }
        return instance;
    }
    private void Instance(StoreAccountFragment instance)
    {
        StoreAccountFragment.instance = instance;
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
        accountList = accountDAO.Instance(this.getContext()).getAccountIsStores();
        adminAdapter = new AdminAdapter(accountList, this, 1);
        rvAccounts.setAdapter(adminAdapter);
        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                adminAdapter.RemoveItem(position);
            }
        }, 1);
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(rvAccounts);

        rvAccounts.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    @OnClick(R.id.insertAccount)
    public void Insert()
    {
        AlertDialog.Builder alerDialog = new AlertDialog.Builder(StoreAccountFragment.this.getContext(),android.R.style.Theme_Light_NoTitleBar_Fullscreen);
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
                                                etAddress.getText().toString(), 1);
                accountDAO.Instance(StoreAccountFragment.this.getContext()).InsertAccount(newAccount);
                adminAdapter.AddItem(newAccount);

                // Insert Location
                locationDAO.Instance(StoreAccountFragment.this.getContext()).InsertLocation("No Name");
                // ----------------

                // Insert Store
                int idLocationMax = locationDAO.Instance(StoreAccountFragment.this.getContext()).getMaxLocationID();
                if(idLocationMax != -1)
                {
                    storeDAO.Instance(StoreAccountFragment.this.getContext()).InsertStore("No Name", idLocationMax, etUserName.getText().toString());
                }
                // -----------------------------
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
