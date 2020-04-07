package com.example.cryptobag;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cryptobag.Entities.Coin;
import com.example.cryptobag.Entities.CoinLoreResponse;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private boolean mTwoPane;
    private CoinAdapter mAdapter;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.detail_container) != null) {
            mTwoPane = true;
        }

        RecyclerView mRecyclerView = findViewById(R.id.rvList);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);




//        Gson gson = new Gson();
        //This uses hardcoded json output for the data we took from the API library
        //CoinLoreResponse response = gson.fromJson(CoinLoreResponse.json, CoinLoreResponse.class);
        //List<Coin> coins = response.getData();
        mAdapter = new CoinAdapter(this, new ArrayList<Coin>(), mTwoPane);
        mRecyclerView.setAdapter(mAdapter);

//        try{
            //Create Retrofit instance and parse the retrieved Json using the Gson deserialiser
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.coinLore.net").addConverterFactory(GsonConverterFactory.create()).build();

            //Getting the service and call object for the request
            CoinService service = retrofit.create(CoinService.class);
            Call<CoinLoreResponse> coinsCall = service.getCoins();

            //Execute the network request
//            Response<CoinLoreResponse> coinsResponse = coinsCall.execute();
            //getData is from the CoinLoreResponse class
//            List<Coin> coins = coinsResponse.body().getData();

            coinsCall.enqueue(new Callback<CoinLoreResponse>(){
                @Override
                public void onResponse(Call<CoinLoreResponse> call, Response<CoinLoreResponse> response){
                    Log.d(TAG, "OnResponse: SUCCESS");
                    List<Coin> coins = response.body().getData();
                    mAdapter.setCoins(coins);
                }
                @Override
                public void onFailure(Call<CoinLoreResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: FAILURE");
                }

            });
//        } catch (IOException e){
//            e.printStackTrace();
//        }

    }
}