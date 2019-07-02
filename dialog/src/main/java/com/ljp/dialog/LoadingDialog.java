package com.ljp.dialog;


import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/*
 *@创建者       L_jp
 *@创建时间     2019/6/21 11:04.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class LoadingDialog  {

    public static final class Builder extends BaseDialogFragment.Builder<Builder> {

        private TextView mMessageView;
        private ProgressBar mProgressView;

        public Builder(FragmentActivity activity) {
            super(activity);

            setThemeStyle(R.style.TransparentDialogStyle);
            setContentView(R.layout.layout_loading_dialog);
            setAnimStyle(BaseDialog.AnimStyle.TOAST);
            setGravity(Gravity.CENTER);
            setCancelable(false);

            mMessageView = findViewById(R.id.tv_dialog_wait_message);
            mProgressView = findViewById(R.id.progress_bar);
        }

        public Builder setMessage(int resId) {
            return setMessage(getText(resId));
        }
        public Builder setMessage(CharSequence text) {
            mMessageView.setText(text);
            mMessageView.setVisibility(text == null ? View.GONE : View.VISIBLE);
            return this;
        }

        @Override
        public BaseDialog create() {
            // 如果内容为空就设置隐藏
            if ("".equals(mMessageView.getText().toString())) {
                mMessageView.setVisibility(View.GONE);
            }
            return super.create();
        }
    }
}
