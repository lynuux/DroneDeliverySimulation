package drone;

import geo.Position; 
public class ExpressDrone extends Drone {
    public ExpressDrone(Position position) {
        super(position, "ExpressDrone", 45, 1.0);
    }

    @Override
    public double calculateConsumption(double distance) {
        return distance * 4; // 4% per km
    }
}
