package com.example.extree;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.extree.tree.BinaryExpressionTree;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs = null;
    private ItemViewModel viewModel;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
            startActivity(intent);
            prefs.edit().putBoolean("firstrun", false).commit();
        } else {
            NavController navController = Navigation.findNavController(this, R.id.activity_main_nav_host_fragment);
            BottomNavigationView bottomNavigationView = findViewById(R.id.activity_main_bottom_navigation_view);
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        }
    }

    public void onClickButtonCalculator(View view) {
        FragmentManager fm = getSupportFragmentManager();
        CalculatorFragment calculatorFragment = (CalculatorFragment) ((fm.getFragments().get(0)).getChildFragmentManager().getFragments().get(0));
        calculatorFragment.onClickButtonCalculator(view);
    }

    public void onClickButtonGenerateTreeCalculator(View view) {
        String resultText = ((TextView) findViewById(R.id.textView)).getText().toString();
        if (!resultText.contains("="))
            viewModel.setDataBinaryExpressionTreeValue(new BinaryExpressionTree(resultText));
        NavController navigation = Navigation.findNavController(findViewById(R.id.activity_main_nav_host_fragment));
        navigation.navigate(R.id.treeFragment);
    }

    public void onClickButtonTree(View view) {
        FragmentManager fm = getSupportFragmentManager();
        TreeFragment treeFragment = (TreeFragment) ((fm.getFragments().get(0)).getChildFragmentManager().getFragments().get(0));
        treeFragment.onClickButtonTree(view);
    }
}