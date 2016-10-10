package com.demo.limits.common.identity;

/**
 *
 * User: lnv
 * Date: 
 * Time: 
 * To change this template use File | Settings | File Templates.
 */
public class IdmUser {

    private long id;
    private String uuid;
    private String name;
    private String firstName;
    private String lastName;

    public IdmUser(long id,String uuid, String name) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public IdmUser setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public IdmUser setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }


}
