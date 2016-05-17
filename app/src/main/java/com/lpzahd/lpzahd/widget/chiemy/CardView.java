package com.lpzahd.lpzahd.widget.chiemy;
/*
 * The MIT License (MIT)
 * Copyright (c) [2015] [chiemy]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;


/**
 * @author chiemy
 *
 * 增加 move 拖动第一视图
 * 增加点击其他子view展开效果
 * 增加拖动距离小于高度一半回复效果
 *
 */
public class CardView extends FrameLayout {
    private static final int ITEM_SPACE = 40;
    private static final int DEF_MAX_VISIBLE = 4;

    private int mMaxVisible = DEF_MAX_VISIBLE;
    private int itemSpace = ITEM_SPACE;

    private float mTouchSlop;
    private ListAdapter mListAdapter;
    private int mNextAdapterPosition;
    private SparseArray<View> viewHolder = new SparseArray<View>();
    private OnCardClickListener mListener;
    private int topPosition;
    private Rect topRect;

    public interface OnCardClickListener {
        void onCardClick(View view, int position);
    }

    public CardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CardView(Context context) {
        super(context);
        init();
    }

    private void init() {
        topRect = new Rect();
        ViewConfiguration con = ViewConfiguration.get(getContext());
        mTouchSlop = con.getScaledTouchSlop();
    }

    public void setMaxVisibleCount(int count) {
        mMaxVisible = count;
    }

    public int getMaxVisibleCount() {
        return mMaxVisible;
    }

    public void setItemSpace(int itemSpace) {
        this.itemSpace = itemSpace;
    }

    public int getItemSpace() {
        return itemSpace;
    }

    public ListAdapter getAdapter() {
        return mListAdapter;
    }

    public void setAdapter(ListAdapter adapter) {
        if (mListAdapter != null) {
            mListAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
        mNextAdapterPosition = 0;
        mListAdapter = adapter;
        adapter.registerDataSetObserver(mDataSetObserver);
        removeAllViews();
        ensureFull();
    }

    public void setOnCardClickListener(OnCardClickListener listener) {
        mListener = listener;
    }

    private void ensureFull() {
        while (mNextAdapterPosition < mListAdapter.getCount()
                && getChildCount() < mMaxVisible) {
            int index = mNextAdapterPosition % mMaxVisible;
            View convertView = viewHolder.get(index);
            final View view = mListAdapter.getView(mNextAdapterPosition,
                    convertView, this);
            view.setOnClickListener(null);
            viewHolder.put(index, view);

            // 添加剩余的View时，始终处在最后
            index = Math.min(mNextAdapterPosition, mMaxVisible - 1);
            ViewHelper.setScaleX(view,getChildScaleX(index));
            int topMargin = (mMaxVisible - index - 1) * itemSpace;
            ViewHelper.setTranslationY(view, topMargin);
            ViewHelper.setAlpha(view, mNextAdapterPosition == 0 ? 1 : 0.5f);

            LayoutParams params = (LayoutParams) view.getLayoutParams();
            if (params == null) {
                params = new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT);
            }
            addViewInLayout(view, 0, params);

            mNextAdapterPosition += 1;

            invalidate();
        }
        // requestLayout();
    }

    /**
     * 获取每层child的缩放尺寸
     * @param childIndex
     * @return
     */
    private float getChildScaleX(int childIndex) {
        return ((mMaxVisible - childIndex - 1) / (float) mMaxVisible) * 0.2f + 0.8f;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();
        int maxHeight = 0;
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            this.measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int height = child.getMeasuredHeight();
            int width = child.getMeasuredWidth();
            if (height > maxHeight) {
                maxHeight = height;
            }
            if (width > maxWidth) {
                maxWidth = width;
            }
        }
        int desireWidth = widthSize;
        int desireHeight = heightSize;
        if (widthMode == MeasureSpec.AT_MOST) {
            desireWidth = maxWidth + getPaddingLeft() + getPaddingRight();
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            desireHeight = maxHeight + (mMaxVisible - 1) * itemSpace + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(desireWidth, desireHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        View topView = getChildAt(getChildCount() - 1);
        if (topView != null) {
            topView.setOnClickListener(listener);
        }
    }

    float downX, downY;

    // down 时监听是否开启移动第一视图
    boolean isMove;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final View topView = getChildAt(getChildCount() - 1);
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (isMove) {
                    int topMargin = (mMaxVisible - 0 - 1) * itemSpace;
                    float tranY = topMargin  + event.getY() - downY;
                    if(event.getY() < downY) {
                        tranY = topMargin;
                    }
                    ViewPropertyAnimator
                            .animate(topView)
                            .translationY(tranY).scaleX(1)
                            .setListener(null).setDuration(0);
                }
                break;
            case MotionEvent.ACTION_UP:
                next(true);
                break;
        }
        return super.onTouchEvent(event);
    }

    public void next() {
        next(false);
    }

    /**
     * next 延时默认时间
     */
    private static final int NEXT_DELAY_TIME = 500;
    private long preTime;

    /**
     * next
     * @param checkPosition 是否检查需要当前位置撤回
     */
    private void next(boolean checkPosition) {
        final View topView = getChildAt(getChildCount() - 1);
        if(topView == null) {
            return ;
        }

        if(!checkPosition) {
            // 检查操作时间，是否过短，导致动画执行尚未结束又开始新一轮了
            long currTime = System.currentTimeMillis();
            if(currTime - preTime > NEXT_DELAY_TIME) {
                goDown();
            }
            preTime = currTime;
            return ;
        }
        if (isMove) {
            downY = -1;

            // 如果拖动范围小于view本身一半则回复状态
            float tranY = ViewHelper.getTranslationY(topView);
            int minHeight = topView.getHeight() / 3;
            if(tranY < minHeight) {
                final int topMargin = (mMaxVisible - 1) * itemSpace;
                ViewPropertyAnimator
                        .animate(topView)
                        .translationY(topMargin).scaleX(getChildScaleX(0))
                        .setListener(null).setDuration(400);
            } else {
                goDown();
            }
        }
    }

    /**
     * 下移所有视图
     */
    private boolean goDown() {
        final View topView = getChildAt(getChildCount() - 1);
        topView.setEnabled(false);
        ViewPropertyAnimator anim = ViewPropertyAnimator
                .animate(topView)
                .translationY(
                        ViewHelper.getTranslationY(topView)
                                + topView.getHeight()).alpha(0).scaleX(1)
                .setListener(null).setDuration(200);
        anim.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                topView.setEnabled(true);
                removeView(topView);
                ensureFull();
                final int count = getChildCount();
                for (int i = 0; i < count; i++) {
                    final View view = getChildAt(i);
                    float scaleX = ViewHelper.getScaleX(view)
                            + ((float) 1 / mMaxVisible) * 0.2f;
                    float tranlateY = ViewHelper.getTranslationY(view)
                            + itemSpace;
                    if (i == count - 1) {
                        bringToTop(view);
                    } else {
                        if ((count == mMaxVisible && i != 0)
                                || count < mMaxVisible) {
                            ViewPropertyAnimator
                                    .animate(view)
                                    .translationY(tranlateY)
                                    .setInterpolator(
                                            new AccelerateInterpolator())
                                    .setListener(null).scaleX(scaleX)
                                    .setDuration(200);
                        }
                    }
                }
            }
        });
        return true;
    }

    /**
     * 将下一个视图移到前边
     *
     * @param view
     */
    private void bringToTop(final View view) {
        topPosition++;
        float scaleX = ViewHelper.getScaleX(view) + ((float) 1 / mMaxVisible)
                * 0.2f;
        float tranlateY = ViewHelper.getTranslationY(view) + itemSpace;
        ViewPropertyAnimator.animate(view).translationY(tranlateY)
                .scaleX(scaleX).setDuration(200).alpha(1)
                .setInterpolator(new AccelerateInterpolator());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float currentY = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                final View topView = getChildAt(getChildCount() - 1);
                if(topView == null) {
                    isMove = false;
                    break;
                }

                if(!topView.isEnabled()){
                    isMove = false;
                    break;
                }
                int childIndex = downChildIndex(downY);

                if(childIndex == 0) {
                    isMove = true;
                } else {
                    scrollToFore(childIndex);
                }
                break;
//                // topView.getHitRect(topRect); 在4.3以前有bug，用以下方法代替
//                topRect = getHitRect(topRect, topView);
//                // 如果按下的位置不在顶部视图上，则不移动
//                if (!topRect.contains((int) downX, (int) downY)) {
//                    isMove = false;
//
//                    int childIndex = downChildIndex(downY);
//                    // 移动到前台
//                    scrollToFore(childIndex);
//                    break;
//                }
//
//                isMove = true;
//                break;
            case MotionEvent.ACTION_MOVE:
                float distance = currentY - downY;
                if (distance > mTouchSlop) {
                    return true;
                }
                break;
        }
        return false;
    }

    /**
     * 移动到前台（循环）
     *  居然没有set，只能手写坑人了
     * @param childIndex
     */
    private void scrollToFore(final int childIndex) {
        if(childIndex > 0) {
            final View topView = getChildAt(getChildCount() - 1);
            topView.setEnabled(false);
            ViewPropertyAnimator anim = ViewPropertyAnimator
                    .animate(topView)
                    .translationY(
                            ViewHelper.getTranslationY(topView)
                                    + topView.getHeight()).alpha(0).scaleX(1)
                    .setListener(null).setDuration(200);
            anim.setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    final int index = childIndex;
                    topView.setEnabled(true);
                    removeView(topView);
                    ensureFull();
                    final int count = getChildCount();
                    for (int i = 0; i < count; i++) {
                        final View view = getChildAt(i);
                        float scaleX = ViewHelper.getScaleX(view)
                                + ((float) 1 / mMaxVisible) * 0.2f;
                        float tranlateY = ViewHelper.getTranslationY(view)
                                + itemSpace;
                        if (i == count - 1) {
                            topPosition++;
                            float sX = ViewHelper.getScaleX(view) + ((float) 1 / mMaxVisible)
                                    * 0.2f;
                            float tY = ViewHelper.getTranslationY(view) + itemSpace;
                            ViewPropertyAnimator animator =  ViewPropertyAnimator.animate(view).translationY(tY)
                                    .scaleX(sX).setDuration(200).alpha(1)
                                    .setInterpolator(new AccelerateInterpolator());
                            animator.setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    scrollToFore(index - 1);
                                }
                            });
                        } else {
                            if ((count == mMaxVisible && i != 0)
                                    || count < mMaxVisible) {
                                ViewPropertyAnimator
                                        .animate(view)
                                        .translationY(tranlateY)
                                        .setInterpolator(
                                                new AccelerateInterpolator())
                                        .setListener(null).scaleX(scaleX)
                                        .setDuration(200);
                            }
                        }
                    }
                }
            });
        }
    }

    /**
     *
     * @param downY
     * @return
     */
    private int downChildIndex(float downY) {
        //mMaxVisible 页面最大显示child数量
        //itemSpace  child之间的间距
        int index = (int) (downY / itemSpace);
        int tIndex = mMaxVisible - index;
        return tIndex > 0 ? tIndex : 0;
    }

    /**
     * 手指按压在哪个child上
     * @param pointF
     * @return
     *
     *
     * 注: 这里只辨识高度是否在child范围之内，不辨识宽度缩放
     */
    private int downChildIndex(PointF pointF) {
        //mMaxVisible 页面最大显示child数量
        //itemSpace  child之间的间距
        int index = (int) (pointF.y / itemSpace);
        int tIndex = mMaxVisible - index;
        return tIndex > 0 ? tIndex : 0;
    }

    public static Rect getHitRect(Rect rect, View child) {
        rect.left = child.getLeft();
        rect.right = child.getRight();
        rect.top = (int) (child.getTop() + ViewHelper.getTranslationY(child));
        rect.bottom = (int) (child.getBottom() + ViewHelper
                .getTranslationY(child));
        return rect;
    }

    /**
     *
     * @param rect
     * @param childIndex
     * @return
     */
    public Rect getHitRect(Rect rect, int childIndex) {
        View child = getChildAt(childIndex);
        return getHitRect(rect, child);
    }

    private final DataSetObserver mDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    };

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onCardClick(v, topPosition);
            }
        }
    };
}