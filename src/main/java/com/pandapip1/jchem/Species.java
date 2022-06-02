package com.pandapip1.jchem;


import java.util.*;

public record Species(UUID uuid, UUID state, String identifier) {
    private static final Map<UUID, Species> allSpecies = new HashMap<>();

    public Species {
        if (allSpecies.containsKey(uuid)) {
            JChem.logger.warning("Species with UUID " + uuid + " already exists.\n" + identifier);
        } else {
            allSpecies.put(uuid, this);
        }
    }

    public static Species getSpecies(UUID uuid) {
        return allSpecies.get(uuid);
    }

    public UUID getUuid() {
        return uuid;
    }

    public MatterState getState() {
        return MatterState.getState(state);
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return getIdentifier();
    }
}
