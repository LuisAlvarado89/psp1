package org.ies.tierno.components;


import org.ies.tierno.model.StatsSnapshot;

public class StationStats {
    private int totalKwh;
    private long totalCents;

    public synchronized void register(int kwh) {
        totalKwh = totalKwh + kwh;
        totalCents = totalCents + kwh * 45L;
    }

    public synchronized StatsSnapshot snapshot() {
        return new StatsSnapshot(totalKwh, totalCents);
    }
}
