package com.example.skyprivate.CheckStatus.LiveJasmin;

public class LiveJasminAchievement {
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    private int level, points;

    @Override
    public String toString() {
        return "Level " + level +
                " und hat " + points +
                " Punkte.";
    }
}
