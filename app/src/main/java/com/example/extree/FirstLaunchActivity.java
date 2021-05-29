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

import static android.text.Html.FROM_HTML_MODE_LEGACY;

public class FirstLaunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_launch);
        AppCompatButton btnSubmit = findViewById(R.id.button_start_first_launch);
        btnSubmit.setOnClickListener(v -> finish());
        AppCompatButton btnClose = findViewById(R.id.button_close_first_launch);
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
        TextView textView = findViewById(R.id.first_launch_body_text_view_part_one);
        textView.setText(Html.fromHtml(bodyTextView, FROM_HTML_MODE_LEGACY));
    }

    private final String bodyTextView = "<strong>Для чого призначений даний застосунок:</strong>" +
            "<ul>" +
            "  <li>" +
            "    Для найважливішої справи, без якої ми б не полетіли в космос, не просунулись так далеко в науці та розвитку нашої цивілізації – рахування." +
            "  </li>" +
            "  <li>" +
            "   Також для того, щоб відобразити бінарне дерево виразів, та з його допомогою навчитись будувати бінарні дерева та переводити вираз з однієї форми запису в іншу, особливо це буде корисно для людей, діяльність яких, так, чи інакше пов’язано з інформатикою. Адже префіксна форма запису (її ще називають польським інверсним записом) використовується всіма мовами програмування для зберігання та обрахування виразів." +
            "  </li>" +
            "</ul>" +
            "<strong>Функціонал даного застосунку:</strong>" +
            "<ul>" +
            "  <li>" +
            "    Працювати у режимі звичайного калькулятору." +
            "  </li>" +
            "  <li>" +
            "    Зберігати значення попередніх обрахунків у базу даних, для подальшого відображення та використання на сторінці Історія." +
            "  </li>" +
            "  <li>" +
            "    Будувати бінарні дерева виразів." +
            "  </li>" +
            "  <li>" +
            "    Анімовано проводити обхід бінарного дерева виразів(префіксний, інфіксний та постфіксний обходи)." +
            "  </li>" +
            "  <li>" +
            "    Отримувати результати обходів." +
            "  </li>" +
            "  Ви можете знайти інструкції з використання програмою на сторінці About, що знаходиться в меню сторінки Calculator." +
            "</ul>";
}