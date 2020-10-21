package com.payzout.business.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.payzout.business.R;

public class PayzoutLoading {

    public static PayzoutLoading payzoutLoading = null;
    private Dialog mDialog;

    public static PayzoutLoading getInstance() {
        if (payzoutLoading == null) {
            payzoutLoading = new PayzoutLoading();
        }
        return payzoutLoading;
    }

    public void showProgress(Context context) {
        mDialog = new Dialog(context, R.style.DDLoading);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.payzout_loading_layout);

        ImageView ivDDLogo = mDialog.findViewById(R.id.ivDDLogo);
        YoYo.with(Techniques.Flash)
                .duration(500)
                .repeat(YoYo.INFINITE)
                .playOn(ivDDLogo);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    public void hideProgress() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
