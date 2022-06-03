package com.pandapip1.jchem;

import com.pandapip1.jchem.classes.ElementaryStep;
import com.pandapip1.jchem.classes.MatterState;
import com.pandapip1.jchem.classes.Species;
import com.pandapip1.jchem.utils.SubscriptConverter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class JChem {
    public static final Logger logger = Logger.getLogger(JChem.class.getName());
    public static final JSONParser parser = new JSONParser();

    public static void loadDataFromJSON(Path path) throws IOException, ParseException {
        JSONObject data = (JSONObject) parser.parse(Files.readString(path, StandardCharsets.UTF_8));

        JSONObject states = (JSONObject) data.get("states");
        JSONObject species = (JSONObject) data.get("species");
        JSONObject steps = (JSONObject) data.get("steps");

        for (Object keyO : states.keySet()) {
            String key = (String) keyO;
            JSONObject stateData = (JSONObject) states.get(key);
            // Ignore result of new MatterState, as it is automatically added to the map
            new MatterState(UUID.fromString(key), (String) stateData.get("identifier"));
        }

        for (Object keyO : species.keySet()) {
            String key = (String) keyO;
            JSONObject speciesData = (JSONObject) species.get(key);
            // Ignore result of new Species, as it is automatically added to the map
            new Species(UUID.fromString(key), UUID.fromString((String) speciesData.get("state")), SubscriptConverter.convertNums((String) speciesData.get("identifier")));
        }

        for (Object keyO : steps.keySet()) {
            String key = (String) keyO;
            JSONObject stepsData = (JSONObject) steps.get(key);

            // Generate reaction data
            JSONObject reactionData = (JSONObject) stepsData.get("reaction");
            Map<UUID, Number> reaction = new HashMap<>();
            for (Object keyO2 : reactionData.keySet()) {
                String key2 = (String) keyO2;
                reaction.put(UUID.fromString(key2), (Number) stepsData.get(key2));
            }

            // Ignore result of new ElementaryStep, as it is automatically added to the map
            new ElementaryStep(UUID.fromString(key), reaction, (Number) stepsData.get("deltaH"), (Number) stepsData.get("deltaS"), (Number) stepsData.get("preExponentialFactor"), (Number) stepsData.get("activationEnergy"));
        }
    }
}
