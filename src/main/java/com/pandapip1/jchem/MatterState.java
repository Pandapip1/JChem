package com.pandapip1.jchem;


import java.util.*;

public record MatterState(UUID uuid, String identifier) {
    private static final Map<UUID, MatterState> allStates = new HashMap<>();

    public MatterState {
        if (allStates.containsKey(uuid)) {
            JChem.logger.warning("State with UUID " + uuid + " already exists.\n" + identifier);
        } else {
            allStates.put(uuid, this);
        }
    }

    public static MatterState getState(UUID uuid) {
        return allStates.get(uuid);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return getIdentifier();
    }
}
