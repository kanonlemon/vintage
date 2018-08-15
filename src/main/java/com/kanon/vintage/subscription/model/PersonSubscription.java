package com.kanon.vintage.subscription.model;

import java.util.Collection;

public abstract class PersonSubscription {

    protected  String id;

    protected Collection<? extends GroupSubscription> groupSubscriptions;

    public PersonSubscription(String id, Collection<? extends GroupSubscription> groupSubscriptions) {
        this.id = id;
        this.groupSubscriptions = groupSubscriptions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Collection<? extends GroupSubscription> getGroupSubscriptions() {
        return groupSubscriptions;
    }

    public void setGroupSubscriptions(Collection<? extends GroupSubscription> groupSubscriptions) {
        this.groupSubscriptions = groupSubscriptions;
    }
}
