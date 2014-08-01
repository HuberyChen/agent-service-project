package com.lx.agent.inteceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hubery.chen
 */
public enum Permission {

    ADMIN("admin"),

    CUSTOMER("customer");

    private String name;

    private Permission(String name) {
        this.name = name;
    }

    public static List<String> getNameList() {
        List<String> nameList = new ArrayList<>();
        for (Permission permission : Permission.values()) {
            nameList.add(permission.getName());
        }
        return nameList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
