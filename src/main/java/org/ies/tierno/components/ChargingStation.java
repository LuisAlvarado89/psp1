package org.ies.tierno.components;

import org.ies.tierno.model.ChargeResult;
import org.ies.tierno.model.Vehicle;

import java.util.concurrent.Semaphore;

public class ChargingStation {
    private final Semaphore chargers = new Semaphore(4);
    private final StationStats stats;

    public ChargingStation(StationStats stats) {
        this.stats = stats;
    }

    //metodo chargeResult
    //recibe un vehículo y devuelve el resultado de la carga.

    public ChargeResult charge(Vehicle vehicle) {

        try {
            chargers.acquire();
            System.out.println(vehicle.plate() + " Vehiculo empieza la carga. ");

            Thread.sleep(vehicle.kwh() * 20L);

            stats.register(vehicle.kwh());
            System.out.println(vehicle.plate() + " Vehiculo termina y libera cargador. ");
            return new ChargeResult(vehicle.plate(), vehicle.kwh(), vehicle.kwh() * 45L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            chargers.release();
        }
    }
}


