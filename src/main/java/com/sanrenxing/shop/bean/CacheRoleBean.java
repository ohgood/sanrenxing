package com.sanrenxing.shop.bean;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created on 2017/7/26.
 *
 * @author tony
 */
@Data
public class CacheRoleBean {

    private static final String PARAM = "?name=%s&op_name=%s&user_id=%s&supplier_id=%s&product_id=%s&type=%s" +
            "&business_code=%s&schedule=%s&channel_id=%s&begin_time=%s&end_time=%s";

    private String id;
    private String name;
    private String opName;
    private String userId;
    private Integer supplierId;
    private Integer productId;
    private String businessCode;
    private Integer channelId;
    private Integer schedule;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private Integer type;

    public String toRequstParam() {
        return String.format(name == null ? "" : name,
                opName == null ? "" : opName,
                userId == null ? "" : userId,
                supplierId == null ? "" : supplierId,
                productId == null ? "" : productId,
                type == null ? "" : type,
                businessCode == null ? "" : businessCode,
                schedule == null ? "" : schedule,
                channelId == null ? "" : channelId,
                beginTime == null ? "" : beginTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                endTime == null ? "" : endTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

}
