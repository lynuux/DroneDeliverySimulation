package drone;

import geo.Position;
import java.util.ArrayList;
import java.util.List;

public abstract class Drone {
    private static int counter = 0;
    protected int id;
    protected Position position;
    protected double battery;
    protected String model;
    protected double speed;
    protected double capacity;
    protected String status;
    protected double totalDistance;
    protected List<Position> positionHistory;

    public Drone(Position position, String model, double speed, double capacity) {
        this.id = ++counter;
        this.position = position;
        this.battery = 100;
        this.model = model;
        this.speed = speed;
        this.capacity = capacity;
        this.status = "AVAILABLE";
        this.totalDistance = 0;
        this.positionHistory = new ArrayList<>();
        positionHistory.add(position);
    }

    // === NEW METHOD ===
    public String getModel() {
        return model;
    }

    public abstract double calculateConsumption(double distance);

    public boolean canFlyTo(Position destination) {
        double dist = position.distanceTo(destination) * 2; // round trip
        return battery >= calculateConsumption(dist);
    }

    public void flyTo(Position destination) {
        double dist = position.distanceTo(destination);
        battery -= calculateConsumption(dist);
        totalDistance += dist;
        position = destination;
        positionHistory.add(destination);
    }

    public void recharge(double percentage) {
        battery = Math.min(100, battery + percentage);
    }

    public double getBattery() { return battery; }
    public double getCapacity() { return capacity; }
    public Position getPosition() { return position; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return model + " #" + id + " [" + status + ", battery=" + battery + "%]";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Drone)) return false;
        Drone d = (Drone) obj;
        return this.id == d.id;
    }
}
