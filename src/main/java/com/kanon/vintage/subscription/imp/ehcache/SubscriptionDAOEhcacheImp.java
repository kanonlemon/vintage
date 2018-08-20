package com.kanon.vintage.subscription.imp.ehcache;

import com.kanon.vintage.configuration.propertiesLoader;
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


/**
 * solution with ehcache3.x
 * need ehcache master!!!!!
 *
 * @author kanon
 * @email wengxuan1992@hotmail.com
 */
public class SubscriptionDAOEhcacheImp extends SubscriptionDAO {

    Logger logger = LoggerFactory.getLogger(SubscriptionDAOEhcacheImp.class);

    public String storagePath;
    CacheManager cacheManager;
    private String cacheName ;

    public SubscriptionDAOEhcacheImp(CacheManager cacheManager, String cacheName) {

        this.storagePath =  propertiesLoader.getProperty("vintage.subscription.ehcache.diskroot");
        this.cacheManager= cacheManager;
        this.cacheName = cacheName;

        if(cacheManager == null){
            this.cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                    .with(CacheManagerBuilder.persistence(new File(this.getStoragePath(), cacheName)))
                    .withCache(cacheName, getDefaultConfigurationBuilder())// init a cache
                    .build(true);
        }
        if(this.cacheManager.getCache(this.cacheName, String.class, PersonSubscription.class)==null){
            this.cacheManager.createCache(this.cacheName, getDefaultConfigurationBuilder());
        }

        if( Boolean.valueOf(propertiesLoader.getProperty("vintage.subscription.ehcache,enableshutdownhook"))) {
            //if shutdown hook is enabled, then add a safe close of cacheManager
            Runtime.getRuntime().addShutdownHook(new Thread(() -> this.cacheManager.close()));
        }

    }

    public SubscriptionDAOEhcacheImp(String cacheName){
        this(null, cacheName);
    }

    public SubscriptionDAOEhcacheImp(){
        this(null,"SUBSCRIPTION_CACHE" );
    }

    public SubscriptionDAOEhcacheImp(CacheManager cacheManager){ this(cacheManager, "SUBSCRIPTION_CACHE");}

    private String getStoragePath(){
        return this.storagePath;
    }

    private static CacheConfigurationBuilder<String, PersonSubscription> getDefaultConfigurationBuilder(){
       return  CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, PersonSubscription.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                        .heap(10, EntryUnit.ENTRIES)
                        .disk(500, MemoryUnit.MB, true));
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getCacheName() {
        return cacheName;
    }

    public Cache getCache(){
        Cache cache = getCacheManager().getCache(cacheName, String.class, PersonSubscription.class);
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