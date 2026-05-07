package com.rakshakavach.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rakshakavach.R;
import com.rakshakavach.model.SafetyGear;

import java.util.List;

public class GearStatusAdapter extends RecyclerView.Adapter<GearStatusAdapter.ViewHolder> {

    private final List<SafetyGear> gearList;

    public GearStatusAdapter(List<SafetyGear> gearList) {
        this.gearList = gearList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gear_status, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        SafetyGear gear = gearList.get(position);
        boolean checked = gear.isChecked();

        h.tvIcon.setText(gear.getEmoji());
        h.tvGear.setText(gear.getName());

        int badgeColor;
        if (checked) {
            h.tvDesc.setText("Protected ✅");
            h.tvDesc.setTextColor(Color.parseColor("#4CAF50"));
            h.tvBadge.setText("SAFE");
            badgeColor = Color.parseColor("#4CAF50");
            h.card.setCardBackgroundColor(Color.parseColor("#1A3D1A"));
        } else {
            h.tvDesc.setText("Risk: " + gear.getInjuryIfIgnored());
            h.tvDesc.setTextColor(Color.parseColor("#F44336"));
            h.tvBadge.setText("AT RISK");
            badgeColor = Color.parseColor("#F44336");
            h.card.setCardBackgroundColor(Color.parseColor("#3D1010"));
        }

        // Tint badge background using GradientDrawable
        GradientDrawable badge = new GradientDrawable();
        badge.setShape(GradientDrawable.RECTANGLE);
        badge.setCornerRadius(12f);
        badge.setColor(badgeColor);
        h.tvBadge.setBackground(badge);
        h.tvBadge.setTextColor(Color.WHITE);
    }

    @Override
    public int getItemCount() { return gearList.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView tvIcon, tvGear, tvDesc, tvBadge;

        ViewHolder(View v) {
            super(v);
            card    = v.findViewById(R.id.card_gear_status);
            tvIcon  = v.findViewById(R.id.tv_status_icon);
            tvGear  = v.findViewById(R.id.tv_status_gear);
            tvDesc  = v.findViewById(R.id.tv_status_desc);
            tvBadge = v.findViewById(R.id.tv_status_badge);
        }
    }
}
