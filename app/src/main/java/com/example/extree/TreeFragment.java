package com.example.extree;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.extree.tree_draw.AnimEndListener;
import com.example.extree.tree_draw.BinaryExpressionTreeView;

public class TreeFragment extends Fragment implements AnimEndListener {
    private View fragmentView;
    private BinaryExpressionTreeView binaryExpressionTreeView;
    private ItemViewModel viewModel;
    private static final int MSG_UPDATE_TRAVERSAL = 1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            binaryExpressionTreeView.Next();
        }
    };

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
            binaryExpressionTreeView = fragmentView.findViewById(R.id.binary_expression_tree_view);
            binaryExpressionTreeView.setBinaryExpressionTree(viewModel.getDataBinaryExpressionTreeValue());
            binaryExpressionTreeView.setAnimEndListener(this);
        }
        return fragmentView;
    }

    @SuppressLint("NonConstantResourceId")
    public void onClickButtonTree(View view) {
        switch (view.getId()) {
            case R.id.btn_pre:
                binaryExpressionTreeView.beginPreOrderTraversal();
                break;

            case R.id.btn_in:
                binaryExpressionTreeView.beginInOrderTraversal();
                break;

            case R.id.btn_post:
                binaryExpressionTreeView.beginPostOrderTraversal();
                break;
        }
    }

    @Override
    public void animEnd(View view) {
        int id = view.getId();
        mHandler.sendEmptyMessage(MSG_UPDATE_TRAVERSAL);
    }
}