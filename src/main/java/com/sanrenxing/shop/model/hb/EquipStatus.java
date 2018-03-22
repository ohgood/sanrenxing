package com.sanrenxing.shop.model.hb;

/**
 * Created on 18/3/2.
 * @author tony
 */
public enum EquipStatus {
    ON("10")    // 开机

    ,OFF("20")  // 关机

    ,NORMAL("30")  // 正常

    ,SLEEP("40")  // 进入休眠

    ,AWAKE("41");  // 唤醒

    private String value;

    EquipStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
