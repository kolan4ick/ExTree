package com.example.extree.tree_draw;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.example.extree.R;
import com.example.extree.tree.BinaryExpressionTree;
import com.example.extree.tree.ExpressionNode;
import com.example.extree.tree.ExpressionNumber;

public class BinaryExpressionTreeView extends View {
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
        init(attrs);
    }

    /* Setter binaryExpressionTree value */
    public void setBinaryExpressionTree(BinaryExpressionTree binaryExpressionTree) {
        this.binaryExpressionTree = binaryExpressionTree;
    }

    /* Main method of View extended class. Drawing the graph */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);

        if (binaryExpressionTree == null) {
            return;
        }
        canvas.drawColor(Color.WHITE);
        drawTree(canvas, binaryExpressionTree, mWidth / 2, mCircleRadius + topAndBottomOffset);
        canvas.restore();
    }

    /* Main drawing-tree method */
    private void drawTree(Canvas canvas, BinaryExpressionTree binaryExpressionTree, int x, int y) {
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

    /* Method for automatic selection of view size */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (binaryExpressionTree != null) {
            int treeHeight = binaryExpressionTree.Height();
            int maxLeafCount = (int) Math.pow(2, treeHeight + 1);
            mWidth = mCircleRadius * 2 * maxLeafCount + 2 * xGap * (maxLeafCount + 1);

            mHeight = mCircleRadius * 2 * treeHeight + yGap * (treeHeight + 2) + 2 * topAndBottomOffset;

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

    /* Method for initializing main values */
    private void init(AttributeSet attrs) {

        commonColor = getResources().getColor(R.color.app_common_color);
        traversalColor = getResources().getColor(R.color.home_nav_sort_color);
        topAndBottomOffset = getResources().getDimensionPixelOffset(R.dimen.bitree_top_bottom_offset);

        if (attrs != null) {
            Resources res = getResources();
            TypedArray ta = res.obtainAttributes(attrs, R.styleable.BinaryExpressionTreeView);
            mCircleRadius = ta.getDimensionPixelSize(R.styleable.BinaryExpressionTreeView_circle_radius, res.getDimensionPixelSize(R.dimen.bitree_radius_default));
            xGap = ta.getDimensionPixelSize(R.styleable.BinaryExpressionTreeView_x_gap, res.getDimensionPixelSize(R.dimen.bitree_x_gap_default));
            yGap = ta.getDimensionPixelSize(R.styleable.BinaryExpressionTreeView_y_gap, res.getDimensionPixelSize(R.dimen.bitree_y_gap_default));
            textSize = ta.getDimensionPixelSize(R.styleable.BinaryExpressionTreeView_text_size, res.getDimensionPixelSize(R.dimen.bitree_text_size_default));
        }

        initPaint();
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
        mTextPaint.setColor(getResources().getColor(R.color.text_color_white));
        mTextPaint.setTextSize(textSize);
    }

    private static final int STATE_NORMAL = -1;
    private static final int STATE_PRE_ORDER_TRAVERSAL = 1;
    private static final int STATE_IN_ORDER_TRAVERSAL = 2;
    private static final int STATE_POST_ORDER_TRAVERSAL = 3;
    private static final int STATE_LEVEL_TRAVERSAL = 4;
    private float mScaleFactor = 1.f;
    private int state = STATE_NORMAL;

    private int mCircleRadius;
    private int xGap;
    private int yGap;
    private int topAndBottomOffset;

    private int textSize;

    private int commonColor;
    private int traversalColor;

    private int mWidth;
    private int mHeight;
    private ScaleGestureDetector mScaleDetector;
    private Paint mLinePaint;
    private Paint mCircleFillPaint;
    private Paint mCircleStrokePaint;
    private Paint mTextPaint;

    private ValueAnimator mValueAnimator;
    private int alpha;

//    private AnimEndListener mAnimEndListener;

    private int lastX;
    /* Tree value */
    private BinaryExpressionTree binaryExpressionTree;

    private int step;

    private int stepLimit;
}
