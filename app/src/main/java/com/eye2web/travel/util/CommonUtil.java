package com.eye2web.travel.util;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.URLSpan;

import com.eye2web.travel.vo.AreaListItem;

import java.util.ArrayList;

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
            case "1" :
                result = "서울";
                break;

            case "3" :
                result = "대전";
                break;

            case "2" :
                result = "인천";
                break;

            case "4" :
                result = "대구";
                break;

            case "5" :
                result = "광주";
                break;

            case "6" :
                result = "부산";
                break;

            case "7" :
                result = "울산";
                break;

            case "8" :
                result = "세종특별자치시";
                break;

            case "31" :
                result = "경기도";
                break;

            case "32" :
                result = "강원도";
                break;

            case "33" :
                result = "충청북도";
                break;

            case "34" :
                result = "충청남도";
                break;

            case "35" :
                result = "경상북도";
                break;

            case "36" :
                result = "경상남도";
                break;

            case "37" :
                result = "전라북도";
                break;

            case "38" :
                result = "전라남도";
                break;

            case "39" :
                result = "제주도";
                break;

            default :
                result = "서울";
                break;

        }

        return result;
    }

    public ArrayList<AreaListItem> getSortList() {
        ArrayList<AreaListItem> resultList = new ArrayList<AreaListItem>();

        AreaListItem sortItem = new AreaListItem("", "정렬", 0);
        resultList.add(sortItem);
        sortItem = new AreaListItem("O", "제목순", 1);
        resultList.add(sortItem);
        sortItem = new AreaListItem("P", "조회순", 2);
        resultList.add(sortItem);
        sortItem = new AreaListItem("Q", "수정일순", 3);
        resultList.add(sortItem);
        sortItem = new AreaListItem("R", "생성일순", 4);
        resultList.add(sortItem);

        return resultList;
    }
}
