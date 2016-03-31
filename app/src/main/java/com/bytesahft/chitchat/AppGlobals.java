package com.bytesahft.chitchat;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBSettings;
import com.quickblox.core.exception.QBResponseException;


public class AppGlobals extends Application {
    static final String APP_ID = "38323";
    static final String AUTH_KEY = "LXhRhBPa9cytrpL";
    static final String AUTH_SECRET = "haxK4wcHuuytL4r";
    static final String ACCOUNT_KEY = "MEQJD3qpqNQoTKGDA7An";
    public static Context sContext;
    public static final String USER_LOGIN_STATUS = "user_login";
    public static final String TOKEN = "token";



    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        QBSettings.getInstance().init(getApplicationContext(), APP_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
        QBAuth.createSession(new QBEntityCallback<QBSession>() {

            @Override
            public void onSuccess(QBSession session, Bundle params) {
                // You have successfully created the session
                //
                // Now you can use QuickBlox API!
                Helpers.saveStringToSP(AppGlobals.TOKEN, session.getToken());
            }

            @Override
            public void onError(QBResponseException errors) {

            }
        });
    }

    public static Context getContext() {
        return sContext;
    }
}
