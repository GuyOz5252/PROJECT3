package com.example.project2_rev2.xmlElements;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

public class TypeWriter extends androidx.appcompat.widget.AppCompatTextView {

    // https://www.youtube.com/watch?v=4JUWmX1lK4o&t=222s

    private CharSequence text;
    private int index;
    private long delay;
    private boolean isRunning;

    public TypeWriter(@NonNull Context context) {
        super(context);
    }

    public TypeWriter(@NonNull Context context, @NonNull AttributeSet attrSet) {
        super(context, attrSet);
    }

    private Handler handler = new Handler();

    private Runnable charAdder = new Runnable() {
        @Override
        public void run() {
            setText(text.subSequence(0, index++));
            if (index <= text.length()) {
                handler.postDelayed(charAdder, delay);
            } else {
                isRunning = false;
            }
        }
    };

    public void animate(CharSequence text) {
        isRunning = true;
        this.text = text;
        index = 0;
        delay = 85;

        setText("");

        handler.removeCallbacks(charAdder);
        handler.postDelayed(charAdder, delay);
    }

    public void animate(CharSequence text, long delay) {
        isRunning = true;
        this.text = text;
        index = 0;
        this.delay = delay;

        setText("");

        handler.removeCallbacks(charAdder);
        handler.postDelayed(charAdder, delay);
    }

    public void stopAnimation() {
        handler.removeCallbacks(charAdder);
        setText(text);
        isRunning = false;
    }

    public boolean getIsRunning() {
        return isRunning;
    }
}
