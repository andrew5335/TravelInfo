package com.eye2web.travel.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.style.ClickableSpan;
import android.view.View;

public class ClickUrlSpan extends ClickableSpan {

    private Context mContext;
    private String mUrl;

    public ClickUrlSpan(@NonNull Context context, String url) {
        super();
        this.mContext = context;
        this.mUrl = url;
    }

    @Override
    public void onClick(View widget) {
        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mUrl)));
    }
}
