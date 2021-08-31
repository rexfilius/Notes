package com.github.rexfilius.prioritynotes.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.rexfilius.prioritynotes.R;

public class HorizontalNumberPicker extends LinearLayout {

    private TextView editTextNumber;
    private int minimum, maximum;

    public HorizontalNumberPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.numberpicker_horizontal, this);
        editTextNumber = findViewById(R.id.editTextNumber);

        final Button buttonLess = findViewById(R.id.buttonLess);
        buttonLess.setOnClickListener(new AddHandler(-1));

        final Button buttonMore = findViewById(R.id.buttonMore);
        buttonMore.setOnClickListener(new AddHandler(1));
    }

    /**
     * GETTERS && SETTERS
     */
    public int getValue() {
        if (editTextNumber != null) {
            try {
                final String value = editTextNumber.getText().toString();
                return Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                Log.e("HorizontalNumberPicker", ex.toString());
            }
        }
        return 0;
    }

    public void setValue(final int value) {
        if (editTextNumber != null) {
            editTextNumber.setText(String.valueOf(value));
        }
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    /**
     * HANDLERS
     */
    private class AddHandler implements OnClickListener {

        final int diff;

        public AddHandler(int diff) {
            this.diff = diff;
        }

        @Override
        public void onClick(View v) {
            int newValue = getValue() + diff;
            if (newValue < minimum) {
                newValue = minimum;
            } else if (newValue > maximum) {
                newValue = maximum;
            }
            editTextNumber.setText(String.valueOf(newValue));
        }
    }

}
