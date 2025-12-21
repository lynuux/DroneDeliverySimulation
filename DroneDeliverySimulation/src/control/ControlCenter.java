package control;

import java.util.List;
import java.util.ArrayList;
import geo.Position;
import geo.Map;
import order.Order;
import drone.Drone;

public class ControlCenter {
    private List<Drone> fleet = new ArrayList<>();
    private List<Order> pendingOrders = new ArrayList<>();
    private Position base;
    private Map map;
    private List<Order> processedOrders = new ArrayList<>();

    private static int totalDeliveries = 0;
    private static double totalDistance = 0;
    private static double energyConsumedStandard = 0;
    private static double energyConsumedExpress = 0;
    private static double energyConsumedHeavy = 0;

    public ControlCenter(Position base, Map map) {
        this.base = base;
        this.map = map;
    }

    // Getters
    public List<Drone> getFleet() { return fleet; }
    public Position getBase() { return base; }
    public Map getMap() { return map; }
    public List<Order> getPendingOrders() { return pendingOrders; }
    public List<Order> getProcessedOrders() { return processedOrders; }

    public void addDrone(Drone d) { fleet.add(d); }

    public Drone findDroneForOrder(Order order) {
        Position dest = order.getDeliverable().getDestination();
        for (Drone d : fleet) {
            if (d.getStatus().equals("AVAILABLE") &&
                d.getCapacity() >= order.getDeliverable().getWeight() &&
                map.isAllowed(dest) &&
                d.canFlyTo(dest)) {
                return d;
            }
        }
        return null;
    }

    public double calculateDeliveryCost(Order order, Drone drone) {
        double distance = base.distanceTo(order.getDeliverable().getDestination()) * 2;
        double consumption = drone.calculateConsumption(distance);
        double operationCost = (distance * 0.1) + (consumption * 0.02) + 20;
        double insurance = Math.max(order.getCost() * 0.02, 10);
        if ("EXPRESS".equals(order.getUrgency())) insurance += 20;
        return operationCost + insurance;
    }

    // Static helpers for statistics
    public static void addDistance(double d) { totalDistance += d; }
    public static void addDelivery() { totalDeliveries++; }
    public static void addEnergy(String type, double consumption) {
        switch (type) {
            case "Standard": energyConsumedStandard += consumption; break;
            case "Express": energyConsumedExpress += consumption; break;
            case "Heavy": energyConsumedHeavy += consumption; break;
        }
    }

    public static void printStatistics() {
        System.out.println("Total Deliveries: " + totalDeliveries);
        System.out.println("Total Distance: " + totalDistance + " km");
        System.out.println("Energy Consumed (Standard): " + energyConsumedStandard);
        System.out.println("Energy Consumed (Express): " + energyConsumedExpress);
        System.out.println("Energy Consumed (Heavy): " + energyConsumedHeavy);
    }
}
