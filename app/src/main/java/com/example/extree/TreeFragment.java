package com.example.extree;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.extree.tree.BinaryExpressionTree;
import com.example.extree.tree_draw.BinaryExpressionTreeView;

public class TreeFragment extends Fragment {
    private View fragmentView;
    BinaryExpressionTreeView binaryExpressionTreeView;
    private ItemViewModel viewModel;

    public TreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_tree, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        if (viewModel.getDataCalculatorResultValue() != null) {
            binaryExpressionTreeView = (BinaryExpressionTreeView) fragmentView.findViewById(R.id.binary_expression_tree_view);
            binaryExpressionTreeView.setBinaryExpressionTree(viewModel.getDataBinaryExpressionTreeValue());
        }
        // Inflate the layout for this fragment

        return fragmentView;
    }
}