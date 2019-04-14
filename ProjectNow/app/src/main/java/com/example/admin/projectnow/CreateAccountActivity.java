package com.example.admin.projectnow;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.admin.projectnow.DAO.accountDAO;
import com.example.admin.projectnow.MODEL.account;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateAccountActivity extends AppCompatActivity {
    private List<String> listUsername;

    //region BindView
    @BindView(R.id.create_username) EditText etUserName;
    @BindView(R.id.create_password) EditText etPassword;
    @BindView(R.id.create_Repassword) EditText etRePassword;
    @BindView(R.id.create_displayName) EditText etDisplayName;
    @BindView(R.id.create_phone) EditText etPhone;
    @BindView(R.id.create_address) EditText etAddress;
    @BindView(R.id.text_input_layout_user) TextInputLayout tilUserName;
    @BindView(R.id.text_input_layout_password) TextInputLayout tilPassword;
    @BindView(R.id.text_input_layout_repassword) TextInputLayout tilRePassword;
    @BindView(R.id.text_input_layout_displayName) TextInputLayout tilDisplayName;
    @BindView(R.id.text_input_layout_phone) TextInputLayout tilPhone;
    @BindView(R.id.text_input_layout_address) TextInputLayout tilAddress;
    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);

        listUsername = accountDAO.Instance(this).getUserNameAccount();
        etUserNameTextChanged();
    }

    //region Event
    @OnClick(R.id.create_signup)
    public void btn_signUpClick()
    {
        if(invalid())
        {
            if(checkRePassword())
            {
                account newAccount = new account(etUserName.getText().toString(),
                        etRePassword.getText().toString(),
                        etDisplayName.getText().toString(),
                        etPhone.getText().toString(),
                        etAddress.getText().toString(), 2);
                accountDAO.Instance(this).InsertAccount(newAccount);
                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Username", etUserName.getText().toString());
                bundle.putString("Password", etRePassword.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
                this.finish();
            }
            else
            {
                tilRePassword.setError("Mật Khẩu Không Trùng Khớp");
            }

        }
    }

    private void etUserNameTextChanged()
    {
        etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(checkUserName(charSequence.toString()) == false)
                {
                    tilUserName.setError("Tài Khoản Đã Bị Trùng");
                }
                else
                {
                    tilUserName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    //endregion

    //region Method
    private boolean invalid()
    {
        if(etUserName.getText().toString().equals(""))
        {
            tilUserName.setError("Đừng để trống ở User Name");
            return false;
        }
        if(etPassword.getText().toString().equals(""))
        {
            tilUserName.setError(null);
            tilPassword.setError("Đừng để trống ở Password");
            return false;
        }
        if(etRePassword.getText().toString().equals(""))
        {
            tilUserName.setError(null);
            tilPassword.setError(null);
            tilRePassword.setError("Đừng để trống ở Rewrite Password");
            return false;
        }
        if(etDisplayName.getText().toString().equals(""))
        {
            tilPassword.setError(null);
            tilRePassword.setError(null);
            tilUserName.setError(null);
            tilDisplayName.setError("Đừng để trống ở Display Name");
            return false;
        }
        if(etPhone.getText().toString().equals(""))
        {
            tilPassword.setError(null);
            tilRePassword.setError(null);
            tilUserName.setError(null);
            tilDisplayName.setError(null);
            tilPhone.setError("Đừng để trống ở Phone");
            return false;
        }
        if(etAddress.getText().toString().equals(""))
        {
            tilPassword.setError(null);
            tilRePassword.setError(null);
            tilUserName.setError(null);
            tilDisplayName.setError(null);
            tilPhone.setError(null);
            tilAddress.setError("Đừng để trống ở Address");
            return false;
        }
        tilUserName.setError(null);
        tilPassword.setError(null);
        tilRePassword.setError(null);
        tilDisplayName.setError(null);
        tilPhone.setError(null);
        tilAddress.setError(null);
        return true;
    }

    private boolean checkUserName(String s)
    {
        for (String a : listUsername)
        {
            if(a.equals(s))
            {
                return false;
            }
        }
        return true;
    }
    private boolean checkRePassword()
    {
        if(etRePassword.getText().toString().equals(etPassword.getText().toString()))
        {
            return true;
        }
        return false;
    }
    //endregion
}
