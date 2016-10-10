package com.demo.limits.common.identity;

import java.util.*;

/**
 *
 * User: lnv
 * Date:
 * Time: 
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticatedSession implements Map<Object, Object>{
    private String id;

    private Map<Object, Object> properties;

    public AuthenticatedSession(String id) {
        this.id = id;
        this.properties = new HashMap<Object, Object>();
    }

    public String getId() {
        return id;
    }

    @Override
    public int size() {
        return properties.size();
    }

    @Override
    public boolean isEmpty() {
        return properties.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return properties.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return properties.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return properties.get(key);
    }

    @Override
    public Object put(Object key, Object value) {
        return properties.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return properties.remove(key);
    }

    @Override
    public void putAll(Map<? extends Object, ? extends Object> m) {
        properties.putAll(m);
    }

    @Override
    public void clear() {
        properties.clear();
    }

    @Override
    public Set<Object> keySet() {
        return properties.keySet();
    }

    @Override
    public Collection<Object> values() {
        return properties.values();
    }

    @Override
    public Set<Entry<Object, Object>> entrySet() {
        return properties.entrySet();
    }
}
