package com.example.crunchy_app.secciones.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.crunchy_app.R;
import com.example.crunchy_app.databinding.ActivityHomeBinding;
import com.example.crunchy_app.secciones.fragment.OrdersFragment;
import com.example.crunchy_app.secciones.fragment.AdminFragment;
import com.google.android.material.snackbar.Snackbar;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    private OrdersFragment ordersFragment;
    private AdminFragment adminFragment;

    public HomeActivity() {
        ordersFragment = new OrdersFragment();
        adminFragment = new AdminFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences prefs = getSharedPreferences("stock_prefs", Context.MODE_PRIVATE);
        if(!prefs.contains("chicharron")  || !prefs.contains("chorizos")){
            Snackbar.make(binding.getRoot(), "No hay stock disponible, debes agregar", Snackbar.LENGTH_LONG).show();
            replaceFragment(adminFragment);
        }else{
            replaceFragment(ordersFragment);
        }

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();
            if (itemId == R.id.order) {
                if(!prefs.contains("chicharron") && !prefs.contains("chorizos")){
                    Snackbar.make(binding.getRoot(), "No hay stock disponible, debes agregar", Snackbar.LENGTH_LONG).show();
                    replaceFragment(adminFragment);
                }else
                    replaceFragment(ordersFragment);
            } else if (itemId == R.id.admin) {
                replaceFragment(adminFragment);
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}