package com.rakshakavach.model;

public class SafetyGear {
    private String id;
    private String name;
    private String emoji;
    private String description;
    private String injuryIfIgnored;
    private boolean isChecked;

    public SafetyGear(String id, String name, String emoji, String description, String injuryIfIgnored) {
        this.id = id;
        this.name = name;
        this.emoji = emoji;
        this.description = description;
        this.injuryIfIgnored = injuryIfIgnored;
        this.isChecked = false;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmoji() { return emoji; }
    public String getDescription() { return description; }
    public String getInjuryIfIgnored() { return injuryIfIgnored; }
    public boolean isChecked() { return isChecked; }
    public void setChecked(boolean checked) { isChecked = checked; }
}
