package com.mssoftwareindia.geniuskit.utils;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.mssoftwareindia.geniuskit.R;


public class ToastUtils {

    public static void show(Context context, String message, int duration) {
        showToast(context, message, duration);
    }

    public static void show(Context context, String message) {
        showToast(context, message, Toast.LENGTH_SHORT);
    }

    public static void showLong(Context context, String message) {
        showToast(context, message, Toast.LENGTH_LONG);
    }

    public static void show(Context context, int messageResId) {
        showToast(context, context.getString(messageResId), Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, String message, int duration) {
        if (message.isEmpty()) {
            return;
        }

        TextView textView = new TextView(context);
        int dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources().getDisplayMetrics());

        textView.setPadding(dp * 20, dp * 12, dp * 20, dp * 12);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setGravity(Gravity.LEFT);
        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        textView.setText(message);
        textView.setBackgroundResource(R.drawable.toast_rect);

        Toast toast = Toast.makeText(context, message, duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(textView);
        toast.show();
    }
}

