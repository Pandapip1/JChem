package com.pandapip1.jchem.classes;


import com.pandapip1.jchem.JChem;

import java.util.*;

public record ElementaryStep(UUID uuid, Map<UUID, Integer> rxn, double deltaH, double deltaS) {
    private static final Map<UUID, ElementaryStep> allSteps = new HashMap<>();

    public ElementaryStep {
        if (allSteps.containsKey(uuid)) {
            JChem.logger.warning("Elementary Step with UUID " + uuid + " already exists.\n" + this);
        } else {
            allSteps.put(uuid, this);
        }
    }

    public static ElementaryStep getStep(UUID uuid) {
        return allSteps.get(uuid);
    }

    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<UUID> keys = new ArrayList<>(rxn.keySet());

        // Order so that the reactants are listed first
        int toSwap = 0;
        for (int i = 0; i < keys.size(); i++) {
            if (rxn.get(keys.get(i)) > 0) {
                UUID temp = keys.get(i);
                keys.set(i, keys.get(toSwap));
                keys.set(toSwap, temp);
                toSwap++;
            }
        }

        // Actuall pretty-printing
        for (int i = 0; i < toSwap; i++) {
            if (Math.abs(rxn.get(keys.get(i))) != 1) {
                sb.append(rxn.get(keys.get(i)));
                sb.append(" ");
            }
            sb.append(Species.getSpecies(keys.get(i)));
            if (i != toSwap - 1) {
                sb.append(" + ");
            }
        }
        sb.append(" ⟶ ");
        for (int i = toSwap; i < keys.size(); i++) {
            if (Math.abs(rxn.get(keys.get(i))) != 1) {
                sb.append(rxn.get(keys.get(i)));
                sb.append(" ");
            }
            sb.append(Species.getSpecies(keys.get(i)));
            if (i != keys.size() - 1) {
                sb.append(" + ");
            }
        }

        // Delta H and Delta S
        sb.append(" ΔH = ");
        sb.append(deltaH);
        sb.append(" ΔS = ");
        sb.append(deltaS);

        return sb.toString();
    }
}
