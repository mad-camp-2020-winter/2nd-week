package com.example.rentalservice;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.rentalservice.ui.main.SectionsPagerAdapter;

import org.conscrypt.Conscrypt;

import java.security.Security;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }

    public void FragmentChange(int index, ListViewItem listViewItem){
        InfoDetailFragment infoDetailFragment = new InfoDetailFragment(listViewItem);
        InfoFragment infoFragment = new InfoFragment();

        if(index == 0){
            getSupportFragmentManager().beginTransaction().remove(infoDetailFragment).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_info_detail, infoFragment).commit();
        }
        else if(index == 1){
            getSupportFragmentManager().beginTransaction().remove(infoFragment).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_info, infoDetailFragment).commit();
        }
    }

}