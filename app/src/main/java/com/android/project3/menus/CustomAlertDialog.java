package com.android.project3.menus;

import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.project3.R;
import com.android.project3.utils.Action;

public class CustomAlertDialog extends Dialog implements View.OnTouchListener {

    private Context context;
    private String message;
    private Action action;
    private Action noAction;

    TextView txtMessage;
    Button btnYes, btnNo;

    public CustomAlertDialog(@NonNull Context context, String message, Action action, Action noAction) {
        super(context);
        this.context = context;
        this.message = message;
        this.action = action;
        this.noAction = noAction;

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.dialog_alert);
        getWindow().setBackgroundDrawableResource(R.drawable.rounded_corners);
        setTitle("confirm dialog");

        txtMessage = findViewById(R.id.txtConfirmMessege_confirm);
        btnYes = findViewById(R.id.btnYes_confirm);
        btnNo = findViewById(R.id.btnNo_confirm);

        btnYes.setOnTouchListener(this);
        btnNo.setOnTouchListener(this);

        txtMessage.setText(message);
    }

    public void clickYes() {
        action.action();
        this.dismiss();
    }

    public void clickYes(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickYes();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    public void clickNo() {
        noAction.action();
        this.dismiss();
    }

    public void clickNo(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickNo();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.btnYes_confirm:
                clickYes(view, motionEvent);
                break;
            case R.id.btnNo_confirm:
                clickNo(view, motionEvent);
                break;
        }
        return true;
    }
}
