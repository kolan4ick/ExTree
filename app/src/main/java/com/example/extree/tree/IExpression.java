package com.example.extree.tree;

/* Interface for all expression-based classes */
public interface IExpression {
    Double Evaluate();

    String PreOrder();

    String SymmetricOrder();

    String PostOrder();
}
