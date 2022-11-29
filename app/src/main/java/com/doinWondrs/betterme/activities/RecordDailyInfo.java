package com.doinWondrs.betterme.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.DailyInfo;
import com.amplifyframework.datastore.generated.model.User;
import com.doinWondrs.betterme.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RecordDailyInfo extends AppCompatActivity {
    private static final String TAG = "dailyinfoactivity";
    private CompletableFuture<User> userFuture;
    private CompletableFuture<List<DailyInfo>> dailyInfoListFuture;
    private List<DailyInfo> dailyInfoList = new ArrayList<>();

    private DailyInfo dailyInfo; // for completable Future
    private User userInfo;
    private String date;
    private String userEmail = null;
    private String userNickName = null;
    private HashMap<String, DailyInfo> mapOfInfo;
    EditText weightInfo;
    TextView bmi;
    EditText currentCalories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_daily_info);
        mapOfInfo = new HashMap<>();
        dailyInfoListFuture = new CompletableFuture<>();

        getDailyInfo();
        getUserAttributes();
        getUser();
        fillUserInfo();
        calcBmi();
        grabDateAndSet();
        createOrUpdate();
        navGoTo();
    }

    private void fillUserInfo(){
        try {
            userInfo = userFuture.get();
        } catch (InterruptedException ie) {
            Log.e(TAG, "InterruptedException while getting product");
            Thread.currentThread().interrupt();
        } catch (ExecutionException ee) {
            Log.e(TAG, "ExecutionException while getting product");
        }
    }

    private void getUserAttributes() {
        Amplify.Auth.fetchUserAttributes(
                success -> {
                    for (AuthUserAttribute attribute : success) {
                        if (attribute.getKey().getKeyString().equals("email")) {
                            userEmail = attribute.getValue();
                        }
                        if (attribute.getKey().getKeyString().equals("nickname")) {
                            userNickName = attribute.getValue();
                        }
                    }
                },
                error -> Log.e(TAG, "failed")
        );
    }

    private void getUser() {
        userFuture = new CompletableFuture<>();
        Amplify.API.query(
                ModelQuery.list(User.class),
                success -> {
                    Log.i(TAG, "Read Users successfully");
                    for (User user : success.getData()) {
                        if (user.getUsername().equals(userNickName)) {
                            userFuture.complete(user);
                        }
                    }
                },
                failure -> Log.i(TAG, "Failed to read Users")
        );
    }

    private void getDailyInfo() {
        // create API query

        Amplify.API.query(
                ModelQuery.list(DailyInfo.class),
                onSuccess -> {
                    for (DailyInfo info : onSuccess.getData()) {
                        dailyInfoList.add(info);
                    }
                    dailyInfoListFuture.complete(dailyInfoList);
                    Log.i(TAG, "Read DailyInfo successfully.");
                },
                onFailure -> Log.e(TAG, "Failed to read DailyInfo.")
        );
    }

    private void fillDailyInfoHashmap(){
        try {
            dailyInfoList = dailyInfoListFuture.get();

            for (DailyInfo info : dailyInfoList) {
                if (!mapOfInfo.containsKey(info.getCalendarDate()) && info.getUser().equals(userInfo)) {
                    mapOfInfo.put(info.getCalendarDate(), info);
                }
            }
        } catch (InterruptedException ie) {
            Log.e(TAG, "InterruptedException while getting teams");
            Thread.currentThread().interrupt();
        } catch (ExecutionException ee) {
            Log.e(TAG, "ExecutionException while getting teams");
        }
    }

    private void createOrUpdate() {
        fillDailyInfoHashmap();
        if (mapOfInfo.containsKey(date)) {
            // we have a dailyinfo
            infoUpdate(mapOfInfo.get(date));
        } else {
            infoCreate();
        }
    }

    private void infoUpdate(DailyInfo todaysInfo) {
        Button saveBtn = findViewById(R.id.infoSaveBtn);
        String dateCreation = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());
        String finalDate = date;

        // set values into UI textboxes
        weightInfo = findViewById(R.id.inforCurrentWeightInput);
        bmi = findViewById(R.id.infoBmiInput);
        currentCalories = findViewById(R.id.infoConsumedCalories);
        DailyInfo currentInfo = mapOfInfo.get(date);

        if(currentInfo.getWeight() != null && currentInfo.getBmi() != null && currentInfo.getCurrentCalorie() != null) {
            weightInfo.setText(currentInfo.getWeight().toString());
            bmi.setText(currentInfo.getBmi().toString());
            currentCalories.setText(currentInfo.getCurrentCalorie().toString());
        }

        saveBtn.setOnClickListener(v -> {
            weightInfo = findViewById(R.id.inforCurrentWeightInput);
            bmi = findViewById(R.id.infoBmiInput);
            currentCalories = findViewById(R.id.infoConsumedCalories);
            if(TextUtils.isEmpty(weightInfo.getText()) || TextUtils.isEmpty(bmi.getText()) || TextUtils.isEmpty(currentCalories.getText())){
                Toast.makeText(RecordDailyInfo.this, "Please Fill Out All Fields", Toast.LENGTH_SHORT).show();
            } else {
                DailyInfo newDailyInfo = DailyInfo.builder()
                        .id(todaysInfo.getId())
                        .user(userInfo)
                        .calendarDate(finalDate)
                        .weight(Integer.parseInt(weightInfo.getText().toString()))
                        .bmi(Integer.parseInt(bmi.getText().toString()))
                        .currentCalorie(Integer.parseInt(currentCalories.getText().toString()))
                        .dateCreated(new Temporal.DateTime(dateCreation))
                        .build();

                Amplify.API.mutate(
                        ModelMutation.update(newDailyInfo),
                        success -> {
                            Log.i(TAG, "Daily Info Updated Successfully");
                        },
                        failure -> Log.e(TAG, "Daily Info Creation Failed" + failure.getMessage())
                );

                Toast.makeText(RecordDailyInfo.this,
                        "Daily info was updated.",
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

    private void calcBmi() {
        Button calcBtn = findViewById(R.id.infoCalcBmiBtn);
        calcBtn.setOnClickListener(v -> {
            TextView bmi = findViewById(R.id.infoBmiInput);
            EditText weightInfo = findViewById(R.id.inforCurrentWeightInput);
            if(userInfo.getHeight() == null || TextUtils.isEmpty(weightInfo.getText())){
                Toast.makeText(RecordDailyInfo.this, "Please make sure you have set your height and weight", Toast.LENGTH_SHORT).show();
            } else {
                double weightNum = Double.parseDouble(weightInfo.getText().toString());
                int height = userInfo.getHeight();
                int userBmi = (int) ((weightNum / (height * height)) * 703);
                bmi.setText(String.valueOf(userBmi));
            }
        });
    }

    private void grabDateAndSet() {

        Intent callingIntent = getIntent();
        date = "";
        if (callingIntent != null) {
            date = callingIntent.getStringExtra(CalendarActivity.CALENDAR_DATE);
        }
        TextView dateView = findViewById(R.id.dateOfDailyInfo);
        dateView.setText(date);
    }

    private void infoCreate() {
        Button saveBtn = findViewById(R.id.infoSaveBtn);
        String dateCreation = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());
        String finalDate = date;
        saveBtn.setOnClickListener(v -> {
            EditText weightInfo = findViewById(R.id.inforCurrentWeightInput);
            TextView bmi = findViewById(R.id.infoBmiInput);
            currentCalories = findViewById(R.id.infoConsumedCalories);
            if(TextUtils.isEmpty(weightInfo.getText()) || TextUtils.isEmpty(bmi.getText()) || TextUtils.isEmpty(currentCalories.getText())){
                Toast.makeText(RecordDailyInfo.this, "Please Fill Out All Fields", Toast.LENGTH_SHORT).show();
            } else {
                DailyInfo newDailyInfo = DailyInfo.builder()
                        .user(userInfo)
                        .calendarDate(finalDate)
                        .weight(Integer.parseInt(weightInfo.getText().toString()))
                        .bmi(Integer.parseInt(bmi.getText().toString()))
                        .currentCalorie(Integer.parseInt(currentCalories.getText().toString()))
                        .dateCreated(new Temporal.DateTime(dateCreation))
                        .build();

                Amplify.API.mutate(
                        ModelMutation.create(newDailyInfo),
                        success -> Log.i(TAG, "Daily Info Created Successfully" + success),
                        failure -> Log.e(TAG, "Daily Info Creation Failed" + failure.getMessage())
                );

                Toast.makeText(RecordDailyInfo.this,
                        "Daily info was saved!",
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

    //Bottom Navbar: NOTE: to link new activity just create a new switch cases and use new intents
    //EXCEPT if you are at workoutpagefirst.java then you dont need to do anything just break out of switch case.
    public void navGoTo() {
        //NOTES: https://www.geeksforgeeks.org/how-to-implement-bottom-navigation-with-activities-in-android/
        //NOTES: bottomnavbar is deprecated: https://developer.android.com/reference/com/google/android/material/bottomnavigation/BottomNavigationView.OnNavigationItemSelectedListener

        //initialize, instantiate
        NavigationBarView navigationBarView;//new way to do nav's but more research needed
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //set home selected: home
        bottomNavigationView.setSelectedItemId(R.id.home_nav);
        //perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_nav:
                        //we are here right now
                        break;
                    case R.id.calendar_nav:
                        startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.gps_nav:
                        startActivity(new Intent(getApplicationContext(), GPSActivity.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.workouts_nav:
                        startActivity(new Intent(getApplicationContext(), WorkoutPageFirst.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.settings_nav:
                        startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
                        overridePendingTransition(0, 0);
                        break;
                    default:
                        return false;// this is to cover all other cases if not working properly
                }

                return true;
            }
        });//end lambda: bottomNavview
    }//end method: navGoTo

}