package com.kyozm.nanoutils.utils;

public class TickTimer {
    public long ticksTillExpired;
    public boolean isExpired;

    public TickTimer(long ticksTillExpired) {
        this.ticksTillExpired = ticksTillExpired;
    }

    public void step() {
        if (ticksTillExpired > 0) {
            ticksTillExpired--;
        } else {
            isExpired = true;
        }
    }
}
