package com.example.skyprivate.CheckStatus.BongaCams;

import java.util.ArrayList;

public class TipPopupOptions {
    private boolean isAvailable;
    private int lastTip;
    private ArrayList<Integer> tips;

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getLastTip() {
        return lastTip;
    }

    public void setLastTip(int lastTip) {
        this.lastTip = lastTip;
    }

    public ArrayList<Integer> getTips() {
        return tips;
    }

    public void setTips(ArrayList<Integer> tips) {
        this.tips = tips;
    }

    @Override
    public String toString() {
        return "TipPopupOptions{" +
                "isAvailable=" + isAvailable +
                ", lastTip=" + lastTip +
                ", tips=" + tips +
                '}';
    }
}
