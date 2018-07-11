package com.cai.framework.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cai.framework.R;
import com.example.clarence.utillibrary.DeviceUtils;

public class GodDialog extends Dialog implements View.OnClickListener {

    TextView positiveButton;
    TextView negativeButton;
    TextView oneButton;
    private DialogInterface.OnClickListener positiveButtonClickListener;
    private DialogInterface.OnClickListener negativeButtonClickListener;

    protected GodDialog(Builder builder) {
        super(builder.context, R.style.Dialog);
        this.oneButton = builder.oneButton;
        this.positiveButton = builder.positiveButton;
        this.negativeButton = builder.negativeButton;
        this.positiveButtonClickListener = builder.positiveButtonClickListener;
        this.negativeButtonClickListener = builder.negativeButtonClickListener;
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        setClickListener();
    }

    private void setClickListener() {
        positiveButton.setOnClickListener(this);
        negativeButton.setOnClickListener(this);
        oneButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_dialog_ok) {
            if (positiveButtonClickListener != null) {
                positiveButtonClickListener.onClick(GodDialog.this, DialogInterface.BUTTON_POSITIVE);
            }
        } else if (id == R.id.btn_dialog_cancel) {
            if (negativeButtonClickListener != null) {
                negativeButtonClickListener.onClick(GodDialog.this, DialogInterface.BUTTON_NEGATIVE);
            }
        } else if (id == R.id.btn_dialog_one) {
            if (positiveButtonClickListener != null) {
                positiveButtonClickListener.onClick(GodDialog.this, DialogInterface.BUTTON_POSITIVE);
            }
        }
        dismiss();
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;

        TextView positiveButton;
        TextView oneButton;
        TextView negativeButton;
        View contentView;
        boolean showOneButton;
        int screenWidth;


        public Builder(Context context) {
            this.context = context;
            screenWidth = DeviceUtils.getScreenWidth(context);
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText, DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setOneButton(String positiveButtonText, DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            this.showOneButton = true;
            return this;
        }

        public Builder setOneButton(int positiveButtonText, DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            this.showOneButton = true;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText, DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public GodDialog build() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.god_dialog, null);

            // set the dialog title
            TextView tvTitile = layout.findViewById(R.id.dialog_title);
            if (TextUtils.isEmpty(title)) {
                tvTitile.setVisibility(View.GONE);
            } else {
                tvTitile.setText(title);
                tvTitile.setVisibility(View.VISIBLE);
            }

            // set the content message
            TextView tvMessage = layout.findViewById(R.id.dialog_message);
            if (contentView != null) {
                LinearLayout dialogContentView = layout.findViewById(R.id.dialog_content);
                dialogContentView.removeAllViews();
                dialogContentView.addView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else if (!TextUtils.isEmpty(message)) {
                tvMessage.setMovementMethod(ScrollingMovementMethod.getInstance());
                tvMessage.setText(message);
            } else {
                tvMessage.setVisibility(View.GONE);
            }
            // set the confirm button
            positiveButton = layout.findViewById(R.id.btn_dialog_ok);
            if (TextUtils.isEmpty(positiveButtonText)) {
                positiveButton.setVisibility(View.GONE);
            } else {
                positiveButton.setText(positiveButtonText);
            }

            // set the cancel button
            negativeButton = layout.findViewById(R.id.btn_dialog_cancel);
            if (TextUtils.isEmpty(negativeButtonText)) {
                negativeButton.setVisibility(View.GONE);
            } else {
                negativeButton.setText(negativeButtonText);
            }

            oneButton = layout.findViewById(R.id.btn_dialog_one);
            LinearLayout rlTwoBtn = layout.findViewById(R.id.rlTwoBtn);
            if (showOneButton) {
                if (TextUtils.isEmpty(positiveButtonText)) {
                    oneButton.setVisibility(View.GONE);
                    rlTwoBtn.setVisibility(View.VISIBLE);
                } else {
                    oneButton.setText(positiveButtonText);
                    oneButton.setVisibility(View.VISIBLE);
                    rlTwoBtn.setVisibility(View.GONE);
                }
            }

            GodDialog dialog = new GodDialog(this);
//            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(DeviceUtils.getScreenWidth(context), DeviceUtils.getScreenHeight(context));
            dialog.setContentView(layout, layoutParams);
//            layout.requestLayout();
            return dialog;
        }
    }
}
