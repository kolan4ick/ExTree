package com.example.extree;

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
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SettingsFragment extends Fragment {
    private View fragmentView;
    private ItemViewModel viewModel;
    private SharedPreferences prefs = null;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getContext().getSharedPreferences("com.example.extree", getContext().MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_settings, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        Toolbar toolbar = (Toolbar) fragmentView.findViewById(R.id.toolbarSettings);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> viewModel.getDataNavControllerValue().popBackStack());

        List<Integer> fontSizes = new ArrayList<>();
        Collections.addAll(fontSizes, 10, 12, 14, 18, 20, 24, 28, 30, 32, 34);

        List<Integer> circleRadiuses = new ArrayList<>();
        Collections.addAll(circleRadiuses, 15, 18, 21, 24, 27, 30, 33, 36, 39, 42);

        List<Integer> topAndBottomOffsets = new ArrayList<>();
        Collections.addAll(topAndBottomOffsets, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50);

        List<Integer> widths = new ArrayList<>();
        Collections.addAll(widths, 2, 4, 6, 8, 12, 14, 16, 18, 22, 24);

        List<Integer> heights = new ArrayList<>();
        Collections.addAll(heights, 4, 8, 12, 16, 20, 24, 28, 32, 36, 40);

        List<Float> animationDurations = new ArrayList<>();
        Collections.addAll(animationDurations, 0.5f, 0.75f, 1.0f, 1.25f, 1.5f, 1.75f, 2.0f, 2.5f, 3.0f);

        Spinner spinnerFontSize = (Spinner) fragmentView.findViewById(R.id.spinner_font_size);
        ArrayAdapter<Integer> dataFontSizesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, fontSizes);
        dataFontSizesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFontSize.setAdapter(dataFontSizesAdapter);
        spinnerFontSize.setSelection(prefs.getInt("fontSizeSettingsIndex", 2));
        spinnerFontSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefs.edit().putInt("fontSizeSettingsIndex", position).apply();
                prefs.edit().putInt("fontSizeSettingsValue", fontSizes.get(position)).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Spinner spinnerCircleRadius = (Spinner) fragmentView.findViewById(R.id.spinner_circle_radius);
        ArrayAdapter<Integer> dataCircleRadiusesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, circleRadiuses);
        dataCircleRadiusesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCircleRadius.setAdapter(dataCircleRadiusesAdapter);
        spinnerCircleRadius.setSelection(prefs.getInt("circleRadiusSettingsIndex", 3));
        spinnerCircleRadius.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefs.edit().putInt("circleRadiusSettingsIndex", position).apply();
                prefs.edit().putInt("circleRadiusSettingsValue", fontSizes.get(position)).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerTopAndBottomOffset = (Spinner) fragmentView.findViewById(R.id.spinner_top_and_bottom_offset);
        ArrayAdapter<Integer> dataTopAndBottomOffsetsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, topAndBottomOffsets);
        dataTopAndBottomOffsetsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTopAndBottomOffset.setAdapter(dataTopAndBottomOffsetsAdapter);
        spinnerTopAndBottomOffset.setSelection(prefs.getInt("topAndBottomOffsetIndex", 4));
        spinnerTopAndBottomOffset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefs.edit().putInt("topAndBottomOffsetIndex", position).apply();
                prefs.edit().putInt("topAndBottomOffsetValue", fontSizes.get(position)).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerWidth = (Spinner) fragmentView.findViewById(R.id.spinner_x_gap);
        ArrayAdapter<Integer> dataWidthsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, widths);
        dataWidthsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWidth.setAdapter(dataWidthsAdapter);
        spinnerWidth.setSelection(prefs.getInt("widthSettingsIndex", 0));
        spinnerWidth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefs.edit().putInt("widthSettingsIndex", position).apply();
                prefs.edit().putInt("widthSettingsValue", fontSizes.get(position)).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerHeight = (Spinner) fragmentView.findViewById(R.id.spinner_y_gap);
        ArrayAdapter<Integer> dataHeightsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, heights);
        dataHeightsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHeight.setAdapter(dataHeightsAdapter);
        spinnerHeight.setSelection(prefs.getInt("heightSettingsIndex", 6));
        spinnerHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefs.edit().putInt("heightSettingsIndex", position).apply();
                prefs.edit().putInt("heightSettingsValue", fontSizes.get(position)).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerAnimationDuration = (Spinner) fragmentView.findViewById(R.id.spinner_animation_duration);
        ArrayAdapter<Float> dataAnimationDurationsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, animationDurations);
        dataAnimationDurationsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnimationDuration.setAdapter(dataAnimationDurationsAdapter);
        spinnerAnimationDuration.setSelection(prefs.getInt("animationDurationSettingsIndex", 2));
        spinnerAnimationDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefs.edit().putInt("animationDurationSettingsIndex", position).apply();
                prefs.edit().putFloat("animationDurationSettingsValue", animationDurations.get(position)).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return fragmentView;

    }
}