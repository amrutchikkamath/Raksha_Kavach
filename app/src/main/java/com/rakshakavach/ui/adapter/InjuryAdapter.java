package com.rakshakavach.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rakshakavach.R;
import com.rakshakavach.model.SafetyGear;

import java.util.List;

public class InjuryAdapter extends RecyclerView.Adapter<InjuryAdapter.ViewHolder> {

    private final List<SafetyGear> gearList;

    public InjuryAdapter(List<SafetyGear> gearList) {
        this.gearList = gearList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_injury, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        SafetyGear gear = gearList.get(position);
        h.tvGear.setText("Without " + gear.getName() + ":");
        h.tvInjury.setText(gear.getInjuryIfIgnored());
    }

    @Override
    public int getItemCount() { return gearList.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvGear, tvInjury;

        ViewHolder(View v) {
            super(v);
            tvGear = v.findViewById(R.id.tv_injury_gear);
            tvInjury = v.findViewById(R.id.tv_injury_desc);
        }
    }
}
