package com.example.aplicatiemanagementfilme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.aplicatiemanagementfilme.fragments.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializare sweep view
        initComponents();
        initViewPagerAdapter();
    }


    // Functii
    // Initializare componente
    private void initComponents() {
        viewPager = findViewById(R.id.viewPager_main);
        fragmentManager = getSupportFragmentManager();
    }

    // Setare adapter
    private void initViewPagerAdapter() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager);
        viewPager.setAdapter(adapter);
    }

}