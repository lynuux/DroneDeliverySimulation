package geo;

import java.util.*;

public class Map {
    private List<DeliveryZone> deliveryZones = new ArrayList<>();
    private List<NoFlyZone> noFlyZones = new ArrayList<>();

    public void addDeliveryZone(DeliveryZone dz) { deliveryZones.add(dz); }
    public void addNoFlyZone(NoFlyZone nfz) { noFlyZones.add(nfz); }

    public boolean isAllowed(Position p) {
        for (NoFlyZone nfz : noFlyZones) {
            if (nfz.contains(p)) return false;
        }
        return true;
    }

    public boolean isForbidden(Position p) {
        return !isAllowed(p);
    }
}

