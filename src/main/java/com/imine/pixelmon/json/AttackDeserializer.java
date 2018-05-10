package com.imine.pixelmon.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AttackDeserializer extends JsonDeserializer<Attack> {

    private static final Logger logger = LoggerFactory.getLogger(AttackDeserializer.class);

    @Override
    public Attack deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String attackName = parser.getText();
        return Attack.getAttackBase(attackName).map(Attack::new).orElseGet(() -> {
            logger.warn("No attack found with name '{}' Context: (name: {}, line: {})", attackName, parser.getCurrentLocation().getLineNr(), parser.currentToken().name());
            return null;
        });

    }
}
