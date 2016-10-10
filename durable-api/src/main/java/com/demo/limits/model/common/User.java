package com.demo.limits.model.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonIgnore
    private String uuid;
    @JsonProperty
    private String name;
    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;

    public User(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    @JsonCreator
    public User(
            @JsonProperty("uuid") String uuid,
            @JsonProperty("name") String name,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName) {
        this.uuid = uuid;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }


}
