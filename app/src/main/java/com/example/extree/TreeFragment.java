package com.example.extree;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.extree.tree.BinaryExpressionTree;
import com.example.extree.tree_draw.BinaryExpressionTreeView;

public class TreeFragment extends Fragment {
    private View fragmentView;
    BinaryExpressionTreeView binaryExpressionTreeView;

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
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_tree, container, false);
        binaryExpressionTreeView = (BinaryExpressionTreeView) fragmentView.findViewById(R.id.binary_expression_tree_view);
        binaryExpressionTreeView.setBinaryExpressionTree(new BinaryExpressionTree("((2-2)*2)+((4+1)*2)-6*(3+1)"));
        return fragmentView;
    }
}