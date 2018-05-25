package com.eye2web.travel.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eye2web.travel.R;

/**
 * @File : IndexPagerAdapter
 * @Date : 2018. 5. 25. PM 2:05
 * @Author : Andrew Kim
 * @Version : 1.0.0
 * @Description : 인덱스 페이지 내 인덱스 메뉴 처리를 위한 Custom Viewpager Adapter
**/
public class IndexPagerAdapter extends PagerAdapter {

    private Context ctx;
    private LayoutInflater inflater;

    public IndexPagerAdapter(Context ctx, LayoutInflater inflater) {
        this.ctx = ctx;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return Integer.parseInt(ctx.getString(R.string.indexmenu_cnt));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //CardView cardView = new CardView(ctx);
        //return cardView;
        //Toast.makeText(container.getContext(), "position : " + position, Toast.LENGTH_LONG).show();
        View view = null;

        // position 값을 기준으로 화면 변경
        switch (position) {
            case 0 :
                view = inflater.inflate(R.layout.indexmenu1, null);
                break;

            case 1 :
                view = inflater.inflate(R.layout.indexmenu2, null);
                break;

            case 2 :
                view = inflater.inflate(R.layout.indexmenu3, null);
                break;

            case 3 :
                view = inflater.inflate(R.layout.indexmenu4, null);
                break;

            case 4 :
                view = inflater.inflate(R.layout.indexmenu5, null);
                break;

            case 5 :
                view = inflater.inflate(R.layout.indexmenu6, null);
                break;

            case 6 :
                view = inflater.inflate(R.layout.indexmenu7, null);
                break;

            case 7 :
                view = inflater.inflate(R.layout.indexmenu8, null);
                break;

            default :
                view = inflater.inflate(R.layout.indexmenu1, null);
                break;

        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        ((ViewPager) container).removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}