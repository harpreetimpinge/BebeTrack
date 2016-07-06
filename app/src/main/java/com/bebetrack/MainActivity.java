package com.bebetrack;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.bebetrack.api.RetroInCallBack;
import com.bebetrack.model.AuthData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AccountAuthenticatorActivity implements Callback<AuthData> {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.txtResponse);



        this.ApiCall();
    }

    private void ApiCall()
    {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetroInCallBack.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetroInCallBack callBack = retrofit.create(RetroInCallBack.class);


        Call<AuthData> call = callBack.getAuthData();
        //asynchronous call
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<AuthData> call, Response<AuthData> response) {
        int code = response.code();
        if (code == 200) {
            AuthData authData = response.body();
            Toast.makeText(this, "I found a token:: " + authData.getToken(), Toast.LENGTH_LONG).show();
            textView.setText(authData.getPin());

            this.setAnAccount(authData);
        } else {
            Toast.makeText(this, "WTF: " + String.valueOf(code), Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public void onFailure(Call<AuthData> call, Throwable t) {
        Toast.makeText(this, "Failure !!!", Toast.LENGTH_LONG).show();

    }


    private boolean setAnAccount(AuthData authData)
    {
        //AccountManager
        AccountManager am = AccountManager.get(this);
        //Account
        Account account = new Account(authData.getPin(), getString(R.string.account_type));
        return am.addAccountExplicitly(account, authData.getToken(), null);
    }
}
