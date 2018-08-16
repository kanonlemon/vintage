package com.kanon.vintage.subscription.service;

import com.kanon.vintage.subscription.dao.SubscriptionDAO;
import com.kanon.vintage.subscription.model.GroupSubscription;
import com.kanon.vintage.subscription.model.PersonSubscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SubscriptionService {

    /**
     * 数据服务层
     */
    private SubscriptionDAO subscriptionDAO = null;

    Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

    public SubscriptionService(SubscriptionDAO subscriptionDAO){
        this.subscriptionDAO = subscriptionDAO;
    }

    public SubscriptionDAO getSubscriptionDAO() {
        return subscriptionDAO;
    }

    public void setSubscriptionDAO(SubscriptionDAO subscriptionDAO) {
        this.subscriptionDAO = subscriptionDAO;
    }

    public PersonSubscription get(String userid){
        return subscriptionDAO.get(userid);
    }

    public boolean delete(String userid){
        return  subscriptionDAO.delete(userid);
    }

    public boolean save(PersonSubscription personSubscription){
        return subscriptionDAO.save(personSubscription);
    }

    public GroupSubscription getGroup(String userid, String groupName){
        return subscriptionDAO.getGroup(userid, groupName);
    }

    public int addGroup(String userid, GroupSubscription groupSubscription){
        return subscriptionDAO.addGroup(userid, groupSubscription);
    }

    public int updateGroup(String userid, GroupSubscription groupSubscription){
        return subscriptionDAO.updateGroup(userid, groupSubscription);
    }

    public int deleteGroup(String userid, String groupName){
        return subscriptionDAO.deleteGroup(userid, groupName);
    }


}
