package com.demo.limits.common.identity;

/**
 *
 * User: lnv
 * Date: 
 * Time: 
 * To change this template use File | Settings | File Templates.
 */
public class Realm {

    private long id;
    private String uuid;
    private String name;

    public Realm(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Realm(long id,String uuid, String name) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }
}
