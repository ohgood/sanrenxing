package com.sanrenxing.shop.model.hb;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.sanrenxing.shop.util.aliplay.Utils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created on 18/3/2.
 * @author tony
 */
public class ExceptionInfoAdapter implements JsonSerializer<List<ExceptionInfo>> {
    
    @Override
    public JsonElement serialize(List<ExceptionInfo> exceptionInfos, Type type, JsonSerializationContext jsonSerializationContext) {
        if (Utils.isListEmpty(exceptionInfos)) {
            return null;
        }

        return new JsonPrimitive(StringUtils.join(exceptionInfos, "|"));
    }
}
