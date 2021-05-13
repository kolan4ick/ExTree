package com.example.extree;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.extree.database.CalculatorModel;
import com.example.extree.tree.BinaryExpressionTree;
import com.example.extree.tree_draw.HistoryScrollView;

import java.util.List;

public class HistoryFragment extends Fragment {
    /* This view */
    private View fragmentView;
    /* ItemViewModel for connection between fragments */
    private ItemViewModel viewModel;
    /* For access to HistoryScrollView */
    private HistoryScrollView historyScrollView;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public AppCompatButton createButton(Integer ID, String result, String expression, Double resultDoulbe) {
        AppCompatButton btn = new AppCompatButton(getContext());
        btn.setBackgroundDrawable(ContextCompat.getDrawable(this.getContext(), R.drawable.invisible_background_button));
        btn.setTag("buttonHistory_" + ID.toString());
        btn.setText(result);
        btn.setTextSize(20);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setDataBinaryExpressionTreeValue(new BinaryExpressionTree(expression));
                viewModel.setDataCalculatorResultValue(result);
                viewModel.setDataCalculatorResultDoubleValue(resultDoulbe);
                viewModel.getDataNavControllerValue().navigate(R.id.calculatorFragment);
            }
        });
        return btn;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_history, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        SQLiteDatabase db = viewModel.getDataBaseHelperValue().getReadableDatabase();
        Toast.makeText(getContext(), db.toString(), Toast.LENGTH_SHORT).show();
        List<CalculatorModel> elements = viewModel.getDataBaseHelperValue().getEveryone();
        LinearLayout linearLayout = fragmentView.findViewById(R.id.linear_elements_layout);
        historyScrollView = fragmentView.findViewById(R.id.scrollable_history);
        historyScrollView.post(new Runnable() {
            public void run() {
                historyScrollView.scrollTo(0, historyScrollView.getBottom());
            }
        });
        historyScrollView.setHeightOfMenu(viewModel.getDataMenuHeightValue());
        for (int i = 0; i < elements.size(); i++) {
            linearLayout.addView(createButton(elements.get(i).getCalculatorID(), elements.get(i).getResultString(), elements.get(i).getExpression(), elements.get(i).getResultDouble()));
        }
        return fragmentView;
    }
}