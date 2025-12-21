package geo;

public class Position {
    private double x, y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(Position other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void moveTo(Position destination, double step) {
        double dx = destination.x - this.x;
        double dy = destination.y - this.y;
        double dist = distanceTo(destination);
        if (dist == 0) return;
        this.x += (dx / dist) * step;
        this.y += (dy / dist) * step;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Position)) return false;
        Position p = (Position) obj;
        return this.x == p.x && this.y == p.y;
    }
}
