package com.example.cryptobag;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;

public class DetailFragment extends Fragment {
    private TextView mName;
    private TextView mSymbol;
    private TextView mValue;
    private TextView mChange1h;
    private TextView mChange24h;
    private TextView mChange7d;
    private TextView mMarketcap;
    private TextView mVolume;
    private ImageView mSearch;

    public static final String ARG_ITEM_ID = "item_id";
    private Coin mCoin;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailFragment() {

        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mCoin = Coin.getCoin(getArguments().getString(ARG_ITEM_ID));
            this.getActivity().setTitle(mCoin.getName());
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container,false);
        int position = 0;
        Boolean mTwoPane = false;

        mName = rootView.findViewById(R.id.tvName);
        mSymbol = rootView.findViewById(R.id.tvSymbol);
        mValue = rootView.findViewById(R.id.tvValueField);
        mChange1h = rootView.findViewById(R.id.tvChange1hField);
        mChange24h = rootView.findViewById(R.id.tvChange24hField);
        mChange7d = rootView.findViewById(R.id.tvChange7dField);
        mMarketcap = rootView.findViewById(R.id.tvMarketcapField);
        mVolume = rootView.findViewById(R.id.tvVolumeField);
        mSearch = rootView.findViewById(R.id.ivSearch);

        if(this.getArguments() != null){
            mTwoPane = getArguments().getBoolean("position", true);
        }
        if(mTwoPane){
            mCoin = Coin.getCoins().get(getArguments().getInt("position"));
        } else {
            Intent intent = getActivity().getIntent();
            position = intent.getIntExtra("position", 0);
            mCoin = Coin.getCoins().get(position);
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        mName.setText(mCoin.getName());
        mSymbol.setText(mCoin.getSymbol());
        mValue.setText(formatter.format(mCoin.getValue()));
        mChange1h.setText((mCoin.getChange1h()) + "%");
        mChange24h.setText((mCoin.getChange24h()) + "%");
        mChange7d.setText((mCoin.getChange7d()) + "%");
        mMarketcap.setText(formatter.format(mCoin.getMarketcap()));
        mVolume.setText(formatter.format(mCoin.getVolume()));
        mSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                searchCoin(mCoin.getName());
            }
        });

        return rootView;
    }

    private void searchCoin(String name){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + name));
        startActivity(intent);
    }


}