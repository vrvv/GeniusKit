package com.mssoftwareindia.geniuskit.ui;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mssoftwareindia.geniuskit.R;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;


public class OtpView extends LinearLayout {
    private EditText mOtpOneField;
    private EditText mOtpTwoField;
    private EditText mOtpThreeField;
    private EditText mOtpFourField;
    private EditText mOtpFiveField;
    private EditText mOtpSixField;
    private EditText mCurrentlyFocusedEditText;

    public OtpView(Context context) {
        super(context);
        this.init((AttributeSet) null);
    }

    public OtpView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(attrs);
    }

    public OtpView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray styles = this.getContext().obtainStyledAttributes(attrs, R.styleable.OtpView);
        @SuppressLint("WrongConstant") LayoutInflater mInflater = (LayoutInflater) this.getContext().getSystemService("layout_inflater");
        mInflater.inflate(R.layout.otp_layout, this);
        this.mOtpOneField = (EditText) this.findViewById(R.id.otp_one_edit_text);
        this.mOtpTwoField = (EditText) this.findViewById(R.id.otp_two_edit_text);
        this.mOtpThreeField = (EditText) this.findViewById(R.id.otp_three_edit_text);
        this.mOtpFourField = (EditText) this.findViewById(R.id.otp_four_edit_text);
        this.mOtpFiveField = (EditText) this.findViewById(R.id.otp_five_edit_text);
        this.mOtpSixField = (EditText) this.findViewById(R.id.otp_six_edit_text);
        this.mOtpOneField.setTextIsSelectable(false);
        this.mOtpOneField.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                String textToPaste = null;

                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);

                /* Returns true if there is currently a primary clip on the clipboard. */

                if (clipboard.hasPrimaryClip()) {
                    ClipData clip = clipboard.getPrimaryClip();

                    // if you need text data only, then you have to check the MIME type for Text as i shown below :
                    if (clip.getDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))
                        // WARNING: The item could cantain URI that points to the text data.
                        // In this case the getText() returns null and this code fails!
                        textToPaste = clip.getItemAt(0).getText().toString();

                    // or you may coerce the data to the text representation: i have explained this in the second image.
                    textToPaste = clip.getItemAt(0).coerceToText(getContext()).toString();
                }
                setOTP(textToPaste);
                //Log.i("TAG", "OTP Clipboard :-> " + textToPaste);

                return false;
            }
        });

        this.styleEditTexts(styles);
        styles.recycle();

    }

    private String makeOTP() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mOtpOneField.getText().toString());
        stringBuilder.append(this.mOtpTwoField.getText().toString());
        stringBuilder.append(this.mOtpThreeField.getText().toString());
        stringBuilder.append(this.mOtpFourField.getText().toString());
        stringBuilder.append(this.mOtpFiveField.getText().toString());
        stringBuilder.append(this.mOtpSixField.getText().toString());
        return stringBuilder.toString();
    }

    public boolean hasValidOTP() {
        return this.makeOTP().length() == 6;
    }

    public String getOTP() {
        return this.makeOTP();
    }

    public void setOTP(String otp) {
        this.mOtpOneField.setText(String.valueOf(otp.charAt(0)));
        this.mOtpTwoField.setText(String.valueOf(otp.charAt(1)));
        this.mOtpThreeField.setText(String.valueOf(otp.charAt(2)));
        this.mOtpFourField.setText(String.valueOf(otp.charAt(3)));
        this.mOtpFiveField.setText(String.valueOf(otp.charAt(4)));
        this.mOtpSixField.setText(String.valueOf(otp.charAt(5)));

    }

    private void styleEditTexts(TypedArray styles) {
        int textColor = styles.getColor(R.styleable.OtpView_android_textColor, -16777216);
        int backgroundColor = styles.getColor(R.styleable.OtpView_text_background_color, 0);
        if (styles.getColor(R.styleable.OtpView_text_background_color, 0) != 0) {
            this.mOtpOneField.setBackgroundColor(backgroundColor);
            this.mOtpTwoField.setBackgroundColor(backgroundColor);
            this.mOtpThreeField.setBackgroundColor(backgroundColor);
            this.mOtpFourField.setBackgroundColor(backgroundColor);
            this.mOtpFiveField.setBackgroundColor(backgroundColor);
            this.mOtpSixField.setBackgroundColor(backgroundColor);
        } else {
        }
        this.mOtpOneField.setTextColor(textColor);
        this.mOtpTwoField.setTextColor(textColor);
        this.mOtpThreeField.setTextColor(textColor);
        this.mOtpFourField.setTextColor(textColor);
        this.mOtpFiveField.setTextColor(textColor);
        this.mOtpSixField.setTextColor(textColor);
        this.setEditTextInputStyle(styles);
    }

    private void setEditTextInputStyle(TypedArray styles) {
        int inputType = styles.getInt(R.styleable.OtpView_android_inputType, 0);
        this.mOtpOneField.setInputType(inputType);
        this.mOtpTwoField.setInputType(inputType);
        this.mOtpThreeField.setInputType(inputType);
        this.mOtpFourField.setInputType(inputType);
        this.mOtpFiveField.setInputType(inputType);
        this.mOtpSixField.setInputType(inputType);
        String text = styles.getString(R.styleable.OtpView_otp);
        if (!TextUtils.isEmpty(text) && text.length() == 6) {
            this.mOtpOneField.setText(String.valueOf(text.charAt(0)));
            this.mOtpTwoField.setText(String.valueOf(text.charAt(1)));
            this.mOtpThreeField.setText(String.valueOf(text.charAt(2)));
            this.mOtpFourField.setText(String.valueOf(text.charAt(3)));
            this.mOtpFiveField.setText(String.valueOf(text.charAt(4)));
            this.mOtpSixField.setText(String.valueOf(text.charAt(5)));
        }

        this.setFocusListener();
        this.setOnTextChangeListener();
    }

    private void setFocusListener() {
        OnFocusChangeListener onFocusChangeListener = new OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                OtpView.this.mCurrentlyFocusedEditText = (EditText) v;
                OtpView.this.mCurrentlyFocusedEditText.setSelection(OtpView.this.mCurrentlyFocusedEditText.getText().length());
            }
        };
        this.mOtpOneField.setOnFocusChangeListener(onFocusChangeListener);
        this.mOtpTwoField.setOnFocusChangeListener(onFocusChangeListener);
        this.mOtpThreeField.setOnFocusChangeListener(onFocusChangeListener);
        this.mOtpFourField.setOnFocusChangeListener(onFocusChangeListener);
        this.mOtpFiveField.setOnFocusChangeListener(onFocusChangeListener);
        this.mOtpSixField.setOnFocusChangeListener(onFocusChangeListener);
    }

    public void disableKeypad() {
        OnTouchListener touchListener = new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                @SuppressLint("WrongConstant") InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService("input_method");
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

                return true;
            }
        };
        this.mOtpOneField.setOnTouchListener(touchListener);
        this.mOtpTwoField.setOnTouchListener(touchListener);
        this.mOtpThreeField.setOnTouchListener(touchListener);
        this.mOtpFourField.setOnTouchListener(touchListener);
        this.mOtpFiveField.setOnTouchListener(touchListener);
        this.mOtpSixField.setOnTouchListener(touchListener);
    }

    public void enableKeypad() {
        OnTouchListener touchListener = new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        };
        this.mOtpOneField.setOnTouchListener(touchListener);
        this.mOtpTwoField.setOnTouchListener(touchListener);
        this.mOtpThreeField.setOnTouchListener(touchListener);
        this.mOtpFourField.setOnTouchListener(touchListener);
    }

    public EditText getCurrentFoucusedEditText() {
        return this.mCurrentlyFocusedEditText;
    }

    private void setOnTextChangeListener() {
        TextWatcher textWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                try {
                    if (OtpView.this.mCurrentlyFocusedEditText.getText().length() >= 1 && OtpView.this.mCurrentlyFocusedEditText != OtpView.this.mOtpSixField) {
                        OtpView.this.mCurrentlyFocusedEditText.focusSearch(View.FOCUS_RIGHT).requestFocus();
                    } else if (OtpView.this.mCurrentlyFocusedEditText.getText().length() >= 1 /*&& OtpView.this.mCurrentlyFocusedEditText == OtpView.this.mOtpsixField*/) {
                        @SuppressLint("WrongConstant") InputMethodManager imm = (InputMethodManager) OtpView.this.getContext().getSystemService("input_method");
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(OtpView.this.getWindowToken(), 0);
                        }
                    } else {
                        String currentValue = OtpView.this.mCurrentlyFocusedEditText.getText().toString();
                        if (currentValue.length() <= 0 && OtpView.this.mCurrentlyFocusedEditText.getSelectionStart() <= 0) {
                            OtpView.this.mCurrentlyFocusedEditText.focusSearch(View.FOCUS_LEFT).requestFocus();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        this.mOtpOneField.addTextChangedListener(textWatcher);
        this.mOtpTwoField.addTextChangedListener(textWatcher);
        this.mOtpThreeField.addTextChangedListener(textWatcher);
        this.mOtpFourField.addTextChangedListener(textWatcher);
        this.mOtpFiveField.addTextChangedListener(textWatcher);
        this.mOtpSixField.addTextChangedListener(textWatcher);
    }

    public void simulateDeletePress() {
        this.mCurrentlyFocusedEditText.setText("");
    }
}
