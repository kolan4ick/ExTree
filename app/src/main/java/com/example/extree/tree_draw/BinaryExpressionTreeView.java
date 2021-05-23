package com.example.extree.tree_draw;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import android.annotation.SuppressLint;
import android.view.ScaleGestureDetector;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintSet;

import com.example.extree.ItemViewModel;
import com.example.extree.R;
import com.example.extree.Util;
import com.example.extree.tree.BinaryExpressionTree;
import com.example.extree.tree.ExpressionNode;
import com.example.extree.tree.ExpressionNumber;
import com.example.extree.tree.IExpression;

public class BinaryExpressionTreeView extends View implements Animator.AnimatorListener {
    /* Main constructor */
    public BinaryExpressionTreeView(Context context) {
        this(context, null);
    }

    /* Constructor with attributes */
    public BinaryExpressionTreeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /* Constructor with arguments and styleAttributes*/
    public BinaryExpressionTreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        init();
    }

    /* Setter binaryExpressionTree value */
    public void setBinaryExpressionTree(BinaryExpressionTree binaryExpressionTree) {
        this.binaryExpressionTree = binaryExpressionTree;
    }

    /* Method for initializing main values */
    private void init() {

        commonColor = getResources().getColor(R.color.app_common_color);
        traversalColor = getResources().getColor(R.color.home_nav_sort_color);

        textSize = (int) Util.pxFromDp(getContext(), prefs.getInt("fontSizeSettingsValue", 14));
        mCircleRadius = (int) Util.pxFromDp(getContext(), prefs.getInt("circleRadiusSettingsValue", 24));
        topAndBottomOffset = (int) Util.pxFromDp(getContext(), prefs.getInt("topAndBottomOffsetSettingsValue", 25));
        xGap = (int) Util.pxFromDp(getContext(), prefs.getInt("widthSettingsValue", 14));
        yGap = (int) Util.pxFromDp(getContext(), prefs.getInt("heightSettingsValue", 14));

        initPaint();
        initAnimator();
    }

    /* Method for initializing main values for animation */
    private void initAnimator() {
        mValueAnimator = ValueAnimator.ofInt(0, 255);
        mValueAnimator.setDuration((int) (800 / (double) prefs.getFloat("animationDurationSettingsValue", 1f)));
        mValueAnimator.addUpdateListener(animation -> {
            alpha = (int) animation.getAnimatedValue();
            invalidate();
        });
        mValueAnimator.addListener(this);
    }

    /* Method for initializing main values for painting */
    private void initPaint() {
        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(getResources().getColor(R.color.app_common_color));

        mCircleFillPaint = new Paint();
        mCircleFillPaint.setStyle(Paint.Style.FILL);
        mCircleFillPaint.setColor(getResources().getColor(R.color.app_common_color));

        mCircleStrokePaint = new Paint();
        mCircleStrokePaint.setStyle(Paint.Style.STROKE);
        mCircleStrokePaint.setStrokeWidth(2);
        mCircleStrokePaint.setAntiAlias(true);
        mCircleStrokePaint.setColor(getResources().getColor(R.color.dark_blue));
        mCircleStrokePaint.setStrokeCap(Paint.Cap.ROUND);

        mTextPaint = new Paint();
        mTextPaint.setColor(getResources().getColor(R.color.TextColor));
        mTextPaint.setTextSize(textSize);
    }

    /* Main method of View extended class. Drawing the graph */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.scale(mScaleFactor, mScaleFactor);
        if (binaryExpressionTree == null) {
            return;
        }
        step = 0;
        if (state == STATE_NORMAL) {
            drawTree(canvas, binaryExpressionTree, mWidth / 2, mCircleRadius + topAndBottomOffset);
            if (firstLaunch) {
                layout(-(mWidth / 2) + (minimumViewWidth / 2), getTop(), (mWidth / 2) + (minimumViewWidth / 2), getBottom());
                firstLaunch = !firstLaunch;
            }
        } else if (state == STATE_PRE_ORDER_TRAVERSAL) {
            preOrderTraversal(canvas, binaryExpressionTree.getRoot(), mWidth / 2, mCircleRadius + topAndBottomOffset, 0, 0);
        } else if (state == STATE_IN_ORDER_TRAVERSAL) {
            inOrderTraversal(canvas, binaryExpressionTree.getRoot(), mWidth / 2, mCircleRadius + topAndBottomOffset, 0, 0);
        } else if (state == STATE_POST_ORDER_TRAVERSAL) {
            postOrderTraversal(canvas, binaryExpressionTree.getRoot(), mWidth / 2, mCircleRadius + topAndBottomOffset, 0, 0);
        }
    }

    /* Main drawing-tree method */
    private void drawTree(Canvas canvas, BinaryExpressionTree binaryExpressionTree, int x, int y) {
        mCircleFillPaint.setColor(commonColor);
        if (binaryExpressionTree.getRoot() instanceof ExpressionNumber)
            drawNumber(canvas, (ExpressionNumber) binaryExpressionTree.getRoot(), x, y, 0, 0);
        else drawNode(canvas, (ExpressionNode) binaryExpressionTree.getRoot(), x, y, 0, 0);
    }

    /* Method for draw number */
    private void drawNumber(Canvas canvas, ExpressionNumber expressionNumber, int x, int y, int parentX, int parentY) {
        if (parentX != 0 && parentY != 0) {
            drawLine(canvas, parentX, parentY, x, y);
        }
        canvas.drawCircle(x, y, mCircleRadius, mCircleFillPaint);
        canvas.drawCircle(x, y, mCircleRadius, mCircleStrokePaint);
        String text = expressionNumber.number.toString();
        Rect rect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        canvas.drawText(text, x - rect.width() / 2, y + rect.height() / 2, mTextPaint);
    }

    /* Method for draw tree node */
    private void drawNode(Canvas canvas, ExpressionNode expressionNode, int x, int y, int parentX, int parentY) {
        if (parentX != 0 && parentY != 0) {
            drawLine(canvas, parentX, parentY, x, y);
        }
        int xOffset = (int) Math.pow(2, (expressionNode.Height() - 1)) * (mCircleRadius + xGap);
        int yOffset = yGap + (mCircleRadius * 2);
        canvas.drawCircle(x, y, mCircleRadius, mCircleFillPaint);
        canvas.drawCircle(x, y, mCircleRadius, mCircleStrokePaint);
        String text = expressionNode.getOp().toString();
        Rect rect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        canvas.drawText(text, x - rect.width() / 2, y + rect.height() / 2, mTextPaint);
        if (expressionNode.getLeft() instanceof ExpressionNumber) {
            drawNumber(canvas, (ExpressionNumber) expressionNode.getLeft(), x - xOffset, y + yOffset, x, y);
        } else {
            drawNode(canvas, (ExpressionNode) expressionNode.getLeft(), x - xOffset, y + yOffset, x, y);
        }
        if (expressionNode.getRight() instanceof ExpressionNumber) {
            drawNumber(canvas, (ExpressionNumber) expressionNode.getRight(), x + xOffset, y + yOffset, x, y);
        } else {
            drawNode(canvas, (ExpressionNode) expressionNode.getRight(), x + xOffset, y + yOffset, x, y);
        }
    }

    /* Method for drawing lines for connection nodes */
    private void drawLine(Canvas canvas, int px, int py, int x, int y) {
        mLinePaint.setColor(commonColor);

        int difX = x - px;
        int difY = y - py;
        int distance = (int) Math.sqrt(Math.pow(difX, 2) + Math.pow(difY, 2));
        int offsetX = difX * mCircleRadius / distance;
        int offsetY = difY * mCircleRadius / distance;
        canvas.drawLine(px + offsetX, py + offsetY, x - offsetX, y - offsetY, mLinePaint);
    }

    /* Method for automatic selection of view size */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (binaryExpressionTree != null) {
            int treeHeight = binaryExpressionTree.Height();
            int maxLeafCount = (int) Math.pow(2, treeHeight);
            mWidth = mCircleRadius * 2 * maxLeafCount + 2 * xGap * (maxLeafCount + 1);
            mWidth = Math.max(mWidth, minimumViewWidth) * 6;
            mHeight = minimumViewHeight;
            setMeasuredDimension(mWidth, mHeight);
        }
    }

    /* Method for drag and drop View (just x coordinates) */
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                break;

            case MotionEvent.ACTION_MOVE:
                /* If action move was with two fingers (gesture scaling) */
                mScaleDetector.onTouchEvent(event);
                int offsetX = x - lastX;
                layout(getLeft() + offsetX, getTop(), getRight() + offsetX, getBottom());
                break;
        }

        return true;
    }

    /* Class for scaling view */
    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

            invalidate();
            return true;
        }
    }

    /* Event listener animation starting */
    @Override
    public void onAnimationStart(Animator animation) {
    }

    /* Event listener animation ending */
    @Override
    public void onAnimationEnd(Animator animation) {
        if (mAnimEndListener != null) {
            mAnimEndListener.animEnd(this);
        }

    }

    /* Event listener animation canceling */
    @Override
    public void onAnimationCancel(Animator animation) {

    }

    /* Event listener animation repeating */
    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    /* Setter for animEndListener */
    public void setAnimEndListener(AnimEndListener animEndListener) {
        mAnimEndListener = animEndListener;
    }


    public void Next() {
        int count = binaryExpressionTree.getNodeCount();
        if (stepLimit > count && state != STATE_NORMAL) {
            Toast.makeText(getContext(), "someTextAfterAnimation", Toast.LENGTH_SHORT).show();
            return;
        }
        stepLimit++;
        mValueAnimator.start();
    }

    /* Setter for animation duration */
    public void setAnimDuring(int during) {
        mValueAnimator.setDuration(during);
    }

    /* Getter stepLimit */
    public int getStepLimit() {
        return stepLimit;
    }

    /* Method for begin prefix tree traversal */
    public void beginPreOrderTraversal() {
        stepLimit = 1;
        state = STATE_PRE_ORDER_TRAVERSAL;
        mValueAnimator.start();

    }

    /* Method for begin infix tree traversal */
    public void beginInOrderTraversal() {
        stepLimit = 1;
        state = STATE_IN_ORDER_TRAVERSAL;
        mValueAnimator.start();

    }

    /* Method for begin postfix tree traversal */
    public void beginPostOrderTraversal() {
        stepLimit = 1;
        state = STATE_POST_ORDER_TRAVERSAL;
        mValueAnimator.start();

    }

    public void beginClear() {
        state = STATE_NORMAL;
        this.invalidate();
    }

    /* Method for animated prefix tree traversal */
    private void preOrderTraversal(Canvas canvas, IExpression expression, int x, int y, int parentX, int parentY) {
        if (step >= stepLimit) {
            return;
        }
        if (parentX != 0 && parentY != 0) {
            traversalLine(canvas, parentX, parentY, x, y);
        }
        int xOffset = (int) Math.pow(2, (expression.Height() - 1)) * (mCircleRadius + xGap);
        int yOffset = yGap + (mCircleRadius * 2);
        if (expression instanceof ExpressionNode) {
            mCircleFillPaint.setColor(traversalColor);
            if (step == stepLimit - 1) {
                mCircleFillPaint.setAlpha(alpha);
            } else {
                mCircleFillPaint.setAlpha(255);
            }
            canvas.drawCircle(x, y, mCircleRadius, mCircleFillPaint);
            canvas.drawCircle(x, y, mCircleRadius, mCircleStrokePaint);
            String text = ((ExpressionNode) expression).getOp().toString();
            Rect rect = new Rect();
            mTextPaint.getTextBounds(text, 0, text.length(), rect);
            canvas.drawText(text, x - rect.width() / 2, y + rect.height() / 2, mTextPaint);
        } else traversalNumber(canvas, ((ExpressionNumber) expression), x, y, parentX, parentY);
        step++;

        if (expression instanceof ExpressionNode) {
            if (((ExpressionNode) expression).getLeft() != null) {
                preOrderTraversal(canvas, ((ExpressionNode) expression).getLeft(), x - xOffset, y + yOffset, x, y);
            }

            if (((ExpressionNode) expression).getRight() != null) {
                preOrderTraversal(canvas, ((ExpressionNode) expression).getRight(), x + xOffset, y + yOffset, x, y);
            }
        }
    }

    /* Method for animated infix tree traversal */
    private void inOrderTraversal(Canvas canvas, IExpression expression, int x, int y, int parentX, int parentY) {
        int xOffset = (int) Math.pow(2, (expression.Height() - 1)) * (mCircleRadius + xGap);
        int yOffset = yGap + (mCircleRadius * 2);
        if (expression instanceof ExpressionNode) {
            if (((ExpressionNode) expression).getLeft() != null) {
                inOrderTraversal(canvas, ((ExpressionNode) expression).getLeft(), x - xOffset, y + yOffset, x, y);
            }
        }
        if (step >= stepLimit) {
            return;
        }
        if (parentX != 0 && parentY != 0) {
            traversalLine(canvas, parentX, parentY, x, y);
        }
        if (expression instanceof ExpressionNode) {
            mCircleFillPaint.setColor(traversalColor);
            if (step == stepLimit - 1) {
                mCircleFillPaint.setAlpha(alpha);
            } else {
                mCircleFillPaint.setAlpha(255);
            }
            canvas.drawCircle(x, y, mCircleRadius, mCircleFillPaint);
            canvas.drawCircle(x, y, mCircleRadius, mCircleStrokePaint);
            String text = ((ExpressionNode) expression).getOp().toString();
            Rect rect = new Rect();
            mTextPaint.getTextBounds(text, 0, text.length(), rect);
            canvas.drawText(text, x - rect.width() / 2, y + rect.height() / 2, mTextPaint);
        } else traversalNumber(canvas, ((ExpressionNumber) expression), x, y, parentX, parentY);
        step++;
        if (expression instanceof ExpressionNode) {
            if (((ExpressionNode) expression).getRight() != null) {
                inOrderTraversal(canvas, ((ExpressionNode) expression).getRight(), x + xOffset, y + yOffset, x, y);
            }
        }
    }

    /* Method for animated postfix tree traversal */
    private void postOrderTraversal(Canvas canvas, IExpression expression, int x, int y, int parentX, int parentY) {

        int xOffset = (int) Math.pow(2, (expression.Height() - 1)) * (mCircleRadius + xGap);
        int yOffset = yGap + (mCircleRadius * 2);
        if (expression instanceof ExpressionNode) {
            if (((ExpressionNode) expression).getLeft() != null) {
                postOrderTraversal(canvas, ((ExpressionNode) expression).getLeft(), x - xOffset, y + yOffset, x, y);
            }

            if (((ExpressionNode) expression).getRight() != null) {
                postOrderTraversal(canvas, ((ExpressionNode) expression).getRight(), x + xOffset, y + yOffset, x, y);
            }
        }
        if (step >= stepLimit) {
            return;
        }
        if (parentX != 0 && parentY != 0) {
            traversalLine(canvas, parentX, parentY, x, y);
        }
        if (expression instanceof ExpressionNode) {
            mCircleFillPaint.setColor(traversalColor);
            if (step == stepLimit - 1) {
                mCircleFillPaint.setAlpha(alpha);
            } else {
                mCircleFillPaint.setAlpha(255);
            }
            canvas.drawCircle(x, y, mCircleRadius, mCircleFillPaint);
            canvas.drawCircle(x, y, mCircleRadius, mCircleStrokePaint);
            String text = ((ExpressionNode) expression).getOp().toString();
            Rect rect = new Rect();
            mTextPaint.getTextBounds(text, 0, text.length(), rect);
            canvas.drawText(text, x - rect.width() / 2, y + rect.height() / 2, mTextPaint);
        } else {
            traversalNumber(canvas, ((ExpressionNumber) expression), x, y, parentX, parentY);
        }
        step++;
    }

    /* Method for animated line traversal */
    private void traversalLine(Canvas canvas, int px, int py, int x, int y) {
        mLinePaint.setColor(traversalColor);

        int difX = x - px;
        int difY = y - py;
        int distance = (int) Math.sqrt(Math.pow(difX, 2) + Math.pow(difY, 2));
        int offsetX = difX * mCircleRadius / distance;
        int offsetY = difY * mCircleRadius / distance;
        canvas.drawLine(px + offsetX, py + offsetY, x - offsetX, y - offsetY, mLinePaint);

    }

    /* Method for animated number traversal */
    private void traversalNumber(Canvas canvas, ExpressionNumber expressionNumber, int x, int y, int parentX, int parentY) {
        mCircleFillPaint.setColor(traversalColor);
        if (step == stepLimit - 1) {
            mCircleFillPaint.setAlpha(alpha);
        } else {
            mCircleFillPaint.setAlpha(255);
        }
        canvas.drawCircle(x, y, mCircleRadius, mCircleFillPaint);
        canvas.drawCircle(x, y, mCircleRadius, mCircleStrokePaint);

        String text = expressionNumber.number.toString();
        Rect rect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        canvas.drawText(text, x - rect.width() / 2, y + rect.height() / 2, mTextPaint);
    }

    /* Normal state used for a simple tree image */
    private static final int STATE_NORMAL = -1;
    /* Pre order traversal state used for animated prefix tree traversal */
    private static final int STATE_PRE_ORDER_TRAVERSAL = 1;
    /* In order traversal state used for animated infix tree traversal */
    private static final int STATE_IN_ORDER_TRAVERSAL = 2;
    /* Post order traversal state used for animated postfix tree traversal */
    private static final int STATE_POST_ORDER_TRAVERSAL = 3;
    /* Scale factor for scale view */
    private float mScaleFactor = 1.f;
    /* Variable that contains information about the current state */
    private int state = STATE_NORMAL;
    /* Radius of node image */
    private int mCircleRadius;
    /* Width of tree image */
    private int xGap;
    /* Height of tree image */
    private int yGap;
    /* Top and bottom offset */
    private int topAndBottomOffset;
    /* Variable for AnimEndListener */
    private AnimEndListener mAnimEndListener;
    /* Size of node operation and number text */
    private int textSize;
    /* Default color for image (not animated) */
    private int commonColor;
    /* Default color for image (animated) */
    private int traversalColor;
    /* Width of view */
    private int mWidth;
    /* Height of view */
    private int mHeight;
    /* ScaleGestureDetector for gestural scaling */
    private final ScaleGestureDetector mScaleDetector;
    /* Paint of line */
    private Paint mLinePaint;
    /* Paint of node or number circle */
    private Paint mCircleFillPaint;
    /* Paint of node or number edging circle */
    private Paint mCircleStrokePaint;
    /* Paint of text */
    private Paint mTextPaint;
    /* Value of animator */
    private ValueAnimator mValueAnimator;
    /* Alpha used in animation */
    private int alpha;
    /* Height of phone screen used for normal reflection tree */
    private final int minimumViewHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    /* Width of phone screen used for normal reflection tree */
    private final int minimumViewWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    /* Variable contains x last x coordinate for move the view */
    private int lastX;
    /* Tree value */
    private BinaryExpressionTree binaryExpressionTree;
    /* Step of animation */
    private int step;
    /* Maximum value of step in animation */
    private int stepLimit;
    private boolean firstLaunch = true;

    public void setmHeightOfMenu(int heightOfMenu) {
        this.heightOfMenu = heightOfMenu;
    }

    private int heightOfMenu;
    /* Shared preferences for parameters of tree */
    private final SharedPreferences prefs = getContext().getSharedPreferences("com.example.extree", Context.MODE_PRIVATE);
}
