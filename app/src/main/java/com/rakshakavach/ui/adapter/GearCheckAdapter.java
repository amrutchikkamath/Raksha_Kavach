package com.rakshakavach.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.rakshakavach.R;
import com.rakshakavach.model.SafetyGear;
import com.rakshakavach.utils.PreferenceManager;

import java.util.List;

public class GearCheckAdapter extends RecyclerView.Adapter<GearCheckAdapter.ViewHolder> {

    public interface OnGearCheckedListener {
        void onGearChecked(List<SafetyGear> updatedList);
    }

    private final List<SafetyGear> gearList;
    private final String taskId;
    private final PreferenceManager prefs;
    private final OnGearCheckedListener listener;

    public GearCheckAdapter(List<SafetyGear> gearList,
                            String taskId,
                            PreferenceManager prefs,
                            OnGearCheckedListener listener) {
        this.gearList = gearList;
        this.taskId   = taskId;
        this.prefs    = prefs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gear_check, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        SafetyGear gear = gearList.get(position);
        h.tvEmoji.setText(gear.getEmoji());
        h.tvName.setText(gear.getName());
        h.tvDesc.setText(gear.getDescription());
        applyCheckedStyle(h, gear.isChecked());

        h.btnCheck.setOnClickListener(v -> {
            boolean newState = !gear.isChecked();
            gear.setChecked(newState);

            // ── KEY FIX: persist immediately so RiskMeter can read it ──────
            prefs.setGearChecked(taskId, gear.getId(), newState);

            notifyItemChanged(position);
            listener.onGearChecked(gearList);
        });
    }

    private void applyCheckedStyle(ViewHolder h, boolean checked) {
        if (checked) {
            h.card.setCardBackgroundColor(Color.parseColor("#1A3D1A"));
            h.btnCheck.setText("✅ DONE");
            h.btnCheck.setBackgroundColor(Color.parseColor("#4CAF50"));
            h.btnCheck.setTextColor(Color.WHITE);
        } else {
            h.card.setCardBackgroundColor(Color.parseColor("#2C2C2C"));
            h.btnCheck.setText("CHECK");
            h.btnCheck.setBackgroundColor(Color.parseColor("#FFD600"));
            h.btnCheck.setTextColor(Color.parseColor("#1A1A1A"));
        }
    }

    @Override
    public int getItemCount() { return gearList.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView tvEmoji, tvName, tvDesc;
        MaterialButton btnCheck;

        ViewHolder(View v) {
            super(v);
            card     = (CardView) v;
            tvEmoji  = v.findViewById(R.id.tv_gear_emoji);
            tvName   = v.findViewById(R.id.tv_gear_name);
            tvDesc   = v.findViewById(R.id.tv_gear_desc);
            btnCheck = v.findViewById(R.id.btn_check);
        }
    }
}
