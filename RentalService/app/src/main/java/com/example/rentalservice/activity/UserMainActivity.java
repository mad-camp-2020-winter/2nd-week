package com.example.rentalservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.rentalservice.R;
import com.example.rentalservice.ui.main.SectionsPagerAdapter;
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
//        Toolbar toolbar = findViewById(R.id.user_tool_bar);
//        setSupportActionBar(toolbar);

    }

}