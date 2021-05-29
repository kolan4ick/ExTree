package com.example.extree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class ResultTreeTraversalActivity extends AppCompatActivity {
    private String treeTraversal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_tree_traversal);
        AppCompatButton btnClose = findViewById(R.id.button_close_result_tree_traversal);
        AppCompatButton btnCopy = findViewById(R.id.button_copy_result_tree_traversal);
        btnClose.setOnClickListener(v -> finish());
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .7));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);
        Bundle arguments = getIntent().getExtras();
        treeTraversal = arguments.getString("result_of_tree_traversal");
        TextView treeTraversalResult = findViewById(R.id.result_tree_traversal_text_view);
        treeTraversalResult.setText(treeTraversal);
        btnCopy.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("result_of_tree_traversal", treeTraversal);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Результат успішно зкопійовано!", Toast.LENGTH_SHORT).show();
        });
    }
}