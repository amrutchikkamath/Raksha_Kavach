package com.rakshakavach.model;

import java.util.List;

public class WorkTask {
    private String id;
    private String name;
    private String emoji;
    private String riskLevel;
    private int riskScore; // 0-100
    private List<SafetyGear> requiredGear;
    private List<String> potentialInjuries;

    public WorkTask(String id, String name, String emoji, String riskLevel, int riskScore,
                    List<SafetyGear> requiredGear, List<String> potentialInjuries) {
        this.id = id;
        this.name = name;
        this.emoji = emoji;
        this.riskLevel = riskLevel;
        this.riskScore = riskScore;
        this.requiredGear = requiredGear;
        this.potentialInjuries = potentialInjuries;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmoji() { return emoji; }
    public String getRiskLevel() { return riskLevel; }
    public int getRiskScore() { return riskScore; }
    public List<SafetyGear> getRequiredGear() { return requiredGear; }
    public List<String> getPotentialInjuries() { return potentialInjuries; }
}
