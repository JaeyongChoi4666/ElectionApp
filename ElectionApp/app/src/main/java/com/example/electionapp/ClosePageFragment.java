package com.example.electionapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClosePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClosePageFragment extends Fragment {
    private String univ_name;
    private String election_name;
    private String hbj_name;
    private String hbj_gender;
    private String hbj_grade;
    private String hbj_giho;
    private String hbj_prom1;
    private String hbj_prom2;
    private String hbj_prom3;
    private int picNum;
    private int hbj_vote;
    private int hbj_code;
    private int election_winner_code;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ClosePageFragment() {
        // Required empty public constructor
    }

    public static ClosePageFragment create(int mPagerNumber){
        ClosePageFragment fragment=new ClosePageFragment();
        Bundle args=new Bundle();
        args.putInt("page",mPagerNumber);
        fragment.setArguments(args);
        return fragment;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClosePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClosePageFragment newInstance(String param1, String param2) {
        ClosePageFragment fragment = new ClosePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            univ_name=getArguments().getString("univ_name");
            election_name=getArguments().getString("election_name");
            hbj_name=getArguments().getString("hbj_name");
            hbj_gender=getArguments().getString("hbj_gender");
            hbj_grade=getArguments().getString("hbj_grade");
            hbj_giho=getArguments().getString("hbj_giho");
            hbj_prom1=getArguments().getString("hbj_prom1");
            hbj_prom2=getArguments().getString("hbj_prom2");
            hbj_prom3=getArguments().getString("hbj_prom3");
            picNum=getArguments().getInt("picNum");
            hbj_vote=getArguments().getInt("hbj_vote");
            election_winner_code=getArguments().getInt("election_winner_code");
            hbj_code=getArguments().getInt("hbj_code");
            Log.i("받아온 투표값:",hbj_vote+"");
            Log.i("winner code:",election_winner_code+"");
            Log.i("hbj code:",hbj_code+"");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup)inflater.inflate(R.layout.fragment_close_page,container,false);
        TextView hbjNum=rootView.findViewById(R.id.hbjNum);
        TextView hbjName=rootView.findViewById(R.id.hbjNameClose);
        TextView hbjGender=rootView.findViewById(R.id.hbjGender);
        TextView hbjGrade=rootView.findViewById(R.id.hbjGrade);
        TextView hbjProm1=rootView.findViewById(R.id.hbjProm1);
        TextView hbjProm2=rootView.findViewById(R.id.hbjProm2);
        TextView hbjProm3=rootView.findViewById(R.id.hbjProm3);
        TextView hbjTotal=rootView.findViewById(R.id.hbjTotal);
        ImageView imageView=rootView.findViewById(R.id.hbjPicture);
        ImageView pic_celebrate=rootView.findViewById(R.id.pic_celebrate);
        hbjNum.setText(hbj_giho);
        hbjName.setText(hbj_name);
        hbjGender.setText(hbj_gender);
        hbjGrade.setText(hbj_grade);
        hbjProm1.setText(hbj_prom1);
        hbjProm2.setText(hbj_prom2);
        hbjProm3.setText(hbj_prom3);
        hbjTotal.setText(hbj_vote+"");

        if(election_winner_code!=hbj_code){
            pic_celebrate.setVisibility(View.GONE);
        }

        String imageurl="http://10.100.102.62:8099/image"+picNum+"?univ_name="+univ_name+"&election_name="+election_name;
        Glide.with(this).load(imageurl).into(imageView);

        return rootView;
    }
}