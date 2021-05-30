package com.example.extree;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.extree.tree_draw.AnimEndListener;
import com.example.extree.tree_draw.BinaryExpressionTreeView;

import static android.content.Context.MODE_PRIVATE;

public class TreeFragment extends Fragment implements AnimEndListener {
    /* For store variables */
    SharedPreferences sharedPreferences;
    /* This view */
    private View fragmentView;
    /* Expression tree for build the tree */
    private BinaryExpressionTreeView binaryExpressionTreeView;
    /* ItemViewModel for connection between fragments */
    private ItemViewModel viewModel;
    private static final int MSG_UPDATE_TRAVERSAL = 1;
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
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
        sharedPreferences = getActivity().getSharedPreferences("com.example.extree", MODE_PRIVATE);
        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        if (viewModel.getDataCalculatorResultValue() != null) {
            binaryExpressionTreeView = fragmentView.findViewById(R.id.binary_expression_tree_view);
            binaryExpressionTreeView.setBinaryExpressionTree(viewModel.getDataBinaryExpressionTreeValue());
            binaryExpressionTreeView.setmHeightOfMenu(viewModel.getDataMenuHeightValue());
            binaryExpressionTreeView.setAnimEndListener(this);
            binaryExpressionTreeView.setTreeFragment(this);
        }
        return fragmentView;
    }

    public void showResultOfTreeTraversal(int state) {
        Intent intent = new Intent(getContext(), ResultTreeTraversalActivity.class);
        switch (state) {
            case 1:
                intent.putExtra("result_of_tree_traversal", viewModel.getDataBinaryExpressionTreeValue().PreOrder());
                break;
            case 2:
                intent.putExtra("result_of_tree_traversal", viewModel.getDataBinaryExpressionTreeValue().SymmetricOrder());

                break;
            default:
                intent.putExtra("result_of_tree_traversal", viewModel.getDataBinaryExpressionTreeValue().PostOrder());
                break;
        }
        startActivity(intent);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClickButtonTree(View view) {
        switch (view.getId()) {
            case R.id.btn_pre:
                if (sharedPreferences.getBoolean("isAnimatedTreeTraversalSettings", true)) {
                    binaryExpressionTreeView.beginPreOrderTraversal();
                } else showResultOfTreeTraversal(1);
                break;
            case R.id.btn_in:
                if (sharedPreferences.getBoolean("isAnimatedTreeTraversalSettings", true)) {
                    binaryExpressionTreeView.beginInOrderTraversal();
                } else showResultOfTreeTraversal(2);
                break;
            case R.id.btn_post:
                if (sharedPreferences.getBoolean("isAnimatedTreeTraversalSettings", true)) {
                    binaryExpressionTreeView.beginPostOrderTraversal();
                } else showResultOfTreeTraversal(3);
                break;
            case R.id.btn_stop:
                if (sharedPreferences.getBoolean("isAnimatedTreeTraversalSettings", true)) {
                    binaryExpressionTreeView.beginClear();
                }
                break;
        }
    }

    @Override
    public void animEnd(View view) {
        int id = view.getId();
        mHandler.sendEmptyMessage(MSG_UPDATE_TRAVERSAL);
    }
}