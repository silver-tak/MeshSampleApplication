package com.silvertak.meshsampleapplication.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import com.silvertak.meshsampleapplication.R;

public class ClickSwitchColorTextView extends AppCompatTextView implements View.OnTouchListener {

    private int nNormalColorId;
    private int nClickedColorId;
    private  OnClickListener onClickListener;

    public ClickSwitchColorTextView(Context context)
    {
        super(context);
    }

    public ClickSwitchColorTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        nNormalColorId = context.obtainStyledAttributes(attrs, R.styleable.ClickSwitchColorTextView).getResourceId(R.styleable.ClickSwitchColorTextView_normalBackgroundColor, 0);
        nClickedColorId = context.obtainStyledAttributes(attrs, R.styleable.ClickSwitchColorTextView).getResourceId(R.styleable.ClickSwitchColorTextView_clickedBackgroundColor, 0);

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
            setBackgroundColor(nClickedColorId);
        else
        {
            setBackgroundColor(nNormalColorId);
            if(onClickListener != null) onClickListener.onClick(view);
        }
        return true;
    }
}
