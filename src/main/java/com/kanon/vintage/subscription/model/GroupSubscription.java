package com.kanon.vintage.subscription.model;

import java.io.Serializable;
import java.util.Collection;

public final class GroupSubscription implements Serializable {

    private static final long serialVersionUID = -7496641844692427844L;

    private String name;

    private Collection<? extends Subscription> subscriptions;

    public GroupSubscription(String name, Collection<? extends Subscription> subscriptions) {
        this.name = name;
        this.subscriptions = subscriptions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<? extends Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Collection<? extends Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
