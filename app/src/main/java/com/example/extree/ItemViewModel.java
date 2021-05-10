package com.example.extree;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.extree.tree.BinaryExpressionTree;

public class ItemViewModel extends ViewModel {
    private final MutableLiveData<String> dataCalculatorResult;//Дані на які можна підписатись і отримувати сповіщеня про їх зміну(не обовязково, але в цьому суть)
    private final MutableLiveData<Double> dataCalculatorResultDouble;//Дані на які можна підписатись і отримувати сповіщеня про їх зміну(не обовязково, але в цьому суть)
    private final MutableLiveData<BinaryExpressionTree> dataBinaryExpressionTree;//Дані на які можна підписатись і отримувати сповіщеня про їх зміну(не обовязково, але в цьому суть)


    public ItemViewModel() {
        dataCalculatorResult = new MutableLiveData<>();//можна обявити одразу як було, але мені так ззручніше
        dataCalculatorResultDouble = new MutableLiveData<>();
        dataBinaryExpressionTree = new MutableLiveData<>();
    }

    public void setDataCalculatorResultValue(String value) {
        dataCalculatorResult.setValue(value);
    }

    public MutableLiveData<BinaryExpressionTree> getDataBinaryExpressionTree() {
        return dataBinaryExpressionTree;
    }

    public BinaryExpressionTree getDataBinaryExpressionTreeValue() {
        return dataBinaryExpressionTree.getValue();
    }

    public void setDataBinaryExpressionTreeValue(BinaryExpressionTree data) {
        dataBinaryExpressionTree.setValue(data);
    }

    public MutableLiveData<String> getDataCalculatorResult() {
        return dataCalculatorResult;
    }

    public String getDataCalculatorResultValue() {// Тут mozna prosto otrymaty statuchni dani jaki e v ViewModel (budi jaku zminnu)
        return dataCalculatorResult.getValue();
    }

    public void setDataCalculatorResultDoubleValue(Double value) {
        dataCalculatorResultDouble.setValue(value);
    }

    public MutableLiveData<Double> getDataCalculatorResultDouble() {
        return dataCalculatorResultDouble;
    }

    public Double getDataCalculatorResultDoubleValue() {// Тут mozna prosto otrymaty statuchni dani jaki e v ViewModel (budi jaku zminnu)
        return dataCalculatorResultDouble.getValue();
    }

}
