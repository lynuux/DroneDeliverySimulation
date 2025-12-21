package drone;

import geo.Position;

public class StandardDrone extends Drone {

    // Constructor: pass model name, base position, speed, and capacity to super
    public StandardDrone(Position base) {
        super(base, "StandardDrone", 50.0, 1.0); 
        // Arguments: Position, model name, speed, capacity
        // Adjust speed/capacity values as needed for your simulation
    }

    @Override
    public double calculateConsumption(double distance) {
        // Example consumption rule: 5% battery per km
        return distance * 5;
    }

    @Override
    public boolean canFlyTo(Position dest) {
        double roundTrip = position.distanceTo(dest) * 2;
        return battery >= calculateConsumption(roundTrip);
    }
}

