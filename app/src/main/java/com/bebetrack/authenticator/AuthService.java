package com.bebetrack.authenticator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;


public class AuthService extends Service {



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        AccountAuthStuff authStuff = new AccountAuthStuff(this);
        return authStuff.getIBinder();
    }
}
