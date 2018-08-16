package com.kanon.vintage.subscription.dao;

import com.kanon.vintage.subscription.model.GroupSubscription;
import com.kanon.vintage.subscription.model.PersonSubscription;
import com.kanon.vintage.subscription.model.Subscription;

import java.util.Optional;

/**
 * 抽象类， 依赖于具体存储方案
 */
public abstract class SubscriptionDAO {

    /**
     * 基础操作， 获取一个人的订阅信息
     * @param userid
     * @return
     */
    public abstract PersonSubscription get(String userid);


    /**
     * 保存一个人的全部订阅
     * @param personSubscription
     * @return
     */
    public abstract boolean save(PersonSubscription personSubscription);


    /**
     * 全量更新订阅信息
     * @param personSubscription
     * @return
     */
    public boolean update(PersonSubscription personSubscription){
        return this.save(personSubscription);
    }

    /**
     * 删除一个人的全部订阅
     * @param userid
     * @return
     */
    public abstract boolean delete(String userid);

    /**
     * 根据用户和组信息获取组订阅
     * @param userid
     * @param groupName
     * @return
     */
    public abstract GroupSubscription getGroup(String userid, String groupName);

    /**
     * 根据用户，添加某个组订阅
     * @param userid
     * @param groupSubscription
     * @return
     */
    public abstract int addGroup(String userid, GroupSubscription groupSubscription);

    /**
     * 全量更新组订阅
     * @param userid
     * @param groupSubscription
     * @return
     */
    public abstract int updateGroup(String userid, GroupSubscription groupSubscription);

    /**
     * 删除某个组
     * @param userid
     * @param groupName
     * @return
     */
    public abstract int deleteGroup(String userid, String groupName);

    /**
     * 添加单条订阅，依赖于对比方法
     * @param userid
     * @param groupname
     * @param subscription
     * @return
     */
    public boolean addSubscription(String userid, String groupname, Subscription subscription) {
        try {
            throw new Exception("unsupport one subscription add to group");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateSubscrition(String userid, String groupname, Subscription subscription){
        try {
            throw new Exception("unsupport one subscription update to group");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteSubscrition(String userid, String groupname, Subscription subscription){
        try {
            throw new Exception("unsupport one subscription delete from group");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
