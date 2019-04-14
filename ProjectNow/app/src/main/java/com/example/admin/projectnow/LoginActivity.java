package com.example.admin.projectnow;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.projectnow.DAO.accountDAO;
import com.example.admin.projectnow.MODEL.account;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    //region BindView
    @BindView(R.id.input_username) TextInputEditText etUserName;
    @BindView(R.id.input_password) TextInputEditText etPassword;
    @BindView(R.id.link_create) TextView link_create;
    //endregion
    private String userName;
    private String password;
    private account account;
    private List<account> accountList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getData();
    }

    //region Event
    @OnClick(R.id.link_create)
    public void link_createClick()
    {
        Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_signUp)
    public void btn_LoginClick()
    {
        if(checkAccount(etUserName.getText().toString(), etPassword.getText().toString()))
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("account", account);
            intent.putExtras(bundle);
            startActivity(intent);
            this.finish();
        }
        else
        {
            Toast.makeText(this, "Tài Khoản Không Đúng, Vui Lòng Kiểm Tra Lại", Toast.LENGTH_SHORT).show();
        }
    }

    //endregion --------------------------------------------


    //region Method
    private void getData()
    {
        accountList = accountDAO.Instance(this).getAccount();
        if(accountList.size() == 0)
        {
            accountDAO.Instance(this).InsertAccount(new account("admin", "123", "Admim", "12345", "ĐN", 0));
            accountList = accountDAO.Instance(this).getAccount();
        }
        userName = "";
        password = "";
        Intent intent = getIntent();
        if(intent != null)
        {
            Bundle bundle = intent.getExtras();
            if(bundle != null)
            {
                userName = bundle.getString("Username");
                password = bundle.getString("Password");
            }
        }
        etUserName.setText(userName);
        etPassword.setText(password);
    }
    private boolean checkAccount(String userName, String password)
    {
        for (account a : accountList)
        {
            if(a.UserName().equals(userName) && a.Password().equals(password))
            {
                account = a;
                return true;
            }
        }
        return false;
    }
    //endregion
}
