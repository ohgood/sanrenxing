package com.sanrenxing.shop.model.hb;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created on 18/3/2.
 * @author tony
 */
public class EquipStatusAdapter implements JsonSerializer<EquipStatus> {
    @Override
    public JsonElement serialize(EquipStatus equipStatus, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(equipStatus.getValue());
    }
}
