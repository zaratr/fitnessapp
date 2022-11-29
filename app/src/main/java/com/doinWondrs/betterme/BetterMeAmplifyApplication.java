package com.doinWondrs.betterme;

import android.app.Application;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

public class BetterMeAmplifyApplication extends Application {

    public static final String TAG = "BETTERME APPLICATION";

    @Override
    public void onCreate() {
        super.onCreate();
        try{
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
        } catch (AmplifyException aEx) {
            Log.e(TAG,"Error with initializing Amplify" + aEx.getMessage());
        }
    }
}
