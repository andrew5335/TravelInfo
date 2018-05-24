package com.eye2web.travel.util;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.URLSpan;

public class CommonUtil {

    public SpannableStringBuilder convertTxtToLink(Context context, String str) {
        CharSequence sequence = convertTxtToHtml(str);
        SpannableStringBuilder buffer = new SpannableStringBuilder(sequence);
        URLSpan[] currentSpans = buffer.getSpans(0, sequence.length(), URLSpan.class);

        for(URLSpan span : currentSpans) {
            ClickUrlSpan clickUrlSpan = new ClickUrlSpan(context, span.getURL());
            int start = buffer.getSpanStart(span);
            int end = buffer.getSpanEnd(span);
            buffer.setSpan(clickUrlSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return buffer;
    }

    public Spanned convertTxtToHtml(String str) {
        Spanned result;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(str);
        }

        return result;
    }

    public String getKeyword(String areaGu) {
        String result = "";

        switch (areaGu) {
            case "seoul" :
                result = "서울";
                break;

            case "daejeon" :
                result = "대전";
                break;

            default :
                result = "서울";
                break;

        }

        return result;
    }
}
