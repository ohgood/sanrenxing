package com.sanrenxing.shop.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created on 2017/3/16.
 * @author tony
 */
@JsonSerialize(using = PageList.CustomSerializer.class)
public class PageList<E> extends ArrayList<E> implements Serializable {

    private static final long serialVersionUID = 1412759446332294208L;
    private int pageSize;
    private int page;
    private int totalItems;

    public PageList() {
    }

    public PageList(Collection<? extends E> c) {
        super(c);
    }

    public PageList(int page, int pageSize, int totalItems) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
    }

    public PageList(Collection<? extends E> c, int page, int pageSize, int totalItems) {
        super(c);
        this.page = page;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalItems() {
        return this.totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    static class CustomSerializer extends JsonSerializer<PageList> {

        public void serialize(PageList pageList, JsonGenerator jGen, SerializerProvider provider)
                throws IOException {
            jGen.writeStartObject();
            jGen.writeNumberField("pageSize", pageList.pageSize);
            jGen.writeNumberField("page", pageList.page);
            jGen.writeNumberField("recordsTotal", pageList.totalItems);
            jGen.writeObjectField("data", pageList.toArray());
            jGen.writeEndObject();
        }
    }
}