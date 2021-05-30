package com.example.extree;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.extree.database.DatabaseHelper;
import com.example.extree.tree.BinaryExpressionTree;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    /* For store variables */
    private SharedPreferences prefs = null;
    /* ItemViewModel for connection between fragments */
    private ItemViewModel viewModel;
    /* For access and store database */
    private DatabaseHelper dbHelper;
    private BottomNavigationView bottomNavigationView;
    private final int flags = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        viewModel.setDataMenuHeightValue(getResources().getDimensionPixelSize(getResources().getIdentifier("design_bottom_navigation_height", "dimen", getPackageName())));
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("com.example.extree", MODE_PRIVATE);
        bottomNavigationView = findViewById(R.id.activity_main_bottom_navigation_view);
        viewModel.setDataMenuValue(bottomNavigationView);
        dbHelper = new DatabaseHelper(this.getBaseContext());
        viewModel.setDataBaseHelperValue(dbHelper);
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                getWindow().getDecorView().setSystemUiVisibility(flags);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(flags);
        if (prefs.getBoolean("firstrun", true)) {
            Intent intent = new Intent(MainActivity.this, FirstLaunchActivity.class);
            startActivity(intent);
            prefs.edit().putBoolean("firstrun", false).apply();
        } else {
            NavController navController = Navigation.findNavController(this, R.id.activity_main_nav_host_fragment);
            viewModel.setDataNavControllerValue(navController);
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
        if (!resultText.contains("=")) {
            BinaryExpressionTree binaryExpressionTree = new BinaryExpressionTree(resultText);
            if (binaryExpressionTree.Evaluate() != null) {
                viewModel.setDataBinaryExpressionTreeValue(binaryExpressionTree);
                bottomNavigationView.getMenu().getItem(1).setEnabled(true);
            } else {
                viewModel.setDataBinaryExpressionTreeValue(null);
                bottomNavigationView.getMenu().getItem(1).setEnabled(false);
            }
        }
        if (viewModel.getDataBinaryExpressionTreeValue() != null && viewModel.getDataBinaryExpressionTreeValue().Evaluate() != null) {
            NavController navigation = Navigation.findNavController(findViewById(R.id.activity_main_nav_host_fragment));
            bottomNavigationView.getMenu().getItem(1).setEnabled(true);
            navigation.navigate(R.id.treeFragment);
        }
    }

    public void onClickButtonTree(View view) {
        FragmentManager fm = getSupportFragmentManager();
        TreeFragment treeFragment = (TreeFragment) ((fm.getFragments().get(0)).getChildFragmentManager().getFragments().get(0));
        treeFragment.onClickButtonTree(view);
    }
}