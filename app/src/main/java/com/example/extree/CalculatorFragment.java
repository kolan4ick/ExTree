package com.example.extree;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.extree.tree.BinaryExpressionTree;
import com.example.extree.tree_draw.BinaryExpressionTreeView;

import java.util.ArrayList;
import java.util.Arrays;

public class CalculatorFragment extends Fragment {
    /* ?? */
    private View fragmentView;
    /* ?? */
    private final ArrayList<Character> operations = new ArrayList<>(Arrays.asList('+', '-', '*', '/', '^'));
    /* ?? */
    private Double result = 0.0;

    public CalculatorFragment() {
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
        fragmentView = inflater.inflate(R.layout.fragment_calculator, container, false);
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        ((TextView) fragmentView.findViewById(R.id.textView)).setHeight((int) (height / 2.5));
        ((TextView) fragmentView.findViewById(R.id.textView)).setWidth((int) (width / 1.2));
        return fragmentView;
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    public void onClickButtonCalculator(View view) {
        Button pressedButton = (Button) view;
        CharSequence pressedButtonText = pressedButton.getText();
        TextView resultText = fragmentView.findViewById(R.id.textView);
        switch (view.getId()) {
            case R.id.buttonAllClear:
                resultText.setText("0");
                break;
            case R.id.buttonErase:
                if (!resultText.getText().toString().contains("="))
                    if (resultText.length() == 1) resultText.setText("0");
                    else
                        resultText.setText(resultText.getText().subSequence(0, resultText.length() - 1));
                break;
            case R.id.buttonResult:
                if (!resultText.getText().toString().contains("="))
                    resultText.append("\n=" + (result = new BinaryExpressionTree(resultText.getText().toString()).Evaluate()).toString());
                break;
            case R.id.buttonGenerateTree:
                break;
            case R.id.buttonDot:
                if (!resultText.getText().toString().contains("=")) {
                    for (int i = resultText.length() - 1; i >= 0; --i) {
                        if (resultText.getText().charAt(i) == '.') break;
                        if (operations.contains(resultText.getText().charAt(i)) || i == 0) {
                            resultText.append(".");
                            break;
                        }
                    }
                } else resultText.setText("0.");
                break;
            default:
                if (!resultText.getText().toString().contains("=")) {
                    if (operations.contains(pressedButtonText.charAt(0))) {
                        if (operations.contains(resultText.getText().charAt(resultText.length() - 1)))
                            resultText.setText(resultText.getText().subSequence(0, resultText.length() - 1));
                        resultText.append(pressedButtonText);
                    } else {
                        if (resultText.length() == 1 && resultText.getText().charAt(0) == '0')
                            resultText.setText(pressedButtonText);
                        else
                            resultText.append(pressedButtonText);
                    }
                } else {
                    try {

                        if (operations.contains(pressedButtonText.charAt(0))) {
                            resultText.setText(result.toString());
                            resultText.append(pressedButtonText);
                        } else {
                            resultText.setText(pressedButtonText);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        }
    }
}