package com.eye2web.travel.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.URLSpan;

import com.eye2web.travel.vo.AreaListItem;

import java.io.InputStream;
import java.util.ArrayList;

public class CommonUtil {

    /**
     * @parameter : context - 문자열 변환시 필요한 context
     *              str - 변환대상 문자열
     * @Date : 2018. 7. 5. PM 5:24
     * @Author : Andrew Kim
     * @Description : 특정 문자열에 링크가 포함된 경우 해당 링크 표시를 위한 변환 처리
    **/
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

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if(height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while((halfHeight / inSampleSize) > reqHeight &&
                    (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public Bitmap decodeSampledBitmapFromStream(InputStream is, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeStream(is);
    }

    /**
     * @parameter : str - 변환대상 문자열
     * @Date : 2018. 7. 5. PM 5:23
     * @Author : Andrew Kim
     * @Description : 일반 문자열을 html 형식으로 변환 처리
    **/
    public Spanned convertTxtToHtml(String str) {
        Spanned result;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(str);
        }

        return result;
    }

    /**
     * @parameter : areaGu - 지역 구분코드
     * @Date : 2018. 7. 5. PM 5:23
     * @Author : Andrew Kim
     * @Description : 지역 구분코드에 따른 검색용 키워드 설정 처리
    **/
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

    /**
     * @parameter : code - 날씨 정보 관련 open weather map 에서 return 되어 오는 코드값
     * @Date : 2018. 7. 5. PM 5:22
     * @Author : Andrew Kim
     * @Description : open weather map api에서 return되는 코드값에 따라 날씨 관련
     *                안내문자 세팅 처리
    **/
    public String getWeatherDesc(String code) {
        String result = "";

        if(null != code && !"".equalsIgnoreCase(code)) {
            String codeStr = "";
            codeStr = code.substring(0, 1);

            switch (codeStr) {
                case "2":
                    result = "뇌우를 동반한 비가 내려요. 안전에 유의하세요.";
                    break;

                case "3":
                    result = "가랑비가 내려요. 우산 꼭 챙기세요.";
                    break;

                case "5":
                    result = "비가 오네요. 우산 꼭 챙기세요.";
                    break;

                case "6":
                    result = "눈이 와요. 미끄러지지 않게 조심하세요.";
                    break;

                case "7":
                    switch (code) {
                        case "701":
                            result = "안개가 낀 날씨에요. 안전에 유의하세요.";
                            break;

                        case "711":
                            result = "연기가 자욱해요. 안전에 유의하세요.";
                            break;

                        case "721":
                            result = "약한 안개가 낀 날씨에요.";
                            break;

                        case "731":
                            result = "모래바람이 날려요.";
                            break;

                        case "741":
                            result = "짙은 안개가 낀 날씨에요. 조심하세요.";
                            break;

                        case "751":
                            result = "황사가 심해요. 건강 유의하세요.";
                            break;

                        case "761":
                            result = "먼지가 심한 날씨에요. 건강 조심하세요.";
                            break;

                        case "771":
                            result = "돌풍이 불어요. 조심하세요.";
                            break;

                        case "781":
                            result = "회오리 바람이 불어요. 안전에 유의하세요.";
                            break;
                    }

                case "8":
                    switch (code) {
                        case "800":
                            result = "맑고 화창한 날씨에요. 나들이하기 좋아요.";
                            break;

                        case "801":
                            result = "약간의 구름이 보이지만 좋은 날씨에요.";
                            break;

                        case "802":
                            result = "구름이 여기저기 보이는 흐린 날씨에요.";
                            break;

                        case "803":
                            result = "구름이 짙고 날씨가 흐려요.";
                            break;

                        case "804":
                            result = "강한 구름이 발달한 아주 흐린 날씨에요.";
                            break;
                    }
            }
        }

        return result;
    }
}
