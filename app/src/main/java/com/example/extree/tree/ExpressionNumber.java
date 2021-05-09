package com.example.extree.tree;

public class ExpressionNumber implements IExpression {
    /* Main constructor */
    public ExpressionNumber(Double number) {
        this.number = number;
    }

    /* Method for calculate value, in this case this is number */
    @Override
    public Double Evaluate() {
        return number;
    }

    /* Method for prefix form, in this case this is number */
    @Override
    public String PreOrder() {
        return number.toString();
    }

    /* Method for infix form, in this case this is number */
    @Override
    public String SymmetricOrder() {
        return number.toString();
    }

    /* Method for postfix form, in this case this is number */
    @Override
    public String PostOrder() {
        return number.toString();
    }

    /* Number */
    public Double number;
}
