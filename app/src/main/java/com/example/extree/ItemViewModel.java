package com.example.extree;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.example.extree.database.DatabaseHelper;
import com.example.extree.tree.BinaryExpressionTree;

public class ItemViewModel extends ViewModel {
    /* Previous String result of calculator (used for saving state of calculator when moving to other parts of BottomNavigationView) */
    private final MutableLiveData<String> dataCalculatorResult;
    /* Previous Double result of calculator (used for saving state of calculator when moving to other parts of BottomNavigationView) */
    private final MutableLiveData<Double> dataCalculatorResultDouble;
    /* Previous BinaryExpressionTree result of calculator (used for saving state of calculator when moving to other parts of BottomNavigationView) */
    private final MutableLiveData<BinaryExpressionTree> dataBinaryExpressionTree;
    /* Previous Height of Menu (used in other parts of BottomNavigationView) */
    private final MutableLiveData<Integer> dataMenuHeight;
    private final MutableLiveData<DatabaseHelper> dataBaseHelper;
    private final MutableLiveData<NavController> dataNavController;

    /* Main constructor */
    public ItemViewModel() {
        dataCalculatorResult = new MutableLiveData<>();
        dataCalculatorResultDouble = new MutableLiveData<>();
        dataBinaryExpressionTree = new MutableLiveData<>();
        dataMenuHeight = new MutableLiveData<>();
        dataBaseHelper = new MutableLiveData<>();
        this.dataNavController = new MutableLiveData<>();
    }

    /* Getter of dataCalculatorResult */
    public MutableLiveData<String> getDataCalculatorResult() {
        return dataCalculatorResult;
    }

    /* Getter of dataCalculatorResultValue */
    public String getDataCalculatorResultValue() {
        return dataCalculatorResult.getValue();
    }

    /* Setter of dataCalculatorResultValue */
    public void setDataCalculatorResultValue(String value) {
        dataCalculatorResult.setValue(value);
    }

    /* Getter of dataCalculatorResultDouble */
    public MutableLiveData<Double> getDataCalculatorResultDouble() {
        return dataCalculatorResultDouble;
    }

    /* Getter of dataCalculatorResultDoubleValue */
    public Double getDataCalculatorResultDoubleValue() {
        return dataCalculatorResultDouble.getValue();
    }

    /* Setter of dataCalculatorResultDoubleValue */
    public void setDataCalculatorResultDoubleValue(Double value) {
        dataCalculatorResultDouble.setValue(value);
    }

    /* Getter of dataBinaryExpressionTree */
    public MutableLiveData<BinaryExpressionTree> getDataBinaryExpressionTree() {
        return dataBinaryExpressionTree;
    }

    /* Getter of dataBinaryExpressionTreeValue */
    public BinaryExpressionTree getDataBinaryExpressionTreeValue() {
        return dataBinaryExpressionTree.getValue();
    }

    /* Setter of dataBinaryExpressionTree */
    public void setDataBinaryExpressionTreeValue(BinaryExpressionTree data) {
        dataBinaryExpressionTree.setValue(data);
    }

    /* Getter of dataMenuHeight */
    public MutableLiveData<Integer> getDataMenuHeight() {
        return dataMenuHeight;
    }

    /* Getter of dataMenuHeightValue */
    public Integer getDataMenuHeightValue() {
        return dataMenuHeight.getValue();
    }

    /* Setter of dataMenuHeightValue */
    public void setDataMenuHeightValue(Integer value) {
        dataMenuHeight.setValue(value);
    }

    public MutableLiveData<DatabaseHelper> getDataBaseHelper() {
        return dataBaseHelper;
    }

    public DatabaseHelper getDataBaseHelperValue() {
        return dataBaseHelper.getValue();
    }

    public void setDataBaseHelperValue(DatabaseHelper value) {
        dataBaseHelper.setValue(value);
    }

    /* Getter of dataNavController */
    public MutableLiveData<NavController> getDataNavController() {
        return dataNavController;
    }

    public NavController getDataNavControllerValue() {
        return dataNavController.getValue();
    }

    public void setDataNavControllerValue(NavController value) {
        dataNavController.setValue(value);
    }

}
