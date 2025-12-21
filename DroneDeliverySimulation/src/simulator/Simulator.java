package simulator;

import control.ControlCenter;
import control.OrderAssigner;
import drone.Drone;
import geo.Position;
import java.util.ArrayList;   // ✅ import OrderAssigner
import java.util.List;
import order.Order;

public class Simulator {
    private ControlCenter controlCenter;
    private int durationMinutes = 480; // 8 hours

    public Simulator(ControlCenter controlCenter) {
        this.controlCenter = controlCenter;
    }

    public void runSimulation(List<Order> orders) {
        // Try to assign all orders initially
        for (Order order : orders) {
            OrderAssigner.assignOrder(controlCenter, order);  // ✅ fixed call
        }

        // Simulation loop: each minute
        for (int minute = 0; minute < durationMinutes; minute++) {
            // Check pending orders each minute
            List<Order> pendingCopy = new ArrayList<>(controlCenter.getPendingOrders());
            for (Order order : pendingCopy) {
                OrderAssigner.assignOrder(controlCenter, order);  // ✅ fixed call
            }

            // Move drones
            for (Drone d : controlCenter.getFleet()) {
                if (d.getStatus().equals("IN_DELIVERY")) {
                    Order currentOrder = findOrderForDrone(d, orders);
                    if (currentOrder != null) {
                        Position dest = currentOrder.getDeliverable().getDestination();
                        d.flyTo(dest);

                        // If reached destination
                        if (d.getPosition().equals(dest)) {
                            currentOrder.setStatus("DELIVERED");
                            d.setStatus("RETURN_TO_BASE");
                        }
                    }
                } else if (d.getStatus().equals("RETURN_TO_BASE")) {
                    d.flyTo(controlCenter.getBase());
                    if (d.getPosition().equals(controlCenter.getBase())) {
                        d.setStatus("AVAILABLE");
                    }
                }
            }
        }

        // Print statistics
        System.out.println("=== Simulation finished ===");
        ControlCenter.printStatistics();
    }

    // Helper: find the order currently assigned to a drone
    private Order findOrderForDrone(Drone drone, List<Order> orders) {
        for (Order o : orders) {
            if (o.getStatus().equals("IN_PROGRESS")) {
                // crude matching: assume this drone is delivering this order
                return o;
            }
        }
        return null;
    }
}

