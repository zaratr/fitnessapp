package com.doinWondrs.betterme.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;




import com.amplifyframework.core.Amplify;
import com.doinWondrs.betterme.R;

import java.io.IOException;

public class LogInActivity extends AppCompatActivity {
    public final String TAG = "LoginActivity";
    //field declaration
    private SurfaceView surfaceView;
    private MediaPlayer player;
    private SurfaceHolder holder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_log_in);
        delayIcon();
        renderBackground2();
        setUpLoginBtn();
        setUpSignInBtn();
        setUpVerify();
    }

    private void setUpVerify() {
        TextView verify = findViewById(R.id.verifyText);
        verify.setOnClickListener(v -> {
            Intent goToVerify = new Intent(LogInActivity.this, VerifyUserActivity.class);
            startActivity(goToVerify);
        });

    }

    // RR: Takes in User input to login
    public void setUpLoginBtn() {
        Button loginBtn = findViewById(R.id.signinButton);
        EditText loginEmail = findViewById(R.id.loginTextEmailAddress);
        EditText loginPassword = findViewById(R.id.signupTextPassword);

        loginBtn.setOnClickListener(v -> {
            String userEmail = loginEmail.getText().toString();
            String userPassword = loginPassword.getText().toString();

            Amplify.Auth.signIn(
                    userEmail,
                    userPassword,

                    success -> {
                        Log.i(TAG, "Successfully Logged-in");
                        Intent goHome = new Intent(LogInActivity.this, MainActivity.class);
                        startActivity(goHome);
                    },

                    fail -> {
                        Log.e(TAG, "Failed Login :" + fail);

                        runOnUiThread(() ->
                        {
                            Toast.makeText(LogInActivity.this, "LOGIN Failed!", Toast.LENGTH_SHORT).show();
                        });

                    }
            );
        });


    }

    // RR: Directs to SignUp Page
    public void setUpSignInBtn() {
        Button signUpBtn = findViewById(R.id.loginSignUpButton);

        signUpBtn.setOnClickListener(v -> {
            Intent goSignup = new Intent(LogInActivity.this, SignUpActivity.class);

            startActivity(goSignup);
        });
    }


    private void delayIcon()
    {
        ImageView iconDelayer = findViewById(R.id.iconMainActivity2);
        int imagePath = R.drawable.icon1;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iconDelayer.setImageResource(imagePath);

            }
        }, 3000);
    }


//    private void renderBackground()
//    {
//        final VideoView videoView = (VideoView) findViewById(R.id.videoBackground1);
//        final String videopath = Uri.parse("android.resource://"+getPackageName() + "/" + R.raw.workout_intro).toString();
//        videoView.setVideoPath(videopath);
//        videoView.start();
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                mediaPlayer.start();
//                mediaPlayer.setLooping(true);
//            }
//        });
//        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer)
//            {
//                videoView.setVideoPath(videopath);
//                videoView.start();
//            }
//        });
//    }

    private void renderBackground2()
    {

        //NOTES :  https://developpaper.com/android-uses-surfaceview-mediaplayer-to-play-video/
        //NOTES : https://itecnote.com/tecnote/java-full-screen-videoview-without-stretching-the-video/
        surfaceView = (SurfaceView) findViewById(R.id.videoBackgroundLogin);
        player = new MediaPlayer();
        try {
            player.setDataSource(this, Uri.parse("android.resource://"+getPackageName() + "/" + R.raw.workout_intro_portrait1) );
            holder= surfaceView.getHolder();
            holder.addCallback(new MyCallBack());
            player.prepare();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {


                    // so it fits on the screen
                    int videoWidth = player.getVideoWidth();
                    int videoHeight = player.getVideoHeight();
                    float videoProportion = (float) videoWidth / (float) videoHeight;
                    int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
                    int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
                    float screenProportion = (float) screenWidth / (float) screenHeight;
                    android.view.ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();

                    if (videoProportion > screenProportion) {
                        lp.width = 1/2 * videoWidth;
                        lp.height =  1/2 * videoHeight;
//                        lp.width = screenWidth;
//                        lp.height = (int) ((float) screenWidth / videoProportion);
                    } else {
                        lp.width = (int) (videoProportion * (float) screenHeight);
                        lp.height = screenHeight;
                    }
                    surfaceView.setLayoutParams(lp);

                    player.start();
                    player.setLooping(true);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private class MyCallBack implements SurfaceHolder.Callback{

        @Override
        public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
            player.setDisplay(holder);
        }

        @Override
        public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        }
    }//END: class MyCallback - implements sufaceholder.callback

}