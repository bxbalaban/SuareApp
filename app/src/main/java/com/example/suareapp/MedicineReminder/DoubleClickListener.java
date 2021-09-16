package com.example.suareapp.MedicineReminder;

import android.view.View;

abstract class DoubleClickLister implements View.OnClickListener {
    private static final long DOUBLE_CLICK_TIME_DELTA=10000;
    long lastClickTime=0;

    public void onClick(View v){
        long clickTime=System.currentTimeMillis();
        if(clickTime-lastClickTime<DOUBLE_CLICK_TIME_DELTA){
            onDoubleClick(v);
        }else{
            onSingleClick(v);
        }
        lastClickTime=clickTime;
    }

    protected abstract void onSingleClick(View v);

    protected abstract void onDoubleClick(View v);

}

