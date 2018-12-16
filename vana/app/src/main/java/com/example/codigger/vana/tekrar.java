package com.example.codigger.vana;

public class tekrar {
    String startTime;
    String endTime;
    String days;
    boolean tg;

    public boolean isTg() {
        return tg;
    }

    public void setTg(boolean tg) {
        this.tg = tg;
    }

    public tekrar(String startTime, String endTime, String days, boolean toggle) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.days = days;
        this.tg = toggle;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
