package com.rakshakavach.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rakshakavach.R;
import com.rakshakavach.model.WorkTask;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    public interface OnTaskSelectedListener {
        void onTaskSelected(String taskId);
    }

    private List<WorkTask> tasks;
    private String selectedId;
    private final OnTaskSelectedListener listener;

    public TaskAdapter(List<WorkTask> tasks, String selectedId, OnTaskSelectedListener listener) {
        this.tasks = new ArrayList<>(tasks);
        this.selectedId = selectedId;
        this.listener = listener;
    }

    public void updateTasks(List<WorkTask> newTasks) {
        tasks = new ArrayList<>(newTasks);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        WorkTask task = tasks.get(position);
        h.tvEmoji.setText(task.getEmoji());
        h.tvName.setText(task.getName());

        String riskLevel = task.getRiskLevel();
        h.tvRisk.setText("Risk: " + riskLevel);
        switch (riskLevel) {
            case "LOW":      h.tvRisk.setTextColor(Color.parseColor("#4CAF50")); break;
            case "MEDIUM":   h.tvRisk.setTextColor(Color.parseColor("#FF9800")); break;
            case "HIGH":     h.tvRisk.setTextColor(Color.parseColor("#F44336")); break;
            case "CRITICAL": h.tvRisk.setTextColor(Color.parseColor("#B71C1C")); break;
            default:         h.tvRisk.setTextColor(Color.GRAY);
        }

        boolean isSelected = task.getId().equals(selectedId);
        h.ivSelected.setVisibility(isSelected ? View.VISIBLE : View.GONE);
        h.card.setCardBackgroundColor(isSelected
                ? Color.parseColor("#2A2A00")
                : Color.parseColor("#2C2C2C"));

        h.itemView.setOnClickListener(v -> {
            selectedId = task.getId();
            listener.onTaskSelected(selectedId);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() { return tasks.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView tvEmoji, tvName, tvRisk;
        ImageView ivSelected;

        ViewHolder(View v) {
            super(v);
            card = (CardView) v;
            tvEmoji = v.findViewById(R.id.tv_task_emoji);
            tvName = v.findViewById(R.id.tv_task_name);
            tvRisk = v.findViewById(R.id.tv_task_risk);
            ivSelected = v.findViewById(R.id.iv_selected);
        }
    }
}
