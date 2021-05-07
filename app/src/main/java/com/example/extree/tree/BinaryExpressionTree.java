package com.example.extree.tree;

import java.util.ArrayList;

import static java.lang.Math.pow;

/* Class for binary expression trees */
public class BinaryExpressionTree implements IExpression {
    /* Main constructor */
    public BinaryExpressionTree(IExpression left, IExpression right, char op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    /* Constructor for converting an expression (in string form) to a binary expression tree */
    public BinaryExpressionTree(String expression) {
        IExpression tree = MakeBinaryExpressionTree(expression);
        if (tree instanceof Number) {
            this.left = tree;
            this.right = null;
            this.op = null;
        } else {
            assert tree != null;
            this.left = ((BinaryExpressionTree) tree).left;
            this.right = ((BinaryExpressionTree) tree).right;
            this.op = ((BinaryExpressionTree) tree).op;
        }
    }

    /* Calculating the value of an expression (recursion) */
    @Override
    public Double Evaluate() {
        if (op == null) return left.Evaluate();
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

    /* Method for checking if there is a no better way to create a tree for addition and subtraction */
    private boolean HasNoBetterVariantForAdditionSubtraction(ArrayList<Object> arrayList, int i) {
        return (!arrayList.get(i - 1).equals(')') && !arrayList.get(i + 1).equals('(')) &&
                (!arrayList.get(i + 2).equals('*') && !arrayList.get(i + 2).equals('/')) &&
                (!arrayList.get(i - 2).equals('*') && !arrayList.get(i - 2).equals('/')) &&
                (!arrayList.get(i + 2).equals('^') && !arrayList.get(i - 2).equals('^'));
    }

    /* Method for checking if there is a no better way to create a tree for multiplication and division */
    private boolean HasNoBetterVariantForMultiplicationDivision(ArrayList<Object> arrayList, int i) {
        return (!arrayList.get(i - 1).equals(')') && !arrayList.get(i + 1).equals('(')) &&
                (!arrayList.get(i + 2).equals('^') && !arrayList.get(i - 2).equals('^'));
    }

    /* Method for checking if there is a no better way to create a tree for exponentiation */
    private boolean HasNoBetterVariantForExponentiation(ArrayList<Object> arrayList, int i) {
        return (!arrayList.get(i + 2).equals('^')) && (!arrayList.get(i - 1).equals(')') && !arrayList.get(i + 1).equals('('));
    }

    /* Method for checking the parentheses */
    private boolean CorrectBracketSequence(String expression) {
        if (expression.length() == 0) return false;
        boolean flag = false;
        boolean wasNumberBetweenBrackets = true;
        int open = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(')
                open++;
            else if (expression.charAt(i) == ')') {
                open--;
                if (expression.charAt(i - 1) == '(') wasNumberBetweenBrackets = false;
            } else
                continue;
            if (open < 0)
                flag = true;
        }
        return !(flag || (open > 0) || !wasNumberBetweenBrackets);
    }

    /* Method for converting a string expression into an array of objects */
    private ArrayList<Object> StringIntoTheArrayObject(String expression) {
        char[] expr = expression.replace(',', '.').toCharArray();
        StringBuilder num = new StringBuilder();
        ArrayList<Object> arrayList = new ArrayList<>();
        for (int i = 0; i < expr.length; ++i) {
            if (!((expr[i] >= 48 && expr[i] <= 57) || expr[i] == '.' || (i > 0 && expr[i] == '-' && !(expr[i - 1] >= 48 && expr[i - 1] <= 57)) || (expr[i] == 'E' || expr[i] == 'e'))) {
                if (num.length() > 0) {
                    arrayList.add(new Number(Double.parseDouble(num.toString())));
                    if (arrayList.size() > 2 && arrayList.get(arrayList.size() - 1) instanceof IExpression && arrayList.get(arrayList.size() - 2) instanceof IExpression)
                        arrayList.add(arrayList.size() - 1, '*');
                }
                arrayList.add(expr[i]);
                if (arrayList.size() > 2 && (arrayList.get(arrayList.size() - 3).equals('(') && arrayList.get(arrayList.size() - 2) instanceof Number && arrayList.get(arrayList.size() - 1).equals(')'))) {
                    arrayList.remove(arrayList.size() - 3);
                    arrayList.remove(arrayList.size() - 1);
                }
                if (arrayList.size() > 2 && arrayList.get(arrayList.size() - 1) instanceof IExpression && arrayList.get(arrayList.size() - 2) instanceof IExpression)
                    arrayList.add(arrayList.size() - 1, '*');
                num = new StringBuilder();
            } else num.append(expr[i]);
        }
        return arrayList;
    }

    /* Method for creating a binary expression tree */
    private void CreateBinaryExpressionTree(ArrayList<Object> arrayList, int index) {
        arrayList.set(index, new BinaryExpressionTree((IExpression) arrayList.get(index - 1), (IExpression) arrayList.get(index + 1), (Character) arrayList.get(index)));
        arrayList.remove(index - 1);
        arrayList.remove(index);
        if (arrayList.get(index - 2).equals('(') && arrayList.get(index).equals(')')) {
            arrayList.remove(index - 2);
            arrayList.remove(index - 1);
        }
    }

    /* Main method for creating binary expression tree from string expression */
    private IExpression MakeBinaryExpressionTree(String expression) {
        if (!CorrectBracketSequence(expression)) return null;
        expression = "(" + expression;
        expression += ")";
        ArrayList<Object> arrayList = StringIntoTheArrayObject(expression);
        int i = 0;
        while (arrayList.size() > 1) {
            if (i >= arrayList.size()) i = 0;
            if (arrayList.get(i).equals('^') && HasNoBetterVariantForExponentiation(arrayList, i)) {
                CreateBinaryExpressionTree(arrayList, i);
                i--;
            }
            if (arrayList.size() > 1)
                if ((arrayList.get(i).equals('*') || arrayList.get(i).equals('/')) && HasNoBetterVariantForMultiplicationDivision(arrayList, i)) {
                    CreateBinaryExpressionTree(arrayList, i);
                    i--;
                }
            if (arrayList.size() > 1)
                if ((arrayList.get(i).equals('+') || arrayList.get(i).equals('-')) && HasNoBetterVariantForAdditionSubtraction(arrayList, i)) {
                    CreateBinaryExpressionTree(arrayList, i);
                    i--;
                }
            i++;
        }
        return (IExpression) arrayList.get(0);
    }


    /* Left Expression (may be Number or BinaryExpressionTree) */
    private final IExpression left;
    /* Right Expression (may be Number or BinaryExpressionTree) */
    private final IExpression right;
    /* Operation (may be: ['+', '-', '*', '/', '^']) */
    private final Character op;
}
