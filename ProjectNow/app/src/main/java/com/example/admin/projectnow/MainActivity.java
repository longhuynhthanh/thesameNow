package com.example.admin.projectnow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.example.admin.projectnow.MODEL.account;
import com.example.admin.projectnow.FRAGMENT.FavoritesFragment;
import com.example.admin.projectnow.FRAGMENT.HomeFragment;
import com.example.admin.projectnow.FRAGMENT.NotificationsFragment;
import com.example.admin.projectnow.FRAGMENT.UserFragment;
import com.example.admin.projectnow.UTILITIES.function;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private account account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        GetData();
        function.Instance().SetData("account", account, UserFragment.Instance());
        function.Instance().SetData("account", account, HomeFragment.Instance());
        function.Instance().SetData("account", account, NotificationsFragment.Instance());
        function.Instance().initFragment(getSupportFragmentManager(), HomeFragment.Instance());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId())
            {
                case R.id.nav_home:
                    selectedFragment = HomeFragment.Instance();
                    break;
                case R.id.nav_favorites:
                    selectedFragment = FavoritesFragment.Instance();
                    break;
                case R.id.nav_notifications:
                    selectedFragment = NotificationsFragment.Instance();
                    break;
                case R.id.nav_user:
                    selectedFragment =  UserFragment.Instance();
                    break;
            }
            function.Instance().initFragment(getSupportFragmentManager(), selectedFragment);
            return true;
        }
    };

    private void GetData()
    {
        Intent intent = getIntent();
        if(intent != null)
        {
            Bundle bundle = intent.getExtras();
            if(bundle != null)
            {
                account = (account)bundle.getSerializable("account");
            }
        }
    }
}
