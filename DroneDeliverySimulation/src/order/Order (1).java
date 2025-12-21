package order;

public class Order {
    private static int counter = 0;
    private int id;
    private String client;
    private Deliverable deliverable;
    private double cost;      // initially: product price; later: delivery fee
    private String urgency;   // "NORMAL" or "EXPRESS"
    private String status;    // "PENDING", "IN_PROGRESS", "DELIVERED", "FAILED"

    public Order(String client, Deliverable deliverable, String urgency, double cost) {
        this.id = ++counter;
        this.client = client;
        this.deliverable = deliverable;
        this.urgency = urgency;
        this.cost = cost;          // initial product price
        this.status = "PENDING";
    }

    // getters
    public int getId() { return id; }
    public String getClient() { return client; }
    public Deliverable getDeliverable() { return deliverable; }
    public double getCost() { return cost; }        // <-- required
    public String getUrgency() { return urgency; }
    public String getStatus() { return status; }

    // setters
    public void setCost(double cost) { this.cost = cost; }   // becomes delivery fee later
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Order #" + id + " [" + client + ", " + urgency + ", " + status + ", cost=" + cost + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Order)) return false;
        return this.id == ((Order) obj).id;
    }
}
