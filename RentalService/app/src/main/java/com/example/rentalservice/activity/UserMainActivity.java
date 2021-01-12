package com.example.rentalservice.activity;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.rentalservice.R;
import com.example.rentalservice.fragment.UserRentalFragment;
import com.example.rentalservice.fragment.UserRentalInfoFragment;
import com.example.rentalservice.fragment.UserRentalInfoFragment_0;
import com.example.rentalservice.ui.main.UserSectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class UserMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_main);
        UserSectionsPagerAdapter usersectionsPagerAdapter = new UserSectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.user_view_pager);
        viewPager.setAdapter(usersectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.user_tabs);
        tabs.setupWithViewPager(viewPager);
        Toolbar toolbar = findViewById(R.id.user_tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("공공장비 온라인센터 (사용자 모드)");
    }

    public void onFragmentChange(int index, String string){
        UserRentalInfoFragment userRentalinfoFragment = new UserRentalInfoFragment(string);
        UserRentalInfoFragment_0 userRentalFragment_0 = new UserRentalInfoFragment_0();

        if(index == 0){
            getSupportFragmentManager().beginTransaction().remove(userRentalinfoFragment).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.user_fragment_rental_info, userRentalFragment_0).commit();
        }
        else if(index == 1){
            getSupportFragmentManager().beginTransaction().remove(userRentalFragment_0).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.user_fragment_rental_info_0, userRentalinfoFragment).commit();
        }
    }

}