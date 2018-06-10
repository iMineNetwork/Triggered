package com.imine.pixelmon.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.attacks.AttackBase;

import java.io.IOException;

public class AttackDeserializer extends JsonDeserializer<Attack> {

    @Override
    public Attack deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String attackName = parser.getText();
        return AttackBase.getAttackBase(attackName).map(Attack::new).orElseThrow(() ->
                new IllegalArgumentException("No attack found with name '" + attackName + "' Context: (name: " + parser.getCurrentLocation().getLineNr() + ", line: " + parser.currentToken().name() + ")")
        );

    }
}
