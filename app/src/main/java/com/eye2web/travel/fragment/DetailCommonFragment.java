package com.eye2web.travel.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eye2web.travel.R;
import com.eye2web.travel.adapter.DetailImageViewPagerAdapter;
import com.eye2web.travel.util.CommonUtil;
import com.eye2web.travel.vo.DetailCommonItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailCommonFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailCommonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailCommonFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View view;
    private DetailCommonItem detailCommonItem;
    private CommonUtil commonUtil;

    private ViewPager imageViewPager;
    private DetailImageViewPagerAdapter detailImageViewPagerAdapter;

    public DetailCommonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailCommonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailCommonFragment newInstance(String param1, String param2) {
        DetailCommonFragment fragment = new DetailCommonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
     **/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view != null) {
            ViewGroup viewGroupParent = (ViewGroup) view.getParent();
            if(viewGroupParent != null) {
                viewGroupParent.removeView(view);
            }
        }

        view = inflater.inflate(R.layout.fragment_detail_common, container, false);
        Bundle bundle = getArguments();
        DetailCommonItem detailCommonItem = (DetailCommonItem) bundle.getSerializable("detailCommonItem");

        String overView = "";
        String homepage = "";
        String addr1 = "";
        String addr2 = "";
        String firstImage = "";
        String title = "";
        String mapAddr = "";
        List<String> imgUrlList = new ArrayList<String>();
        //String firstImage2 = "";

        title = (String) detailCommonItem.getTitle();
        overView = detailCommonItem.getOverview();
        homepage = detailCommonItem.getHomepage();
        addr1 = detailCommonItem.getAddr1();
        addr2 = detailCommonItem.getAddr2();
        mapAddr = addr1 + " " + addr2;
        imgUrlList = (List<String>) detailCommonItem.getImgUrlList();
        firstImage = detailCommonItem.getFirstimage();
        //return inflater.inflate(R.layout.fragment_detail_common, container, false);
        TextView detailOverView = (TextView) view.findViewById(R.id.detailOverView);
        TextView detailAddr1 = (TextView) view.findViewById(R.id.detailAddr1);
        TextView detailAddr2 = (TextView) view.findViewById(R.id.detailAddr2);
        TextView detailHomepage = (TextView) view.findViewById(R.id.detailHomepage);
        TextView detailTitle = (TextView) view.findViewById(R.id.detailTitle);

        detailTitle.setText(title);

        commonUtil = new CommonUtil();
        //detailOverView.setText(overView);

        SpannableStringBuilder overViewBuilder = new SpannableStringBuilder();
        overViewBuilder = commonUtil.convertTxtToLink(getContext(), overView);
        if(null != overViewBuilder) { detailOverView.setText(overViewBuilder.toString()); }

        SpannableStringBuilder addr1Builder = new SpannableStringBuilder();
        addr1Builder = commonUtil.convertTxtToLink(getContext(), addr1);
        if(null != addr1Builder) { detailAddr1.setText(addr1Builder.toString()); }

        SpannableStringBuilder addr2Builder = new SpannableStringBuilder();
        addr2Builder = commonUtil.convertTxtToLink(getContext(), addr1);
        if(null != addr2Builder) { detailAddr2.setText(addr2Builder.toString()); }

        SpannableStringBuilder homePageBuilder = new SpannableStringBuilder();
        homePageBuilder = commonUtil.convertTxtToLink(getContext(), homepage);
        if(null != homePageBuilder) { detailHomepage.setText(homePageBuilder.toString()); }

        imageViewPager = (ViewPager) view.findViewById(R.id.detail_img_viewpager);
        detailImageViewPagerAdapter = new DetailImageViewPagerAdapter(getContext(), inflater, imgUrlList, firstImage);
        imageViewPager.setAdapter(detailImageViewPagerAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
