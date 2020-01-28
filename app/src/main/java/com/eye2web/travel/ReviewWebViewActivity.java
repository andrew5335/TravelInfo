package com.eye2web.travel;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.eye2web.travel.util.CommonUtil;

public class ReviewWebViewActivity extends AppCompatActivity {

    private CommonUtil commonUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_web_view);

        commonUtil = new CommonUtil();

        Intent reviewIntent = getIntent();
        String reviewUrl = (String) reviewIntent.getStringExtra("reviewUrl");
        String reviewTitle = (String) reviewIntent.getStringExtra("reviewTitle");

        SpannableStringBuilder reviewTitleBuilder = new SpannableStringBuilder();
        reviewTitleBuilder = commonUtil.convertTxtToLink(getApplicationContext(), reviewTitle);
        String reviewTitleStr = reviewTitleBuilder.toString();

        if(25 < reviewTitleStr.length()) {
            reviewTitleStr = reviewTitleStr.substring(0, 25) + "...";
        }

        if(null != reviewUrl && !"".equals(reviewUrl) && 0 < reviewUrl.length()) {
            WebView reviewWebView = (WebView) findViewById(R.id.review_webview);
            TextView reviewTitleView = (TextView) findViewById(R.id.reviewTitle);

            reviewTitleView.setText(reviewTitleStr);

            reviewWebView.setWebViewClient(new WebViewClient());
            reviewWebView.setWebChromeClient(new WebChromeClient());
            reviewWebView.setNetworkAvailable(true);
            reviewWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                reviewWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            } else {
                reviewWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
            reviewWebView.setScrollbarFadingEnabled(true);
            reviewWebView.getSettings().setSupportZoom(true);
            reviewWebView.getSettings().setUseWideViewPort(true);
            reviewWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            reviewWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            reviewWebView.getSettings().setLoadWithOverviewMode(true);
            reviewWebView.getSettings().setBuiltInZoomControls(true);
            reviewWebView.getSettings().setJavaScriptEnabled(true);
            reviewWebView.getSettings().setDomStorageEnabled(true);

            reviewWebView.loadUrl(reviewUrl);
        }
    }

    public void onGobackBtnClicked(View v) {
        super.onBackPressed();
    }
}
