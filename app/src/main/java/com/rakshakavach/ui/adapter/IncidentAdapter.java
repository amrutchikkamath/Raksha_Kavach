package com.rakshakavach.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rakshakavach.R;
import com.rakshakavach.data.IncidentEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class IncidentAdapter extends RecyclerView.Adapter<IncidentAdapter.ViewHolder> {

    private List<IncidentEntity> incidents = new ArrayList<>();

    public void setIncidents(List<IncidentEntity> list) {
        this.incidents = list != null ? list : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_incident, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        IncidentEntity inc = incidents.get(position);
        h.tvTitle.setText(inc.title);
        h.tvDesc.setText(inc.description.isEmpty() ? "No description provided." : inc.description);
        h.tvLocation.setText("📍 " + (inc.location.isEmpty() ? "Location not specified" : inc.location));

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault());
        h.tvDate.setText(sdf.format(new Date(inc.timestamp)));

        // Severity badge with GradientDrawable
        int badgeColor;
        switch (inc.severity) {
            case "LOW":    badgeColor = Color.parseColor("#4CAF50"); break;
            case "MEDIUM": badgeColor = Color.parseColor("#FF9800"); break;
            default:       badgeColor = Color.parseColor("#F44336");
        }
        h.tvSeverity.setText(inc.severity);
        h.tvSeverity.setTextColor(Color.WHITE);

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setCornerRadius(12f);
        gd.setColor(badgeColor);
        h.tvSeverity.setBackground(gd);
    }

    @Override
    public int getItemCount() { return incidents.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc, tvDate, tvLocation, tvSeverity;

        ViewHolder(View v) {
            super(v);
            tvTitle    = v.findViewById(R.id.tv_incident_title);
            tvDesc     = v.findViewById(R.id.tv_incident_desc);
            tvDate     = v.findViewById(R.id.tv_incident_date);
            tvLocation = v.findViewById(R.id.tv_incident_location);
            tvSeverity = v.findViewById(R.id.tv_severity_badge);
        }
    }
}
