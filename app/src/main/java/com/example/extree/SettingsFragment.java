package com.example.extree;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SettingsFragment extends Fragment {
    private View fragmentView;
    private ItemViewModel viewModel;
    private SharedPreferences prefs;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_settings, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        prefs = getContext().getSharedPreferences("com.example.extree", Context.MODE_PRIVATE);
        Toolbar toolbar = fragmentView.findViewById(R.id.toolbarSettings);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> viewModel.getDataNavControllerValue().popBackStack());

        Spinner spinnerFontSize = fragmentView.findViewById(R.id.spinner_font_size);
        spinnerFontSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefs.edit().putInt("fontSizeSettingsIndex", position).apply();
                prefs.edit().putInt("fontSizeSettingsValue", Integer.parseInt(spinnerFontSize.getItemAtPosition(position).toString())).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.custom_spinner, getResources().getStringArray(R.array.fontSizes));
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinnerFontSize.setAdapter(adapter);
        spinnerFontSize.setSelection(prefs.getInt("fontSizeSettingsIndex", 2));

        Spinner spinnerCircleRadius = fragmentView.findViewById(R.id.spinner_circle_radius);
        spinnerCircleRadius.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefs.edit().putInt("circleRadiusSettingsIndex", position).apply();
                prefs.edit().putInt("circleRadiusSettingsValue", Integer.parseInt(spinnerCircleRadius.getItemAtPosition(position).toString())).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapter = new ArrayAdapter<>(getContext(), R.layout.custom_spinner, getResources().getStringArray(R.array.circleRadiuses));
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinnerCircleRadius.setAdapter(adapter);
        spinnerCircleRadius.setSelection(prefs.getInt("circleRadiusSettingsIndex", 3));

        Spinner spinnerTopAndBottomOffset = fragmentView.findViewById(R.id.spinner_top_and_bottom_offset);
        spinnerTopAndBottomOffset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefs.edit().putInt("topAndBottomOffsetIndex", position).apply();
                prefs.edit().putInt("topAndBottomOffsetValue", Integer.parseInt(spinnerTopAndBottomOffset.getItemAtPosition(position).toString())).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapter = new ArrayAdapter<>(getContext(), R.layout.custom_spinner, getResources().getStringArray(R.array.topAndBottomOffsets));
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinnerTopAndBottomOffset.setAdapter(adapter);
        spinnerTopAndBottomOffset.setSelection(prefs.getInt("topAndBottomOffsetIndex", 4));

        Spinner spinnerWidth = fragmentView.findViewById(R.id.spinner_x_gap);
        spinnerWidth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefs.edit().putInt("widthSettingsIndex", position).apply();
                prefs.edit().putInt("widthSettingsValue", Integer.parseInt(spinnerWidth.getItemAtPosition(position).toString())).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapter = new ArrayAdapter<>(getContext(), R.layout.custom_spinner, getResources().getStringArray(R.array.widths));
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinnerWidth.setAdapter(adapter);
        spinnerWidth.setSelection(prefs.getInt("widthSettingsIndex", 0));

        Spinner spinnerHeight = fragmentView.findViewById(R.id.spinner_y_gap);
        spinnerHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefs.edit().putInt("heightSettingsIndex", position).apply();
                prefs.edit().putInt("heightSettingsValue", Integer.parseInt(spinnerHeight.getItemAtPosition(position).toString())).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapter = new ArrayAdapter<>(getContext(), R.layout.custom_spinner, getResources().getStringArray(R.array.heights));
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinnerHeight.setAdapter(adapter);
        spinnerHeight.setSelection(prefs.getInt("heightSettingsIndex", 6));

        Spinner spinnerAnimationDuration = fragmentView.findViewById(R.id.spinner_animation_duration);
        spinnerAnimationDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefs.edit().putInt("animationDurationSettingsIndex", position).apply();
                prefs.edit().putFloat("animationDurationSettingsValue", Float.parseFloat(spinnerAnimationDuration.getItemAtPosition(position).toString())).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapter = new ArrayAdapter<>(getContext(), R.layout.custom_spinner, getResources().getStringArray(R.array.animationDurations));
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinnerAnimationDuration.setAdapter(adapter);
        spinnerAnimationDuration.setSelection(prefs.getInt("animationDurationSettingsIndex", 2));

        CheckBox checkBox = fragmentView.findViewById(R.id.checkbox_is_animated_tree_traversal);
        checkBox.setChecked(prefs.getBoolean("isAnimatedTreeTraversalSettings", true));
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkBox.setChecked(isChecked);
            prefs.edit().putBoolean("isAnimatedTreeTraversalSettings", isChecked).apply();
        });
        return fragmentView;

    }
}