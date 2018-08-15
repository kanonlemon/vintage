package com.kanon.vintage.subscription.action;

import com.kanon.vintage.subscription.model.GroupSubscription;
import com.kanon.vintage.subscription.model.PersonSubscription;
import com.kanon.vintage.subscription.model.Subscription;

public interface SubscriptionService {

    public PersonSubscription get(String userid);

    public boolean delete(String userid);

    public boolean save(PersonSubscription personSubscription);

    public GroupSubscription getGroup(String userid, String groupName);

    public int addGroup(String userid, GroupSubscription groupSubscription);

    public int updateGroup(String userid, GroupSubscription groupSubscription);

    public int deleteGroup(String userid, String groupName);

    public boolean addSubscription(String userid, String groupname, Subscription subscription);

    public boolean updateSubscrition(String userid, String groupname, Subscription subscription);

    public boolean deleteSubscrition(String userid, String groupname, Subscription subscription);


}
