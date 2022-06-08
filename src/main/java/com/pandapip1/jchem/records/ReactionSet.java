package com.pandapip1.jchem.records;

import com.pandapip1.jchem.JChem;
import org.ejml.simple.SimpleMatrix;

import java.util.*;

public class EquipmentType {
    private static final Map<UUID, EquipmentType> allEquipmentTypes = new HashMap<>();
    private final UUID uuid;
    private final String identifier;
    private final List<UUID> possibleReactions;

    // Rate = k[A]^a[B]^b
    // log Rate = log k + a log [A] + b log [B]
    // log Rate = log (A e^(Ea/RT)) + a log [A] + b log [B]
    // log Rate = log A + log (e^(Ea/RT)) + a log [A] + b log [B]
    // log Rate = log A + Ea / RT + a log [A] + b log [B]
    // log Rates = log A + (Ea / R) / T + c log [C]
    // log Rates = logPreExponentialFactors + activationEnergiesOverR / T + rateLawCoefficients log Concentrations
    private final Map<UUID, Integer> speciesOrder = new HashMap<>();
    private final SimpleMatrix logPreExponentialFactors;
    private final SimpleMatrix activationEnergiesOverR;
    private final SimpleMatrix rateLawCoefficients;

    public EquipmentType(UUID uuid, String identifier, List<UUID> possibleReactions) {
        this.uuid = uuid;
        this.identifier = identifier;
        this.possibleReactions = possibleReactions;

        this.logPreExponentialFactors = new SimpleMatrix(possibleReactions.size(), 1);
        this.activationEnergiesOverR = new SimpleMatrix(possibleReactions.size(), 1);

        for (var i = 0; i < possibleReactions.size(); i++) {
            ElementaryStep step = ElementaryStep.getStep(possibleReactions.get(i));

            this.logPreExponentialFactors.set(i, 0, Math.log(step.getPreExponentialFactor().doubleValue()));
            this.activationEnergiesOverR.set(i, 0, step.getActivationEnergy().doubleValue() / JChem.idealGasConstant.doubleValue());

            for (UUID rxnSpecies : step.getRxn().keySet()) {
                this.speciesOrder.putIfAbsent(rxnSpecies, this.speciesOrder.size());
            }
        }

        this.rateLawCoefficients = new SimpleMatrix(possibleReactions.size(), this.speciesOrder.size());

        for (var i = 0; i < possibleReactions.size(); i++) {
            ElementaryStep step = ElementaryStep.getStep(possibleReactions.get(i));
            Map<UUID, Number> rxn = step.getRxn();

            for (UUID rxnSpecies : rxn.keySet()) {
                this.rateLawCoefficients.set(i, this.speciesOrder.get(rxnSpecies), rxn.get(rxnSpecies).doubleValue());
            }
        }

        if (allEquipmentTypes.containsKey(uuid)) {
            JChem.logger.warning("Equipment Type with UUID " + uuid + " already exists.\n" + this);
        } else {
            allEquipmentTypes.put(uuid, this);
        }
    }

    public static EquipmentType getEquipmentType(UUID eType) {
        return allEquipmentTypes.get(eType);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<UUID> getPossibleReactions() {
        return new ArrayList<>(possibleReactions); // Copy so that possible reactions is immutable
    }

    public List<UUID> getSpeciesOrder() {
        List<UUID> order = new ArrayList<>(speciesOrder.size());
        for (UUID id : speciesOrder.keySet()) {
            order.set(speciesOrder.get(id), id);
        }
        return order;
    }

    public SimpleMatrix getLogPreExponentialFactors() {
        return logPreExponentialFactors;
    }

    public SimpleMatrix getActivationEnergiesOverR() {
        return activationEnergiesOverR;
    }

    public SimpleMatrix getRateLawCoefficients() {
        return rateLawCoefficients;
    }

    @Override
    public String toString() {
        return "EquipmentType{" +
                "uuid=" + uuid +
                ", identifier='" + identifier + '\'' +
                ", possibleReactions=" + possibleReactions +
                ", speciesOrder=" + speciesOrder +
                ", logPreExponentialFactors=" + logPreExponentialFactors +
                ", activationEnergiesOverR=" + activationEnergiesOverR +
                ", rateLawCoefficients=" + rateLawCoefficients +
                '}';
    }
}
