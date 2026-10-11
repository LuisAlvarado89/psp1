package org.ies.tierno;

import org.ies.tierno.components.ChargingStation;
import org.ies.tierno.components.StationStats;
import org.ies.tierno.model.StatsSnapshot;
import org.ies.tierno.model.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        List<Vehicle> vehicles = new ArrayList<>();
        long sumKwh = 0;

        for (int i = 0; i < 20; i++) {
            String plate = "MAT- " + random.nextInt(10000);
            int kwh = random.nextInt(1, 120);
            vehicles.add(new Vehicle(plate, kwh));
            sumKwh = sumKwh + kwh;
        }

        StationStats stats = new StationStats();
        ChargingStation station = new ChargingStation(stats);

        try (ExecutorService executor = Executors.newFixedThreadPool(8)) {
            for (Vehicle vehicle : vehicles) {
                executor.submit(() -> station.charge(vehicle));
            }
        }

        //Comprueba resutlados

        StatsSnapshot result = stats.snapshot();
        System.out.println("Total kwh: " + result.totalKwh());
        System.out.println("Total céntimos: " + result.totalCents());

        if (sumKwh == result.totalKwh()) {
            System.out.println("La energía coincide ");
        } else {
            System.out.println("La energía no coincide ");
        }
        if (result.totalCents() == result.totalKwh() * 45L) {
            System.out.println("Los ingresos coinciden");
        } else {
            System.out.println("Los ingresos no coinciden ");
        }
    }
}