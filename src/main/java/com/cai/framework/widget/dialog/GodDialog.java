package com.cai.framework.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cai.framework.R;

public class GodDialog extends Dialog {
    public GodDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public GodDialog(Context context) {
        super(context);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
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

        public GodDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.god_dialog, null);
            final GodDialog dialog = new GodDialog(context, R.style.Dialog);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            // set the dialog title
            TextView tvTitile = (TextView) layout.findViewById(R.id.dialog_title);
            if (TextUtils.isEmpty(title)) {
                tvTitile.setVisibility(View.GONE);
            } else {
                tvTitile.setText(title);
                tvTitile.setVisibility(View.VISIBLE);
            }

            // set the content message
            TextView tvMessage = (TextView) layout.findViewById(R.id.dialog_message);
            if (contentView != null) {
                LinearLayout dialogContentView = (LinearLayout) layout.findViewById(R.id.dialog_content);
                dialogContentView.removeAllViews();
                dialogContentView.addView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else if (!TextUtils.isEmpty(message)) {
                tvMessage.setText(message);
            }else{
                tvMessage.setVisibility(View.GONE);
            }
            dialog.setContentView(layout);

            // set the confirm button
            TextView positiveButton = (TextView) layout.findViewById(R.id.btn_dialog_ok);
            if (TextUtils.isEmpty(positiveButtonText)) {
                positiveButton.setVisibility(View.GONE);
            } else {
                positiveButton.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                            dialog.dismiss();
                        }
                    });
                }

            }

            // set the cancel button
            TextView negativeButton = (TextView) layout.findViewById(R.id.btn_dialog_cancel);
            if (TextUtils.isEmpty(negativeButtonText)) {
                negativeButton.setVisibility(View.GONE);
            } else {
                negativeButton.setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    negativeButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                            dialog.dismiss();
                        }
                    });
                }
            }

            return dialog;
        }
    }
}
