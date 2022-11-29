package com.doinWondrs.betterme.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.doinWondrs.betterme.DevAdapter;
import com.doinWondrs.betterme.DevModel;
import com.doinWondrs.betterme.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class AboutUsActivity extends AppCompatActivity {

    private RecyclerView devRV;
    private ArrayList<DevModel> devModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        devRV = findViewById(R.id.aboutUsRecView);

        setDevInfoAndAdapter();
        navGoTo();
    }

    private void setDevInfoAndAdapter(){
        devModelArrayList = new ArrayList<>();
        devModelArrayList.add(new DevModel("Raul Zarate",
                "https://www.linkedin.com/in/raul-zarate/",
                "https://github.com/zaratr",
                R.drawable.raulpic,
                "Git to the Choppa!"));
        devModelArrayList.add(new DevModel("Chuck Altopiedi",
                "https://www.linkedin.com/in/chuckaltopiedi/",
                "https://github.com/ChuckAlto",
                R.drawable.chuckpic,
                "\"Sometimes it is the people no one can imagine anything of who do the things no one can imagine.- Alan Turing\""));
        devModelArrayList.add(new DevModel("Jon Rumsey",
                "https://www.linkedin.com/in/jonathan-rumsey-wa/",
                "https://github.com/nojronatron",
                R.drawable.jonrumsey,
                "Full Stack Software Developer"));
        devModelArrayList.add(new DevModel("Roger Reyes",
                "https://www.linkedin.com/in/rogermreyes/",
                "https://github.com/RogerMReyes",
                R.drawable.rogerpic,
                "\"I have failed a thousand times, and I will continue to fail. Everytime stronger than the last\""));
        devModelArrayList.add(new DevModel("Michael Brunette",
                "https://www.linkedin.com/in/michael-brunette-0800b5233/",
                "https://github.com/mcbrunette33",
                R.drawable.mike_pic,
                "\"If you\'re not first, you\'re last\" - Ricky Bobby"));
        devModelArrayList.add(new DevModel("Abdulahi Mohamud",
                "https://www.linkedin.com/in/abdulahimmohamud/",
                "https://github.com/AbdulahiMohamud",
                R.drawable.abdulahi_pic,
                "\"And I knew exactly what to do. But in a much more real sense, I had no idea what to do.\" – Michael Scott"));
        devModelArrayList.add(new DevModel("Jason Wilson",
                "https://www.linkedin.com/in/jason-wilson-5b5baa1a8/",
                "https://github.com/WilsonJhub",
                R.drawable.jasonpic,
                "\"Your actions can be no wiser than your thoughts. Think DEEPLY on this.\""
        ));

        DevAdapter devAdapter = new DevAdapter(this,devModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        devRV.setLayoutManager(linearLayoutManager);
        devRV.setAdapter(devAdapter);
    }

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
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
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
                        break;
                    default:
                        return false;// this is to cover all other cases if not working properly
                }

                return true;
            }
        });
    }

}