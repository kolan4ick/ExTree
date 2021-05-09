package com.example.extree.tree;

import java.util.ArrayList;

/* Class for binary expression trees */
public class BinaryExpressionTree {
    /* Main constructor */
    public BinaryExpressionTree(IExpression left, IExpression right, char op) {
        this.root = new ExpressionNode(left, right, op);
    }

    /* Constructor for converting an expression (in string form) to a binary expression tree */
    public BinaryExpressionTree(String expression) {
        IExpression tree = MakeBinaryExpressionTree(expression);
        if (tree instanceof ExpressionNumber) {
            this.root = new ExpressionNumber(((ExpressionNumber) tree).number);
        } else {
            assert tree != null;
            this.root = new ExpressionNode(((ExpressionNode) tree).getLeft(), ((ExpressionNode) tree).getRight(), ((ExpressionNode) tree).getOp());
        }
    }

    /* Getter root value */
    public IExpression getRoot() {
        return root;
    }

    /* Method for calculating height of tree graph (how deep is it) */
    public int Height() {
        if (root instanceof ExpressionNumber) return 0;
        else return ((ExpressionNode) root).Height();
    }

    /* Calculating the value of an expression */
    public Double Evaluate() {
        return root.Evaluate();
    }

    /* Prefix form */
    public String PreOrder() {
        return root.PreOrder();
    }

    /* Infix form */
    public String SymmetricOrder() {
        return root.SymmetricOrder();
    }

    /* Postfix form */
    public String PostOrder() {
        return root.PostOrder();
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
        return flag || (open > 0) || !wasNumberBetweenBrackets;
    }

    /* Method for converting a string expression into an array of objects */
    private ArrayList<Object> StringIntoTheArrayObject(String expression) {
        char[] expr = expression.replace(',', '.').toCharArray();
        StringBuilder num = new StringBuilder();
        ArrayList<Object> arrayList = new ArrayList<>();
        for (int i = 0; i < expr.length; ++i) {
            if (!((expr[i] >= 48 && expr[i] <= 57) || expr[i] == '.' || (i > 0 && expr[i] == '-' && expr[i - 1] == '(') || (expr[i] == 'E' || expr[i] == 'e'))) {
                if (num.length() > 0) {
                    arrayList.add(new ExpressionNumber(Double.parseDouble(num.toString())));
                    if (arrayList.size() > 2 && arrayList.get(arrayList.size() - 1) instanceof IExpression && arrayList.get(arrayList.size() - 2) instanceof IExpression)
                        arrayList.add(arrayList.size() - 1, '*');
                }
                arrayList.add(expr[i]);
                if (arrayList.size() > 2 && (arrayList.get(arrayList.size() - 3).equals('(') && arrayList.get(arrayList.size() - 2) instanceof ExpressionNumber && arrayList.get(arrayList.size() - 1).equals(')'))) {
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
    private void CreateExpressionNode(ArrayList<Object> arrayList, int index) {
        arrayList.set(index, new ExpressionNode((IExpression) arrayList.get(index - 1), (IExpression) arrayList.get(index + 1), (Character) arrayList.get(index)));
        arrayList.remove(index - 1);
        arrayList.remove(index);
        if (arrayList.get(index - 2).equals('(') && arrayList.get(index).equals(')')) {
            arrayList.remove(index - 2);
            arrayList.remove(index - 1);
        }
    }

    /* Main method for creating binary expression tree from string expression */
    private IExpression MakeBinaryExpressionTree(String expression) {
        if (CorrectBracketSequence(expression)) {
            if (CorrectBracketSequence(expression + ")")) return null;
            else expression += ")";
        }
        expression = "(" + expression;
        expression += ")";
        ArrayList<Object> arrayList = StringIntoTheArrayObject(expression);
        int i = 0;
        while (arrayList.size() > 1) {
            if (i >= arrayList.size()) i = 0;
            if (arrayList.get(i).equals('^') && HasNoBetterVariantForExponentiation(arrayList, i)) {
                CreateExpressionNode(arrayList, i);
                i--;
            }
            if (arrayList.size() > 1)
                if ((arrayList.get(i).equals('*') || arrayList.get(i).equals('/')) && HasNoBetterVariantForMultiplicationDivision(arrayList, i)) {
                    CreateExpressionNode(arrayList, i);
                    i--;
                }
            if (arrayList.size() > 1)
                if ((arrayList.get(i).equals('+') || arrayList.get(i).equals('-')) && HasNoBetterVariantForAdditionSubtraction(arrayList, i)) {
                    CreateExpressionNode(arrayList, i);
                    i--;
                }
            i++;
        }
        return (IExpression) arrayList.get(0);
    }

    /* Root element of graph, may be ExpressionNode and ExpressionNumber (because tree may contains just one element) */
    public IExpression root;
}
