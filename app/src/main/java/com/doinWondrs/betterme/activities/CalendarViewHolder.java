package com.doinWondrs.betterme.activities;
import android.view.View;
import android.widget.TextView;

import com.doinWondrs.betterme.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private final ArrayList<LocalDate> days;
    public final View parentView;
    public final TextView dayOfMonth;
    public final TextView calendarDate;
    private final CalendarAdapter.OnItemListener onItemListener;
    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener, ArrayList<LocalDate> days) {
        super(itemView);
        parentView = itemView.findViewById(R.id.parentView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        calendarDate = itemView.findViewById(R.id.calendarRelation);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
        this.days = days;
    }

    @Override
    public void onClick(View view) {
        onItemListener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));
    }
}
