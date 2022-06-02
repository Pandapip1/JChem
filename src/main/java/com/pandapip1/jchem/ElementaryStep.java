package com.pandapip1.jchem;


import java.util.*;

public record ElementaryStep(UUID uuid, Map<UUID, Integer> rxn, double deltaH, double deltaS) {
    private static final Map<UUID, ElementaryStep> allSteps = new HashMap<>();

    public ElementaryStep {
        if (allSteps.containsKey(uuid)) {
            JChem.logger.warning("State with UUID " + uuid + " already exists.\n" + rxn);
        } else {
            allSteps.put(uuid, this);
        }
    }

    public static ElementaryStep getStep(UUID uuid) {
        return allSteps.get(uuid);
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return getIdentifier();
    }
}
