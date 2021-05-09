package com.example.extree.tree;

import static java.lang.Math.pow;

public class ExpressionNode implements IExpression {
    /* Main constructor */
    public ExpressionNode(IExpression left, IExpression right, Character op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    /* Getter left value */
    public IExpression getLeft() {
        return left;
    }

    /* Getter right value */
    public IExpression getRight() {
        return right;
    }

    /* Getter operation value */
    public Character getOp() {
        return op;
    }

    /* Method for calculating height of tree graph (how deep is it) */
    public int Height() {
        return Height(this);
    }

    /* Method for calculating height of tree graph (how deep is it) */
    private int Height(IExpression root) {
        if (root == null) {
            return 0;
        }
        if (root instanceof ExpressionNumber) return 0;
        int left = Height(((ExpressionNode) root).getLeft());
        int right = Height(((ExpressionNode) root).getRight());
        return left > right ? left + 1 : right + 1;
    }


    /* Calculating the value of an expression */
    @Override
    public Double Evaluate() {
        if (op != null)
            switch (op) {
                case '+':
                    return left.Evaluate() + right.Evaluate();
                case '-':
                    return left.Evaluate() - right.Evaluate();
                case '*':
                    return left.Evaluate() * right.Evaluate();
                case '/':
                    return left.Evaluate() / right.Evaluate();
                default:
                    return pow(left.Evaluate(), right.Evaluate());
            }
        return left.Evaluate();
    }

    /* Prefix form */
    @Override
    public String PreOrder() {
        if (op == null) return left.PreOrder();
        return op + " " + left.PreOrder() + " " + right.PreOrder();
    }

    /* Infix form */
    @Override
    public String SymmetricOrder() {
        if (op == null) return "(" + left.SymmetricOrder() + ")";
        return "( " + left.SymmetricOrder() + " " + op + " " + right.SymmetricOrder() + " )";
    }

    /* Postfix form */
    @Override
    public String PostOrder() {
        if (op == null) return left.PostOrder();
        return left.PostOrder() + " " + right.PostOrder() + " " + op;
    }

    /* Left Expression (may be Number or BinaryExpressionTree) */
    private final IExpression left;
    /* Right Expression (may be Number or BinaryExpressionTree) */
    private final IExpression right;
    /* Operation (may be: ['+', '-', '*', '/', '^']) */
    private final Character op;

}
