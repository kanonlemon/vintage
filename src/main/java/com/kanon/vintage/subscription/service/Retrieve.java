package com.kanon.vintage.subscription.service;

import com.kanon.vintage.subscription.imp.ehcache.SubscriptionServiceEhcacheImp;
import com.kanon.vintage.subscription.model.PersonSubscription;
import com.kanon.vintage.subscription.model.Subscription;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Retrieve {

    private Logger logger = LoggerFactory.getLogger(Retrieve.class);

    private List<SubscriptionService> subscriptionServices = new ArrayList<SubscriptionService>();

    /**
     * 默认为本地存储
     */
    public Retrieve(Object... objects){

        if(objects.length == 0) {
            this.subscriptionServices.add(new SubscriptionServiceEhcacheImp());
        } else {
            for (int i = 0; i < objects.length; i++) {
                Object obj = objects[i];
                if(obj instanceof SubscriptionService)
                    subscriptionServices.add((SubscriptionService) obj);
            }
        }
    }

    public Retrieve(){
        this.subscriptionServices.add(new SubscriptionServiceEhcacheImp());
    }

    public List<Subscription> retrieval(String userid, String[] groupNames, String keywords){
        PersonSubscription personSubscription = new PersonSubscription(userid, new HashMap<>());
        List<Subscription> results = new ArrayList<Subscription>();
        if(StringUtils.isBlank(keywords)){
            return results;
        }
        this.subscriptionServices.parallelStream().forEach( x->{
            PersonSubscription PS = x.get(userid);
            personSubscription.getGroupSubscriptions().putAll(PS.getGroupSubscriptions());
        });

        if(groupNames.length==0){
            logger.warn("empty searching space, use all" );
            Set<String> set = personSubscription.getGroupSubscriptions().keySet();
            groupNames = set.toArray(new String[set.size()]);
        }
        for(String groupname: groupNames) {
            List<Subscription> cur_search = (List<Subscription>) personSubscription.getGroupSubscriptions().get(groupname);
            if(cur_search!=null && cur_search.size() > 0){
                results.addAll(cur_search.parallelStream()
                        .filter( x -> (x.getTitle()!=null && x.getTitle().indexOf(keywords) >= 0)
                                ||(x.getLink()!=null && x.getLink().indexOf(keywords) >= 0)
                                ||(x.getDescription() != null && x.getDescription().indexOf(keywords) >= 0)
                                ||(x.getFrom() != null && x.getFrom().indexOf(keywords) >= 0))
                        .collect(Collectors.toList()));
            }
        }
        return results;
    }

    public List<Subscription> retrieval(String userid, String groupNames, String keywords) {
        if(StringUtils.isNotEmpty(groupNames))
            return retrieval(userid, groupNames.split(","), keywords);
        else
            return retrieval(userid, new String[]{}, keywords);
    }

}
