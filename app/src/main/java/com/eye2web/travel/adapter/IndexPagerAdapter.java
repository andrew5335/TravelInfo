package com.eye2web.travel.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eye2web.travel.R;
import com.squareup.picasso.Picasso;

import java.util.List;

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
    private List<String> cityList;
    private View view;
    private ImageView imageView;
    private String imageUrl;

    public IndexPagerAdapter(Context ctx, LayoutInflater inflater, List<String> cityList) {
        this.ctx = ctx;
        this.inflater = inflater;
        this.cityList = cityList;
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //CardView cardView = new CardView(ctx);
        //return cardView;
        //Toast.makeText(container.getContext(), "position : " + position, Toast.LENGTH_LONG).show();

        imageUrl = ctx.getResources().getString(R.string.image_url);
        imageUrl = imageUrl + "/images/indexmenu/";

        // position 값을 기준으로 화면 변경
        switch (position) {
            case 0 :
                view = inflater.inflate(R.layout.indexmenu1, null);
                imageView = view.findViewById(R.id.seoul_btn);
                Picasso.get().load(imageUrl + "seoul.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case 1 :
                view = inflater.inflate(R.layout.indexmenu2, null);
                imageView = view.findViewById(R.id.incheon_btn);
                Picasso.get().load(imageUrl + "incheon.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case 2 :
                view = inflater.inflate(R.layout.indexmenu3, null);
                imageView = view.findViewById(R.id.daejeon_btn);
                Picasso.get().load(imageUrl + "daejeon.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case 3 :
                view = inflater.inflate(R.layout.indexmenu4, null);
                imageView = view.findViewById(R.id.daegu_btn);
                Picasso.get().load(imageUrl + "daegu.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case 4 :
                view = inflater.inflate(R.layout.indexmenu5, null);
                imageView = view.findViewById(R.id.busan_btn);
                Picasso.get().load(imageUrl + "busan.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case 5 :
                view = inflater.inflate(R.layout.indexmenu6, null);
                imageView = view.findViewById(R.id.kwangju_btn);
                Picasso.get().load(imageUrl + "kwangju.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case 6 :
                view = inflater.inflate(R.layout.indexmenu7, null);
                imageView = view.findViewById(R.id.ulsan_btn);
                Picasso.get().load(imageUrl + "ulsan.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case 7 :
                view = inflater.inflate(R.layout.indexmenu8, null);
                imageView = view.findViewById(R.id.sejong_btn);
                Picasso.get().load(imageUrl + "sejong.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case 8 :
                view = inflater.inflate(R.layout.indexmenu9, null);
                imageView = view.findViewById(R.id.kyunggi_btn);
                Picasso.get().load(imageUrl + "kyunggi2.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case 9 :
                view = inflater.inflate(R.layout.indexmenu10, null);
                imageView = view.findViewById(R.id.kangwon_btn);
                Picasso.get().load(imageUrl + "kangwon.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case 10 :
                view = inflater.inflate(R.layout.indexmenu11, null);
                imageView = view.findViewById(R.id.chungbook_btn);
                Picasso.get().load(imageUrl + "chungbook.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case 11 :
                view = inflater.inflate(R.layout.indexmenu12, null);
                imageView = view.findViewById(R.id.chungnam_btn);
                Picasso.get().load(imageUrl + "chungnam.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case 12 :
                view = inflater.inflate(R.layout.indexmenu13, null);
                imageView = view.findViewById(R.id.jeonbook_btn);
                Picasso.get().load(imageUrl + "jeonbook.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case 13 :
                view = inflater.inflate(R.layout.indexmenu14, null);
                imageView = view.findViewById(R.id.jeonnam_btn);
                Picasso.get().load(imageUrl + "jeonnam.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case 14 :
                view = inflater.inflate(R.layout.indexmenu15, null);
                imageView = view.findViewById(R.id.kyungbook_btn);
                Picasso.get().load(imageUrl + "kyungbook.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case 15 :
                view = inflater.inflate(R.layout.indexmenu16, null);
                imageView = view.findViewById(R.id.kyungnam_btn);
                Picasso.get().load(imageUrl + "kyungnam.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            case 16 :
                view = inflater.inflate(R.layout.indexmenu17, null);
                imageView = view.findViewById(R.id.jeju_btn);
                Picasso.get().load(imageUrl + "jeju.jpg").placeholder(R.mipmap.logo_final).into(imageView);
                break;

            default :
                view = inflater.inflate(R.layout.indexmenu1, null);
                imageView = view.findViewById(R.id.incheon_btn);
                Picasso.get().load(imageUrl + "incheon.jpg").placeholder(R.mipmap.logo_final).into(imageView);
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