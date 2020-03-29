package com.silvertak.meshsampleapplication.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.silvertak.meshsampleapplication.R;

public class ClickSwitchImageView extends AppCompatImageView implements View.OnTouchListener {

    private int nNormalImageId;
    private int nClickedImageId;
    private  OnClickListener onClickListener;

    public ClickSwitchImageView(Context context)
    {
        super(context);
        this.setScaleType(ScaleType.FIT_CENTER);
    }

    public ClickSwitchImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.setScaleType(ScaleType.FIT_CENTER);
        nNormalImageId = context.obtainStyledAttributes(attrs, R.styleable.ClickSwitchImageView).getResourceId(R.styleable.ClickSwitchImageView_normalSrc, 0);
        nClickedImageId = context.obtainStyledAttributes(attrs, R.styleable.ClickSwitchImageView).getResourceId(R.styleable.ClickSwitchImageView_clickedSrc, 0);

        setImageResource(nNormalImageId);
        this.setOnTouchListener(this);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        onClickListener = l;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE)
            setImageResource(nClickedImageId);
        else
        {
            setImageResource(nNormalImageId);
            if(onClickListener != null) onClickListener.onClick(view);
        }
        return true;
    }
}
