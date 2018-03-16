package com.sanrenxing.shop.bean;

import com.sanrenxing.shop.db.admin.bean.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/3/9.
 * @author tony
 */
public class ResourceBean {

    private Resource resource;

    private List<Resource> childResource;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public List<Resource> getChildResource() {
        if (childResource == null) {
            childResource = new ArrayList<>();
        }
        return childResource;
    }

    public void setChildResource(List<Resource> childResource) {
        this.childResource = childResource;
    }

    public void addChildResource(Resource resource) {
        getChildResource().add(resource);
    }

}
