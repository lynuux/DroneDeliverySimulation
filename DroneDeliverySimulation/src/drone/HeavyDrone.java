package drone;

import geo.Position;

public class HeavyDrone extends Drone {
    public HeavyDrone(Position position) {
        super(position, "HeavyDrone", 20, 3.0);
    }

    @Override
    public double calculateConsumption(double distance) {
        return distance * 5; // 5% per km
    }

    @Override
    public void flyTo(Position destination) {
        if (battery < 20) speed = 16;
        super.flyTo(destination);
    }
}