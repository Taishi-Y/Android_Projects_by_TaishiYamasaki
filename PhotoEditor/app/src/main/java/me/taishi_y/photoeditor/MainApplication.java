package me.taishi_y.photoeditor;

import android.app.Application;

import com.adobe.creativesdk.aviary.IAviaryClientCredentials;
import com.adobe.creativesdk.foundation.AdobeCSDKFoundation;
import com.adobe.creativesdk.foundation.auth.IAdobeAuthClientCredentials;

/**
 * Created by yamasakitaishi on 2016/03/20.
 */
//public class MainApplication {
//}
public class MainApplication extends Application implements IAviaryClientCredentials {

    /* Be sure to fill in the two strings below. */
    private static final String CREATIVE_SDK_CLIENT_ID = "dbec27b0d19849d9906f277b81c5d42c";
    private static final String CREATIVE_SDK_CLIENT_SECRET = "080f41be-72ae-4641-8f3d-77c0c76f2cd1";

    @Override
    public void onCreate() {
        super.onCreate();
        AdobeCSDKFoundation.initializeCSDKFoundation(getApplicationContext());
    }

    @Override
    public String getClientID() {
        return CREATIVE_SDK_CLIENT_ID;
    }

    @Override
    public String getClientSecret() {
        return CREATIVE_SDK_CLIENT_SECRET;
    }

    /* 2) Add the getBillingKey() method */
    @Override
    public String getBillingKey() {
        return ""; // Leave this blank
    }
}
