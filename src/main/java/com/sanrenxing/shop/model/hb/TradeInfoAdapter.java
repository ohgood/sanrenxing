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
public class TradeInfoAdapter implements JsonSerializer<List<TradeInfo>> {
    @Override
    public JsonElement serialize(List<TradeInfo> tradeInfoList, Type type, JsonSerializationContext jsonSerializationContext) {
        if (Utils.isListEmpty(tradeInfoList)) {
            return null;
        }

        TradeInfo tradeInfo = tradeInfoList.get(0);
        if (tradeInfo instanceof PosTradeInfo) {
            return new JsonPrimitive(StringUtils.join(tradeInfoList, ""));
        }

        return jsonSerializationContext.serialize(tradeInfoList);
    }
}
