package com.doinWondrs.betterme.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.DailyInfo;
import com.doinWondrs.betterme.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>{
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;
    private final Context callingActivity;

    public CalendarAdapter(ArrayList<LocalDate> days,
                           OnItemListener onItemListener,
                           Context callingActivity
    ) {
        this.days = days;
        this.onItemListener = onItemListener;
        this.callingActivity = callingActivity;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(days.size() > 15)
            layoutParams.height = (int) (parent.getHeight() * 0.14555555555);
        else
            layoutParams.height = (int) parent.getHeight();


        return new CalendarViewHolder(view, onItemListener, days);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        final LocalDate date = days.get(position);

        if (date ==null)
            holder.dayOfMonth.setText("");
        else{
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            holder.calendarDate.setText(date.toString());

            holder.dayOfMonth.setOnClickListener(v -> {
                Intent goToCreateDaily = new Intent(callingActivity, RecordDailyInfo.class);
                goToCreateDaily.putExtra(CalendarActivity.CALENDAR_DATE, date.toString());
                callingActivity.startActivity(goToCreateDaily);
            });

            if (date.equals(CalendarUtils.selectedDate)){
                holder.parentView.setBackgroundColor(Color.LTGRAY);
            }
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public interface  OnItemListener {
        void onItemClick(int position, LocalDate date);
    }

}
