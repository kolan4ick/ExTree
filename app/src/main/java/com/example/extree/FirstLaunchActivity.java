package com.example.extree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class FirstLaunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_launch);
        AppCompatButton btnSubmit = findViewById(R.id.button_start_first_launch);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .5));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);
        TextView textView = findViewById(R.id.first_launch_body_text_view_part_one);
        textView.setText(Html.fromHtml(bodyTextView));
    }

    private final String bodyTextView = "<strong>What is this application for:</strong>" +
            "<ul>" +
            "  <li>" +
            "    The most important thing, without which we would not have flown into space, have not advanced so far in science" +
            "    and the development of our civilization - counting." +
            "  </li>" +
            "  <li>" +
            "    Display a binary expression tree and use it to learn how to build binary trees and translate an" +
            "    expression" +
            "    from one form of writing to another, it will be especially useful for people whose activities are somehow related to" +
            "    computer science. After all, the prefix form of writing (it is also called the Polish inverse record) is used in all" +
            "    programming languages to store and calculate expressions." +
            "  </li>" +
            "</ul>" +
            "<strong>Functionality of this application:</strong>" +
            "<ul>" +
            "  <li>" +
            "    Work in normal calculator mode." +
            "  </li>" +
            "  <li>" +
            "    Save the values of previous calculations in the database, for further display and use on the History page." +
            "  </li>" +
            "  <li>" +
            "    Build binary expression trees." +
            "  </li>" +
            "  <li>" +
            "    Animated traversal of the binary expression tree (prefix, infix and postfix traversals)." +
            "  </li>" +
            "  <li>" +
            "    Get the results of the traversals." +
            "  </li>" +
            "  You can find instructions for using the program on the About page, which is located in the menu of the Calculator" +
            "  page." +
            "</ul>";
}