package geo;

public class DeliveryZone {
    private Position center;
    private double radius;

    public DeliveryZone(Position center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public boolean contains(Position p) {
        return center.distanceTo(p) <= radius;
    }
}
