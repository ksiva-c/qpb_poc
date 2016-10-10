package com.demo.limits.common.identity;

/**
 *
 * User: lnv
 * Date: 
 * Time: 
 * To change this template use File | Settings | File Templates.
 */
public class Locale {

    private long id;
    private String name;

    public Locale(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
