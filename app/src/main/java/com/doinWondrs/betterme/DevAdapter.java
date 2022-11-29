package com.doinWondrs.betterme;

import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DevAdapter extends RecyclerView.Adapter<DevAdapter.ViewHolder> {

    private Context context;
    private final ArrayList<DevModel> devModelArrayList;

    public DevAdapter(Context context, ArrayList<DevModel> devModelArrayList) {
        this.context = context;
        this.devModelArrayList = devModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_card_layout, parent, false);
        return new ViewHolder(view);
    }

    //Grabs the holders views and sets the dev information
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DevModel dev = devModelArrayList.get(position);
        holder.devNameView.setText(dev.getDevName());
        holder.devImgView.setImageResource(dev.getDevImg());
        holder.devLinkedInView.setText(dev.getDevLinkedIn());
        holder.devGitHubView.setText(dev.getDevGitHub());
        holder.devQuote.setText(dev.getDevQuote());

        // OnClickListener set to show or hide the hidden information
        holder.arrow.setOnClickListener(v ->{
            if(holder.hiddenView.getVisibility() == View.VISIBLE){
                TransitionManager.beginDelayedTransition(holder.cardView,
                        new AutoTransition());
                holder.hiddenView.setVisibility(View.GONE);
                holder.arrow.setImageResource(R.drawable.ic_baseline_expand_more_24);
            } else {

                TransitionManager.beginDelayedTransition(holder.cardView,
                        new AutoTransition());
                holder.hiddenView.setVisibility(View.VISIBLE);
                holder.arrow.setImageResource(R.drawable.ic_baseline_expand_less_24);
            }
        });

    }

    @Override
    public int getItemCount() {
        return devModelArrayList.size();
    }

    // Grabs the all Views that need to be edited and associates it to the holder
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView devImgView;
        private final TextView devNameView;
        private final TextView devLinkedInView;
        private final TextView devGitHubView;
        private final CardView cardView;
        private final LinearLayout hiddenView;
        private final ImageButton arrow;
        private final TextView devQuote;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            devImgView = itemView.findViewById(R.id.devPhoto);
            devNameView = itemView.findViewById(R.id.devName);
            devLinkedInView = itemView.findViewById(R.id.linkedInTextView);
            devGitHubView = itemView.findViewById(R.id.gitHubTextView);
            cardView = itemView.findViewById(R.id.profileCard);
            arrow = itemView.findViewById(R.id.arrow_button);
            hiddenView = itemView.findViewById(R.id.hidden_view);
            devQuote = itemView.findViewById(R.id.devQuote);
        }
    }
}
