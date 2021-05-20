package com.example.extree;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class InstructionsFragment extends Fragment {
    private View fragmentView;
    private ItemViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_instructions, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        Toolbar toolbar = fragmentView.findViewById(R.id.toolbarInstructions);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> viewModel.getDataNavControllerValue().popBackStack());
        TextView textView = fragmentView.findViewById(R.id.instructions_body_text_view_part_one);
        textView.setText(Html.fromHtml(bodyTextView));
//        ScrollView scrollView = fragmentView.findViewById(R.id.scrollViewInstructions);
//        scrollView.measure(scrollView.getWidth(), scrollView.getHeight() - 300);
        return fragmentView;
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