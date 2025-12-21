package control;

import drone.ExpressDrone;
import drone.HeavyDrone;
import drone.StandardDrone;
import geo.DeliveryZone;
import geo.Map;
import geo.NoFlyZone;
import geo.Position;
import java.util.ArrayList;
import java.util.List;
import order.Order;
import order.StandardPackage;
import simulator.Simulator;

public class Main {
    public static void main(String[] args) {
        // === Base and Map ===
        Position base = new Position(0, 0);
        Map cityMap = new Map();
        cityMap.addDeliveryZone(new DeliveryZone(new Position(10, 10), 5));
        cityMap.addNoFlyZone(new NoFlyZone(new Position(5, 5), 2));

        // === Control Center ===
        ControlCenter controlCenter = new ControlCenter(base, cityMap);

        // === Fleet ===
        controlCenter.addDrone(new StandardDrone(base));
        controlCenter.addDrone(new ExpressDrone(base));
        controlCenter.addDrone(new HeavyDrone(base));

        // === Orders ===
        Order o1 = new Order("Client A",
                new StandardPackage(0.5, new Position(8, 8)),
                "NORMAL", 100);
        Order o2 = new Order("Client B",
                new StandardPackage(1.0, new Position(12, 12)),
                "EXPRESS", 200);
        Order o3 = new Order("Client C",
                new StandardPackage(2.5, new Position(15, 15)),
                "NORMAL", 150);

        List<Order> orders = new ArrayList<>();
        orders.add(o1);
        orders.add(o2);
        orders.add(o3);

        // === Run Simulation ===
        Simulator simulator = new Simulator(controlCenter);
        simulator.runSimulation(orders);

        // === Results ===
        System.out.println("\n=== Final Orders ===");
        for (Order o : orders) {
            System.out.println(o);
        }

        System.out.println("\n=== Fleet Status ===");
        for (var d : controlCenter.getFleet()) {
            System.out.println(d);
        }

        System.out.println("\n=== Statistics ===");
        ControlCenter.printStatistics();
    }
}
