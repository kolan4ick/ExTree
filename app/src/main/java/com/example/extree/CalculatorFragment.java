package com.example.extree;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.extree.database.CalculatorModel;
import com.example.extree.tree.BinaryExpressionTree;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;

public class CalculatorFragment extends Fragment {
    /* This view */
    private View fragmentView;
    /* ItemViewModel for connection between fragments */
    private ItemViewModel viewModel;
    /* Possible operations */
    private final ArrayList<Character> operations = new ArrayList<>(Arrays.asList('+', '-', '*', '/', '^'));
    /* Result of expression */
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
        AppCompatButton btnTree = fragmentView.findViewById(R.id.buttonGenerateTree);
        AppCompatButton btnOne = fragmentView.findViewById(R.id.buttonOne);
        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        if (viewModel.getDataCalculatorResultValue() != null) {
            ((TextView) (fragmentView.findViewById(R.id.textView))).setText(viewModel.getDataCalculatorResultValue());
            result = viewModel.getDataCalculatorResultDoubleValue();
        }
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        ((TextView) fragmentView.findViewById(R.id.textView)).setHeight((int) (height - ((Util.pxFromDp(getContext(), (int) (75 * 6.7))))));
        ((TextView) fragmentView.findViewById(R.id.textView)).setWidth((int) (width / 1.2));
        ((TextView) fragmentView.findViewById(R.id.textView)).setMovementMethod(new ScrollingMovementMethod());
        androidx.appcompat.widget.Toolbar toolbar = fragmentView.findViewById(R.id.toolbarCalculator);
        /* Adding action after click on Settings item in menu */
        toolbar.getMenu().getItem(0).setOnMenuItemClickListener(item -> {
            viewModel.getDataNavControllerValue().navigate(R.id.settingsFragment);
            return true;
        });
        /* Adding action after click on About item in menu */
        toolbar.getMenu().getItem(1).setOnMenuItemClickListener(item -> {
            viewModel.getDataNavControllerValue().navigate(R.id.aboutFragment);
            return true;
        });
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
                if (!resultText.getText().toString().contains("=")) {
                    BinaryExpressionTree binaryExpressionTree = new BinaryExpressionTree(resultText.getText().toString());
                    String expression = resultText.getText().toString();
                    resultText.append("\n=" + (result = binaryExpressionTree.Evaluate()).toString());
                    viewModel.setDataBinaryExpressionTreeValue(binaryExpressionTree);
                    Boolean insert = viewModel.getDataBaseHelperValue().addOne(new CalculatorModel(expression, resultText.getText().toString(), result));
                    Toast.makeText(getContext(), insert.toString(), Toast.LENGTH_SHORT).show();
                }
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
        viewModel.setDataCalculatorResultValue(resultText.getText().toString());
        viewModel.setDataCalculatorResultDoubleValue(result);
    }
}