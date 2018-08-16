package com.kanon.vintage.subscription.ehcacheImp;

import com.kanon.vintage.subscription.dao.SubscriptionDAO;
import com.kanon.vintage.subscription.model.GroupSubscription;
import com.kanon.vintage.subscription.model.PersonSubscription;
import com.kanon.vintage.subscription.model.Subscription;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class SubscriptionEhcacheImp extends SubscriptionDAO {

    private static SubscriptionEhcacheImp subscriptionEhcacheImp = new SubscriptionEhcacheImp();

    public static SubscriptionEhcacheImp getInstance(){ return subscriptionEhcacheImp;}

    private SubscriptionEhcacheImp() {}


    static String getStoragePath(){
        return "../tmp/ehcache";
    }

    Logger logger = LoggerFactory.getLogger(SubscriptionEhcacheImp.class);
    CacheManager cacheManager = null;

    private final static String CACHE_NAME = "SUBSCRIPTION_CACHE";

    private CacheConfigurationBuilder<String, PersonSubscription> getDefaultConfigurationBuilder(){
       return  CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, PersonSubscription.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                        .heap(10, EntryUnit.ENTRIES)
                        .offheap(1, MemoryUnit.MB)
                        .disk(20, MemoryUnit.MB, true));
    }

    public CacheManager getCacheManager() {
        if(this.cacheManager==null) {
            cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                    .with(CacheManagerBuilder.persistence(new File(getStoragePath(), CACHE_NAME)))
                    .withCache(CACHE_NAME, getDefaultConfigurationBuilder())
                    .build(true);
        }
        return cacheManager;
    }



    public Cache getCache(){
        Cache cache =  getCacheManager().getCache(CACHE_NAME, String.class, PersonSubscription.class);
        if(cache == null){
            cache = getCacheManager().createCache(CACHE_NAME, getDefaultConfigurationBuilder());
        }
        return cache;
    }
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public PersonSubscription get(String userid) {
        PersonSubscription personSubscription = (PersonSubscription) getCache().get(userid);
        if(personSubscription == null){
            personSubscription = new PersonSubscription(userid, new HashMap<>());
            this.save(personSubscription);
            logger.warn("do not exist, create one");
        }
        return personSubscription;
    }

    public boolean save(PersonSubscription personSubscription) {
        getCache().put(personSubscription.getId(), personSubscription);
        return false;
    }

    public boolean update(Optional<? extends PersonSubscription> personSubscription) {
        getCache().put(personSubscription.get().getId(), personSubscription);
        return false;
    }

    public boolean delete(String userid) {
        getCache().remove(userid);
        return true;
    }

    public GroupSubscription getGroup(String userid, final String groupName) {
        return new GroupSubscription( groupName,  this.get(userid).getGroupSubscriptions().get(groupName));
    }

    public int addGroup(String userid, GroupSubscription groupSubscription) {
        PersonSubscription personSubscription = this.get(userid);
        if(personSubscription.getGroupSubscriptions()!=null &&
                personSubscription.getGroupSubscriptions().get(groupSubscription.getName())!=null){
            logger.error("already exists, need to update?");
            return 0;
        }
        else{
            Map<String, Collection<? extends Subscription>> groupSubscriptions = personSubscription.getGroupSubscriptions();
            groupSubscriptions.put(groupSubscription.getName(), groupSubscription.getSubscriptions());
            this.save(personSubscription);
            return groupSubscription.getSubscriptions().size();
        }
    }

    public int updateGroup(String userid, GroupSubscription groupSubscription) {
        PersonSubscription personSubscription = this.get(userid);
        personSubscription.getGroupSubscriptions().remove(groupSubscription.getName());
        personSubscription.getGroupSubscriptions().put(groupSubscription.getName(), groupSubscription.getSubscriptions());
        this.save(personSubscription);
        return groupSubscription.getSubscriptions().size();
    }

    public int deleteGroup(String userid, String groupName) {
        PersonSubscription personSubscription = this.get(userid);
        int size = personSubscription.getGroupSubscriptions().get(groupName) == null? 0: personSubscription.getGroupSubscriptions().get(groupName).size();
        personSubscription.getGroupSubscriptions().remove(groupName);
        this.save(personSubscription);
        return size;
    }
}
