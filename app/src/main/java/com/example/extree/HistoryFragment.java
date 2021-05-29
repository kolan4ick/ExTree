package com.example.extree;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.extree.database.CalculatorModel;
import com.example.extree.tree.BinaryExpressionTree;

import java.util.List;

public class HistoryFragment extends Fragment {
    /* This view */
    private View fragmentView;
    /* ItemViewModel for connection between fragments */
    private ItemViewModel viewModel;
    /* For access to HistoryScrollView */
    private ScrollViewWithBottomPadding scrollViewWithBottomPadding;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /* Create element of history */
    public AppCompatButton createButton(Integer ID, String result, String expression, Double resultDoulbe) {
        AppCompatButton btn = new AppCompatButton(getContext());
        btn.setBackgroundDrawable(ContextCompat.getDrawable(this.getContext(), R.drawable.invisible_background_button));
        btn.setTag("buttonHistory_" + ID.toString());
        btn.setText(result);
        btn.setTextSize(20);
        BinaryExpressionTree binaryExpressionTree = new BinaryExpressionTree(expression);
        btn.setOnClickListener(v -> {
            if (binaryExpressionTree.Evaluate() != null) {
                viewModel.setDataBinaryExpressionTreeValue(new BinaryExpressionTree(expression));
                viewModel.getDataMenuValue().getMenu().getItem(1).setEnabled(true);
            } else {
                viewModel.getDataMenuValue().getMenu().getItem(1).setEnabled(false);
                viewModel.setDataBinaryExpressionTreeValue(null);
            }
            viewModel.setDataCalculatorResultValue(expression);
            viewModel.setDataCalculatorResultDoubleValue(resultDoulbe);
            viewModel.getDataNavControllerValue().popBackStack();
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        params.setMargins(0, 0, 0, 20);
        btn.setPadding(15, 15, 15, 15);
        btn.setLayoutParams(params);
        return btn;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_history, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        Fragment currentFragment = this;
        /* Set toolbar (trash button) */
        Toolbar toolbar = fragmentView.findViewById(R.id.toolbarHistory);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.trash);
        /* Get database from ItemViewModel */
        SQLiteDatabase db = viewModel.getDataBaseHelperValue().getReadableDatabase();
        toolbar.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Очистка історії")
                        .setMessage("Ви впевнені?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Так", (dialog, whichButton) -> {
                            viewModel.getDataBaseHelperValue().clearDatabase();
                            getParentFragmentManager().beginTransaction().detach(currentFragment).attach(currentFragment).commit();
                        })
                        .setNegativeButton("Ні", null).show();
            }
        });
        /* Create elements of history */
        List<CalculatorModel> elements = viewModel.getDataBaseHelperValue().getEveryone();
        LinearLayout linearLayout = fragmentView.findViewById(R.id.linear_elements_layout);
        if (elements.size() > 0) {
            scrollViewWithBottomPadding = fragmentView.findViewById(R.id.scrollable_history);
            scrollViewWithBottomPadding.post(() -> scrollViewWithBottomPadding.fullScroll(View.FOCUS_DOWN));
            scrollViewWithBottomPadding.setBottomPadding(15);
            for (int i = 0; i < elements.size(); i++) {
                linearLayout.addView(createButton(elements.get(i).getCalculatorID(), elements.get(i).getResultString(), elements.get(i).getExpression(), elements.get(i).getResultDouble()));
            }
        } else {
            TextView emptyHistoryTextView = new TextView(getContext());
            emptyHistoryTextView.setText("Історія поки що пуста...");
            emptyHistoryTextView.setPadding(0, 300, 0, 0);
            emptyHistoryTextView.setTextSize(25);
            emptyHistoryTextView.setGravity(Gravity.CENTER);
            linearLayout.addView(emptyHistoryTextView);
        }
        return fragmentView;
    }
}