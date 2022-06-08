package com.pandapip1.jchem.classes;


import com.pandapip1.jchem.JChem;
import com.pandapip1.jchem.utils.SubscriptConverter;

import java.util.*;

public record Species(UUID uuid, UUID state, String identifier) {
    private static final Map<UUID, Species> allSpecies = new HashMap<>();

    public Species {
        if (allSpecies.containsKey(uuid)) {
            JChem.logger.warning("Species with UUID " + uuid + " already exists.\n" + this);
        } else {
            allSpecies.put(uuid, this);
        }
    }

    public static Species getSpecies(UUID uuid) {
        return allSpecies.get(uuid);
    }

    public UUID getUUID() {
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
        return getIdentifier() + SubscriptConverter.convert("(" + getState() + ")");
    }
}
