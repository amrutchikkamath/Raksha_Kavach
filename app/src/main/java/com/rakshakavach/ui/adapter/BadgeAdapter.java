package com.rakshakavach.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rakshakavach.R;
import com.rakshakavach.utils.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.ViewHolder> {

    static class Badge {
        String emoji, name, requirement;
        boolean unlocked;
        Badge(String emoji, String name, String requirement, boolean unlocked) {
            this.emoji = emoji; this.name = name;
            this.requirement = requirement; this.unlocked = unlocked;
        }
    }

    private List<Badge> badges = new ArrayList<>();

    public BadgeAdapter(PreferenceManager prefs) {
        buildBadges(prefs);
    }

    public void refresh(PreferenceManager prefs) {
        buildBadges(prefs);
        notifyDataSetChanged();
    }

    private void buildBadges(PreferenceManager prefs) {
        badges.clear();
        int score      = prefs.getSafetyScore();
        int streak     = prefs.getStreakDays();
        int checklists = prefs.getChecklistCount();
        int quizzes    = prefs.getQuizCount();

        badges.add(new Badge("🔰", "First Check",    "Complete 1 checklist",        checklists >= 1));
        badges.add(new Badge("🔥", "3-Day Streak",   "3 safe days in a row",        streak >= 3));
        badges.add(new Badge("🧠", "Quiz Starter",   "Complete 1 quiz",             quizzes >= 1));
        badges.add(new Badge("⭐", "50 Points",      "Earn 50 safety points",       score >= 50));
        badges.add(new Badge("🏅", "7-Day Warrior",  "7 safe days in a row",        streak >= 7));
        badges.add(new Badge("🎯", "Quiz Master",    "Complete 5 quizzes",          quizzes >= 5));
        badges.add(new Badge("💯", "100 Points",     "Earn 100 safety points",      score >= 100));
        badges.add(new Badge("🏆", "Safety Legend",  "Earn 500 safety points",      score >= 500));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_badge, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        Badge badge = badges.get(position);
        h.tvEmoji.setText(badge.unlocked ? badge.emoji : "🔒");
        h.tvName.setText(badge.name);
        h.tvReq.setText(badge.requirement);

        if (badge.unlocked) {
            h.card.setCardBackgroundColor(Color.parseColor("#2A2A00"));
            h.tvEmoji.setAlpha(1f);
            h.tvName.setTextColor(Color.parseColor("#FFD600"));
        } else {
            h.card.setCardBackgroundColor(Color.parseColor("#1A1A1A"));
            h.tvEmoji.setAlpha(0.35f);
            h.tvName.setTextColor(Color.parseColor("#616161"));
        }
    }

    @Override
    public int getItemCount() { return badges.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView tvEmoji, tvName, tvReq;
        ViewHolder(View v) {
            super(v);
            card    = (CardView) v;
            tvEmoji = v.findViewById(R.id.tv_badge_emoji);
            tvName  = v.findViewById(R.id.tv_badge_name);
            tvReq   = v.findViewById(R.id.tv_badge_req);
        }
    }
}
