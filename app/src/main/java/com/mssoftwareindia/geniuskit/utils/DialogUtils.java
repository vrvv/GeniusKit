package com.mssoftwareindia.geniuskit.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.mssoftwareindia.geniuskit.R;


/**
 * Created by Karan Pitroda on 23,March,2021
 */
public class DialogUtils {

    public static ThemeLoaderDialog showLoader(Activity activity) {
        ThemeLoaderDialog dialog = new ThemeLoaderDialog(activity);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }
    public static class ThemeLoaderDialog extends Dialog implements
            View.OnClickListener {

        public Activity activity;
        public Context context;
        public ProgressBar progressBar;


        public ThemeLoaderDialog(Activity activity) {
            super(activity);
            this.activity = activity;
            this.context = activity;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            setContentView(R.layout.view_loader);
            progressBar=findViewById(R.id.progressBar);
        }


        @Override
        public void onClick(View v) {
            dismiss();
        }
    }

}
