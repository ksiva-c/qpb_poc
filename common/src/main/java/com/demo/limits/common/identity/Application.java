package com.demo.limits.common.identity;

/**
 *
 * User: lnv
 * Date: 
 * Time: 
 * To change this template use File | Settings | File Templates.
 */
public class Application {

    private long id;
    private String name;

    public Application(long id, String name) {
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
