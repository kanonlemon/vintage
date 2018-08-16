package com.kanon.vintage.subscription.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class PersonSubscription implements Serializable {

    private static final long serialVersionUID = 7957779827408286676L;
    protected  String id;

    protected Map<String, Collection<? extends Subscription>> groupSubscriptions;

    public PersonSubscription(String id, Map<String, Collection<? extends Subscription>> groupSubscriptions) {
        this.id = id;
        this.groupSubscriptions = groupSubscriptions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Collection<? extends Subscription>> getGroupSubscriptions() {
        return groupSubscriptions;
    }

    public void setGroupSubscriptions(Map<String, Collection<? extends Subscription>> groupSubscriptions) {
        this.groupSubscriptions = groupSubscriptions;
    }

    @Override
    public String toString() {
        return "PersonSubscription{" +
                "id='" + id + '\'' +
                ", groupSubscriptions=" + groupSubscriptions +
                '}';
    }
}
