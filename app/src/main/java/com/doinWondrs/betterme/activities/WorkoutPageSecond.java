package com.doinWondrs.betterme.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.doinWondrs.betterme.R;
import com.doinWondrs.betterme.helpers.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class WorkoutPageSecond extends AppCompatActivity {
    public static String typeWorkoutString;
    public TypesOfWorkouts workoutsHelper;
    public ArrayList<String> workoutName;
    private ArrayList<String> infoWorkouts;
    private ArrayList<String> workoutInfo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_page_second);



        //declarations
        navGoTo();//creates bottomnavbar
        typesOfWorkouts();//sets workout types
    }//END: OnCreate


    //pull all the extra's

    public void typesOfWorkouts()
    {
        //get intents extra's here
        Intent intent = getIntent();
        //use this to determine what workouts in helper
        typeWorkoutString = intent.getStringExtra(typeWorkoutString);

        //set workouts to object for later
        workoutsHelper = new TypesOfWorkouts(typeWorkoutString);

        //set arraylist for usage
        workoutName = new ArrayList<>();
        workoutInfo = new ArrayList<>();
        infoWorkouts = new ArrayList<>();

        /* ***********************
         *Logic for setting arraylist from helper class workouts
         * *******************/
        //use workout helper to get reps, sets, strings

        for(String key : workoutsHelper.workoutRoutine.keySet())
        {
            workoutName.add(key);
            workoutInfo.add(workoutsHelper.workoutRoutine.get(key));

        }

        //GET DATA from workouts helper class - pass iti to an array
        for(int i = 0 ; i < workoutInfo.size(); ++i)
        {
            for(String workoutInfoTemp : workoutInfo.get(i).split(","))
            {
                infoWorkouts.add(workoutInfoTemp);//has split data such as sets, reps, jpg, etc
            }
        }
        /* ******************
         * END: Logic for setting arraylist from helper class workouts
         ************* */

        //GET location from .xml file
        TextView workoutTitle = findViewById(R.id.textViewTest);
        ImageView workoutGif  = (ImageView) findViewById(R.id.workoutGif);
        ImageView workoutGif2 = (ImageView) findViewById(R.id.workoutGif2);
        ImageView workoutGif3 = (ImageView) findViewById(R.id.workoutGif3);

        TextView workoutTitle1 = (TextView) findViewById((R.id.workoutTitle1));
        TextView workoutTitle2 = (TextView) findViewById((R.id.workoutTitle2));
        TextView workoutTitle3 = (TextView) findViewById((R.id.workoutTitle3));

        TextView  workoutDescription1 = (TextView) findViewById((R.id.description1));
        TextView workoutDescription2 = (TextView) findViewById((R.id.description2));
        TextView workoutDescription3 = (TextView) findViewById((R.id.description3));

        TextView workoutRoutine1 = (TextView) findViewById((R.id.routine1));
        TextView workoutRoutine2 = (TextView) findViewById((R.id.routine2));
        TextView workoutRoutine3 = (TextView) findViewById((R.id.routine3));


        //int gifLocation3 = workoutsHelper.gif3;
        //GET gifs from helper
        int gifLocation1 = Integer.parseInt(infoWorkouts.get(2));
        int gifLocation2 = Integer.parseInt(infoWorkouts.get(5));
        int gifLocation3 = Integer.parseInt(infoWorkouts.get(8));

        //GET values from helper - to place to .xml

        //workout title from helper to string
        String toTextViewTitle1 = workoutName.get(0);
        String toTextViewTitle2 = workoutName.get(1);
        String toTextViewTitle3 = workoutName.get(2);

        //first workout data
        String workoutInstructions1 = infoWorkouts.get(0);
        String workoutLocation1     = infoWorkouts.get(1);
        String workoutOtherInfo1    = infoWorkouts.get(2);

        //second workout data
        String workoutInstructions2 = infoWorkouts.get(3);
        String workoutLocation2     = infoWorkouts.get(4);
        String workoutOtherInfo2    = infoWorkouts.get(5);

        //third workout data
        String workoutInstructions3 = infoWorkouts.get(6);
        String workoutLocation3     = infoWorkouts.get(7);
        String workoutOtherInfo3    = infoWorkouts.get(8);

        //SET title of page
        workoutTitle.setText(typeWorkoutString);

        //SET Text View: titles
        workoutTitle1.setText(toTextViewTitle1);
        workoutTitle2.setText(toTextViewTitle2);
        workoutTitle3.setText(toTextViewTitle3);

        //SET Text View : informations
        workoutDescription1.setText(workoutInstructions1);
        workoutRoutine1.setText(workoutLocation1);

        workoutDescription2.setText(workoutInstructions2);
        workoutRoutine2.setText(workoutLocation2);

        workoutDescription3.setText(workoutInstructions3);
        workoutRoutine3.setText(workoutLocation3);

        //SET imageView
        workoutGif.setImageResource(gifLocation1);
        workoutGif2.setImageResource(gifLocation2);
        workoutGif3.setImageResource(gifLocation3);
        //added new comment but not important here

    }//END: typesWorkouts

    public void navGoTo() {
        //initialize, instantiate
        //NavigationBarView navigationBarView;//TODO: new way to do nav's but more research needed
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //set home selected:workout_nav
        bottomNavigationView.setSelectedItemId(R.id.workouts_nav);

        //perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_nav:
                        startActivity
                                (new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.calendar_nav:
                        startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.gps_nav:
                        startActivity(new Intent(getApplicationContext(), GPSActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.workouts_nav:
                        //we are here right now
                        break;
                    case R.id.settings_nav:
                        startActivity
                                (new Intent(getApplicationContext(), UserProfileActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    default:
                        return false;// this is to cover all other cases if not working properly
                }

                return true;
            }//end method: onNavItemSelected
        });//end lambda: bottomNavview
    }//end method: navGOTO
}//END: class
